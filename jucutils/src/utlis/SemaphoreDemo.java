package utlis;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuBin
 * SemaphoreDemo信号量：争车位  6个车抢三个车位
 * Semaphreo解决多个线程抢多个资源   并且Semaphreo可以代替synchronized和lock
 * 可以限流？
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <=6; i++) {
            new Thread(()-> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t抢到车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "\t离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }


}
