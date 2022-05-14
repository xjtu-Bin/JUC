# JUC
## 锁相关
> 公平锁
多个线程按照申请锁的顺序来获取锁；在并发环境中，每个线程在获取锁时会先查看此锁维护的等待队列，如果队列为空或者当前线程是等待队列的第一个节点，当前线程就占有锁，否则就会加入等待队列中，然后按照FIFO的规则等待出队获取锁。
> 非公平锁
多个线程获取所得顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁。在高并发的情况下，有可能会造成优先级反转或者饥饿现象。非公平锁会直接尝试获取锁，如果尝试失败，就再采用类似公平锁的方式获取锁。非公平锁的优点在于吞吐量比公平锁大。![image](https://user-images.githubusercontent.com/69098342/168426490-6f69b615-7a5f-4e60-bd5f-edaf371a9299.png)
