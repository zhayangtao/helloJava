package javathread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhayangtao
 * 2017/11/25
 */
public class JavaThreadTest {

    public static void main(String[] args) {
        JavaThreadTest testJavaThread = new JavaThreadTest();
//        testJavaThread.testThread();
//        testJavaThread.testExecutor();
        testJavaThread.testCallable();
    }

    private void testThread() {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };
        task.run();

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done");

        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Foo " + name);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread1 = new Thread(runnable);
        thread1.start();

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future future = executorService.submit(task);
        try {
            System.out.println("future=" + future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void testExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            String name = Thread.currentThread().getName();
            System.out.println("Hello " + name);
        });
        try {
            System.out.println("attempt to shutdown executor");
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!executorService.isTerminated()) {
                System.out.println("cancel non-finished tasks");
            }
            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    private void testCallable() {
        Callable<Integer> task = () -> {
            TimeUnit.SECONDS.sleep(1);
            return 123;
        };
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(task);
        System.out.println("future done? " + future.isDone());
        try {
            Integer result = future.get(1, TimeUnit.SECONDS);
            System.out.println("future done? " + future.isDone());
            System.out.println("result: " + result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        ExecutorService executorService1 = Executors.newWorkStealingPool();
        List<Callable<String>> callableList = Arrays.asList(() -> "task1", () -> "task2", () -> "task3");
        try {
            executorService1.invokeAll(callableList).stream().map(stringFuture -> {
                try {
                    return stringFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new IllegalStateException(e);
                }
            }).forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
