package lockdeom;

import com.sun.security.auth.NTSidPrimaryGroupPrincipal;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author LiuBin
 * 自旋锁demo。利用原子引用的CAS。
 * 首先线程A通过CAS获取锁成功，不会陷入While循环，A线程沉睡5秒。B线程尝试获取锁，CAS不成功，就开始在While自选。
 * 等待A线程释放锁才能获取。
 * A	 come in
 * B	 come in
 * Thread[A,5,main]	 release lock
 * Thread[B,5,main]	 release lock
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in");
        while (!atomicReference.compareAndSet(null,thread)) {

        }
    }

    public void myUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread()+"\t release lock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()-> {
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnlock();
        },"A").start();
        //为了保障线程A先获取锁
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.myLock();
            spinLockDemo.myUnlock();
        },"B").start();
    }


}
