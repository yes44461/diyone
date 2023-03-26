package org.diyone.threadPool;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool();

        myThreadPool.execute(()->{
            try {
                System.out.println("任务一 start");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("任务一 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        myThreadPool.execute(()->{
            try {
                System.out.println("任务二 start");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("任务二 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        myThreadPool.execute(()->{
            try {
                System.out.println("任务三 start");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("任务三 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        myThreadPool.execute(()->{
            try {
                System.out.println("任务四 start");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("任务四 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

    }
}
