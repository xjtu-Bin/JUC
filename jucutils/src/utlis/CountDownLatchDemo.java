package utlis;

import java.util.concurrent.CountDownLatch;

/**
 * @author LiuBin
 * 火箭发射倒计时,做减法。秦灭六国，一统华夏。
 * 例子一closeDoor()：主线程要成其他线程运行完毕之后才能运行。比如主线程是班长，班长最后走要锁门，那么就必须等同学们都走完才可以锁门。
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //closeDoor();
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <=6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t国，被灭");
                countDownLatch.countDown();
            },CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t***********秦灭六国，一统华夏");

        //枚举类就相当于一个简易的数据库
        System.out.println(CountryEnum.ONE);//表名ONE
        System.out.println(CountryEnum.ONE.getRetCode());//ONE表的主键
        System.out.println(CountryEnum.ONE.getRetMessage());//ONE表的其他字段
    }


    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <=6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t关门");
    }
}
