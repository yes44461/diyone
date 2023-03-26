package org.diyone.threadPool;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactory {


    private static final AtomicInteger groupCounter = new AtomicInteger(1);
    private static final AtomicInteger threadCounter = new AtomicInteger(1);
    


    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("MyThreadPool-" + groupCounter.getAndIncrement());


    public  Thread createThread(ThreadTask threadTask){
        return new Thread(THREAD_GROUP, threadTask,"MyThreadPool-tread-" + threadCounter.getAndIncrement());
    }



    public static class ThreadTask implements Runnable{

        private final MyWorkQueue myWorkQueue;
        private volatile boolean isRun = true;
        public  ThreadTask(MyWorkQueue myWorkQueue){

            this.myWorkQueue = myWorkQueue;
        }



        @Override
        public void run() {
            while (isRun && !Thread.currentThread().isInterrupted()){
                try {
                    Runnable take = this.myWorkQueue.take();
                    take.run();
                }catch (InterruptedException e){
                    isRun = false;
                    break;
                }

            }
        }

        public void stop(){
            this.isRun = false;
        }
    }
}
