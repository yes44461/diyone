package org.diyone.threadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    private int initSize = 2;//初始大小

    private int maxSize = 5;//最大线程数

    private int minSize = 1; //最小线程数

    private int activeSize = 0;//当前活跃的线程数

    private  MyWorkQueue myWorkQueue; //任务队列

    private int queueSize = 10; //队列大小

    //线程池
    private LinkedList<ThreadItem> threadPool = new LinkedList<>();

    //线程工厂
    private final ThreadFactory threadFactory;

    public MyThreadPool() {
        this.myWorkQueue = new MyWorkQueue(queueSize);
        threadFactory = new ThreadFactory();
        init();
        new Thread(this::start,"check").start();
    }


    public void init() {
        //初始化线程池
        for (int i = 0; i < initSize; i++) {
            ThreadFactory.ThreadTask threadTask = new ThreadFactory.ThreadTask(myWorkQueue);
            Thread thread = threadFactory.createThread(threadTask);
            threadPool.add(new ThreadItem(threadTask, thread));
            activeSize++;
            thread.start();
        }
    }


    private static class ThreadItem{
        public final ThreadFactory.ThreadTask threadTask;
        public final Thread thread;

        public ThreadItem(ThreadFactory.ThreadTask threadTask, Thread thread) {
            this.threadTask = threadTask;
            this.thread = thread;
        }
    }


    public void execute(Runnable runnable){
        this.myWorkQueue.offer(runnable);
    }

    public synchronized void start(){
         while (true){
             System.out.println("activeSize=" + activeSize);
             //每隔10s检测一次
             try {
                 TimeUnit.SECONDS.sleep(1);
                 System.out.println("start check");
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }


             //如果任务队列有任务，且线程池没有到达最大值，则继续创建线程
             if(myWorkQueue.getLinkedList().size() >0 && activeSize < maxSize){
                 ThreadFactory.ThreadTask threadTask = new ThreadFactory.ThreadTask(myWorkQueue);
                 Thread thread = threadFactory.createThread(threadTask);
                 threadPool.add(new ThreadItem(threadTask, thread));
                 activeSize++;
                 thread.start();
             }


             //这边其实还有核心线程大小跟任务队列的比较，懒得写了

             //如果任务队列已经空了，则删除线程
             if(myWorkQueue.getLinkedList().size() ==0 && activeSize > minSize){
                 ThreadItem threadItem = threadPool.removeFirst();
                 threadItem.threadTask.stop();
                 activeSize--;
             }

         }
    }
}
