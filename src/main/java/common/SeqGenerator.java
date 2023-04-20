package common;

import java.util.concurrent.atomic.AtomicLong;

public class SeqGenerator {
    private static AtomicLong count = new AtomicLong(0);

    public static long incrementAndGet() {
        return count.incrementAndGet();
    }

    public static long get() {
        return count.get();
    }
}
