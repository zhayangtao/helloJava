package javarandom;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zhayangtao
 *         2017/11/29
 */
public class JavaRandomTest {
    public static final long COUNT = 10000000L;
    public static final int THREADS = 2;

    public static void main(String[] args) {
        System.out.println();
    }

    private static class RandomTask implements Runnable {

        private final int id;
        private final Random random;
        private final long cnt;
        private final CountDownLatch latch;

        private RandomTask(Random random, int id, long cnt, CountDownLatch latch) {
            this.random = random;
            this.id = id;
            this.cnt = cnt;
            this.latch = latch;
        }

        protected Random getRandom() {
            return random;
        }

        @Override
        public void run() {
            try {
                final Random random = getRandom();
                latch.countDown();
                latch.await();
                final long start = System.currentTimeMillis();
                int sum = 0;
                for (int i = 0; i < cnt; i++) {
                    sum += random.nextInt();
                }
                final long time = System.currentTimeMillis() - start;
                System.out.println("Thread #" + id + " Time = " + time / 1000.0 + " sec, sum = " + sum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void testRandom(final int threads, final long cnt) {
        final CountDownLatch latch = new CountDownLatch(threads);
        final Random random = new Random(100);
        for (int i = 0; i < threads; i++) {
            final Thread thread = new Thread(new RandomTask(random, i, cnt, latch));
            thread.start();
        }
    }

    private static void testRandomArray(final int threads, final long cnt, final int padding) {
        final CountDownLatch latch = new CountDownLatch(threads);
        final Random[] randoms = new Random[threads * padding];
        for (int i = 0; i < threads * padding; i++) {
            randoms[i] = new Random(100);
        }
        for (int i = 0; i < threads; i++) {
            final Thread thread = new Thread(new RandomTask(randoms[i * padding], i, cnt, latch));
            thread.start();
        }
    }

    private static void testThreadLocalRandom(final int threads, final long cnt) {
        final CountDownLatch latch = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            final Thread thread = new Thread(new RandomTask(null, i, cnt, latch) {
                @Override
                protected Random getRandom() {
                    return ThreadLocalRandom.current();
                }
            });
            thread.start();
        }
    }

    private static void testThreadLocal_Random(final int threads, final long cnt) {
        final CountDownLatch latch = new CountDownLatch(threads);
        final ThreadLocal<Random> randomThreadLocal = new ThreadLocal<Random>() {
            @Override
            protected Random initialValue() {
                return new Random(100);
            }
        };
        for (int i = 0; i < threads; i++) {
            final Thread thread = new Thread(new RandomTask(null, i, cnt, latch) {
                @Override
                protected Random getRandom() {
                    return randomThreadLocal.get();
                }
            });
            thread.start();
        }
    }
}
