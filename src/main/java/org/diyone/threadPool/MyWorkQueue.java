package org.diyone.threadPool;

import java.util.LinkedList;

public class MyWorkQueue {

    private  final LinkedList<Runnable> linkedList = new LinkedList<>();

    public LinkedList<Runnable> getLinkedList() {
        return linkedList;
    }

    private int limit;
    public MyWorkQueue(int limit) {
        this.limit = limit;
    }

    public Runnable take() throws InterruptedException {
        synchronized (linkedList){

            while (linkedList.isEmpty()){
                try {
                    linkedList.wait();
                } catch (InterruptedException e) {
                    throw  e;
                }
            }
            return  linkedList.removeFirst();
        }
    }


    public void offer(Runnable runnable){
        synchronized (linkedList){
            if(linkedList.size() > limit){
                //TODO 任务队列满了，可以执行拒绝策略
                //throw  new RuntimeException("");
                System.out.println("任务队列满了，可以执行拒绝策略=====");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            linkedList.addLast(runnable);
            linkedList.notifyAll();
        }
    }
}
