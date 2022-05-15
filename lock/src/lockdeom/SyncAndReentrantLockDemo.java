package lockdeom;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LiuBin
 * Sync和lock有五种区别
 * 多个线程之间按照顺序调用，实现A->B->C三个线程启动
 * AA打印5次，BB打印10次，CC打印15次
 * 来10轮
 */
class ShareResource {
    private int number = 1;// A:1 B:2 C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    public void print(int count) {
        lock.lock();
        try {
            if (count == 5) {
                //判断
                while (number != 1) {
                    c1.await();
                }
                //干活
                for (int i = 1; i <=5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                //通知
                number = 2;
                c2.signal();
            }
            if (count == 10) {
                //判断
                while (number != 2) {
                    c2.await();
                }
                //干活
                for (int i = 1; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                //通知
                number = 3;
                c3.signal();
            }
            if (count == 15) {
                //判断
                while (number != 3) {
                    c3.await();
                }
                //干活
                for(int i = 1; i <= 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                //通知
                number = 1;
                c1.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(()-> {
            for (int i = 1; i <=10; i++) {
                shareResource.print(5);
            }
        },"A").start();
        new Thread(()-> {
            for (int i = 1; i <=10; i++) {
                shareResource.print(10);
            }
        },"B").start();
        new Thread(()-> {
            for (int i = 1; i <=10; i++) {
                shareResource.print(15);
            }
        },"C").start();
    }
}
