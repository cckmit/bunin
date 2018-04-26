package my.bunin.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class DelayQueueUtils {

  private static final DelayQueue<CountDelayed> DELAY_QUEUE = new DelayQueue<>();

  @SuppressWarnings("NullableProblems")
  private static class CountDelayed implements Delayed {

    private long time;

    final long now() {
      return System.currentTimeMillis();
    }


    CountDelayed(long time) {
      this.time = time;
    }

    @Override
    public long getDelay(TimeUnit unit) {
      unit = Optional.ofNullable(unit).orElse(NANOSECONDS);
      return unit.convert(time - now(), NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
      if (other == this) // compare zero if same object
        return 0;

      long diff = getDelay(NANOSECONDS) - other.getDelay(NANOSECONDS);
      return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
    }
  }


  @Slf4j
  private static class TakeTask implements Runnable {

    private String name;

    TakeTask(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      try {
        CountDelayed delayed = DELAY_QUEUE.take();

        log.info("{} has take queue[{}]", name, delayed.time);

      } catch (InterruptedException ex) {
        log.info(ex.getMessage(), ex);
      }
    }
  }

  @Slf4j
  private static class OfferTask implements Runnable {

//    private long time;
//
//    public OfferTask(long time) {
//      this.time = time;
//    }

    @Override
    public void run() {
      int size = DELAY_QUEUE.size();
      if (size >= 3) {
        log.info("delay queue is full.");
        return;
      }

      long time = Date.from(LocalDateTime.now().plusSeconds(5).atZone(ZoneId.systemDefault()).toInstant()).getTime();
      DELAY_QUEUE.offer(new CountDelayed(time));
      log.info("delay queue offer task[{}]", time);
    }
  }

  public static void main(String[] args) throws InterruptedException {

    new Thread(new TakeTask("take")).start();

    new Thread(new OfferTask()).start();
    Thread.sleep(1000L);
    new Thread(new OfferTask()).start();
    Thread.sleep(1000L);
    new Thread(new OfferTask()).start();
    Thread.sleep(1000L);
    new Thread(new OfferTask()).start();


//    System.out.println(System.nanoTime());
//    System.out.println(System.nanoTime());
//    System.out.println(System.currentTimeMillis());
//    System.out.println(LocalDateTime.now().getNano());
//
//    System.out.println(System.currentTimeMillis());
//    System.out.println(new Date().getTime());
//    System.out.println(new Date().getTime());

    // 2018-04-25 17:34:30
    // 1524648862979 1524648862.979 25410814.36666667 423513.5666666667
  }







}
