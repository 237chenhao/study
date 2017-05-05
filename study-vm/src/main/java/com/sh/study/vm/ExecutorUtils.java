package com.sh.study.vm;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenhao on 2016/9/26.
 */
public class ExecutorUtils {
    private static Object object = new Object();
    private static final long default_timeout_second = 5L;
    static final int corePoolSize = 2;
    static final int maximumPoolSize = 2;
    static final int workQueueCapacity = 10;
    private static ThreadPoolExecutor timingExecutorService;
    private static ThreadPoolExecutor executorService;
    private static MyThreadFactory myThreadFactory = new MyThreadFactory("exec-t-");

    private static final void init() {
        synchronized (object) {
            if (executorService == null) {
                timingExecutorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                        0l, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<>(workQueueCapacity),
                        new MyThreadFactory("timing-thread"));
                executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                        0l,
                        TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(workQueueCapacity),
                        new MyThreadFactory("service-thread"));
            }
        }
    }

    public static final Future submit(final Callable callable) {
        return submit(callable, null, default_timeout_second);
    }

    public static final Future submit(final Callable callable, final ExecutorCallBack executorCallBack) {
        return submit(callable, executorCallBack, default_timeout_second);
    }

    public static final Future submit(final Callable callable, long timeoutSecond) {
        return submit(callable, null, timeoutSecond);
    }

    public static final Future submit(final Callable callable, final ExecutorCallBack executorCallBack, long timeoutSecond) {
        init();
        return timingExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Future future = executorService.submit(callable);
                Object execObj = null;
                boolean success = true;
                String remark = "";
                try {
                    execObj = future.get(timeoutSecond, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    //超时
//                    System.out.println("超时，强制中断: "+future.cancel(false));
//                    System.out.println("超时");
                    executorService.purge();
                    remark = "超时";
                    success = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    //不可预料的异常
                    remark = e.getMessage();
                    success = false;
                }
                if (null != executorCallBack) {
                    executorCallBack.process(execObj, success, remark);
                }
            }
        });
    }

    public static final void execute(Runnable runnable) {
        init();
        executorService.execute(runnable);
    }

    static class MyThreadFactory implements ThreadFactory {
        private final AtomicInteger count = new AtomicInteger(0);
        private final String prefixName;

        public MyThreadFactory(String prefixName) {
            this.prefixName = prefixName;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(prefixName + "-" + count.incrementAndGet());
            return thread;
        }
    }


    static class MyCallable implements Runnable {
        private int num;

        public MyCallable(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println("=====" + num + "=====");
            while (1 == 1) {

            }
//            return 0;
        }
    }

    static class Worker implements Callable {
        private Runnable runnable;
        private long timeout;
        private TimeUnit unit;

        public Worker(Runnable runnable, long timeout, TimeUnit unit) {
            this.runnable = runnable;
            this.timeout = timeout;
            this.unit = unit;
        }

        @Override
        public Object call() {
            try {
                Thread t = myThreadFactory.newThread(runnable);
                t.start();
                return waitFor(t, timeout, unit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        public boolean waitFor(Thread t, long timeout, TimeUnit unit)
                throws InterruptedException {
            long startTime = System.nanoTime();
            long rem = unit.toNanos(timeout);

            do {
                try {
                    if (t.getState().equals(Thread.State.TERMINATED))
                        return true;
                } catch (IllegalThreadStateException ex) {
                    if (rem > 0)
                        Thread.sleep(
                                Math.min(TimeUnit.NANOSECONDS.toMillis(rem) + 1, 100));
                }
                rem = unit.toNanos(timeout) - (System.nanoTime() - startTime);
            } while (rem > 0);

            if(!t.isInterrupted()){
                System.out.println("time out:"+t.getName());
                t.stop();
            }
            System.out.println(t.getName()+" isalive:"+t.isAlive() +" isInterrupted:"+t.isInterrupted());
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        init();
        int i = 0;
        while (++i < 10) {
//            System.out.println("提交" + i);
            executorService.submit(new Worker(new MyCallable(i), 5, TimeUnit.SECONDS));
//            System.out.println("提交" + i + "完成");
        }

        System.out.println("end");
        TimeUnit.SECONDS.sleep(15);
        executorService.shutdownNow();

    }
}
