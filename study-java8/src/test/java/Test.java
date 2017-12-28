import java.util.concurrent.TimeUnit;

/**
 * @author cj-ch
 * @date 2017/12/15 上午12:38
 */
public class Test {
    static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        System.out.println("d");

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start!");
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + " has lock!");
                    lock.wait(5000);
                    System.out.println(Thread.currentThread().getName() + " notify!");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " exit!");

        });
        thread.start();
//        thread.join();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock){
            lock.notify();
        }
        System.out.println(Thread.currentThread().getName() + " exit!");

    }

}
