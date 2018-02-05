package my.bunin.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SnowFlakeTest {

    @Test
    public void test() {
        final SnowFlake w = new SnowFlake(1, 2);
        final CyclicBarrier cdl = new CyclicBarrier(1000);

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    cdl.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    log.error(e.getMessage(), e);
                }
                log.info("{}", w.nextId());
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

    }
}
