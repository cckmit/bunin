package my.bunin.util;

import java.util.Optional;

@SuppressWarnings("FieldCanBeLocal")
public class SnowFlake {

    private final static long EPOCH = 1288834974657L;

    public final static long DEFAULT_WORKER_ID_BITS = 5L;
    public final static long DEFAULT_DATA_CENTER_ID_BITS = 5L;
    public final static long DEFAULT_SEQUENCE_BITS = 12L;

    private final long workerIdBits;
    private final long dataCenterIdBits;
    private final long sequenceBits;

    private final long maxWorkerId;
    private final long maxDataCenterId;

    private final long workerIdShift;
    private final long dataCenterIdShift;
    private final long timestampLeftShift;

    private final long sequenceMask;

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    private final long workerId;
    private final long dataCenterId;

    public SnowFlake(long workerId, long dataCenterId) {
        this(workerId, dataCenterId, null, null, null);
    }

    public SnowFlake(long workerId, long dataCenterId, Long workerIdBits,
                     Long dataCenterIdBits, Long sequenceBits) {

        this.workerIdBits = Optional.ofNullable(workerIdBits).orElse(DEFAULT_WORKER_ID_BITS);
        this.dataCenterIdBits = Optional.ofNullable(dataCenterIdBits).orElse(DEFAULT_DATA_CENTER_ID_BITS);
        this.sequenceBits = Optional.ofNullable(sequenceBits).orElse(DEFAULT_SEQUENCE_BITS);

        this.maxWorkerId = ~(-1L << this.workerIdBits);
        this.maxDataCenterId = ~(-1L << this.dataCenterIdBits);

        this.workerIdShift = this.sequenceBits;
        this.dataCenterIdShift = this.sequenceBits + this.workerIdBits;
        this.timestampLeftShift = this.sequenceBits + this.workerIdBits + this.dataCenterIdBits;

        this.sequenceMask = ~(-1L << this.sequenceBits);

        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("dataCenter Id can't be greater than %d or less than 0",
                            maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;

    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception(
                        String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                                lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - EPOCH) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
