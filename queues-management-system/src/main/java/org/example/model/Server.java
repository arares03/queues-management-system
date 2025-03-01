package org.example.model;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Server implements Runnable {
    private LinkedBlockingQueue<Task> tasks;
    private AtomicInteger waitingTime;
    private CyclicBarrier barrier;

    public Server(CyclicBarrier barrier) {
        this.tasks = new LinkedBlockingQueue<>();
        this.waitingTime = new AtomicInteger(0);
        this.barrier = barrier;
    }

    public LinkedBlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    @Override
    public String toString() {
        String result = "Server Task Queue:\n";
        for (Task task : tasks) {
            result += "(" + task.getId() + "," + task.getArrivalTime() + "," + task.getServiceTime() + ")  ";
        }
        return result;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                if (!tasks.isEmpty()) {
                    waitingTime.decrementAndGet();

                    Task topTask = tasks.peek();
                    int currentServiceTime = topTask.getServiceTime();
                    currentServiceTime--;
                    topTask.setServiceTime(currentServiceTime);

                    if (currentServiceTime == 0) {
                        tasks.take();
                    }
                }

                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTask(Task task) {
        try {
            this.tasks.put(task);
            this.waitingTime.addAndGet(task.getServiceTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isActive(){
        return (!tasks.isEmpty());
    }
}
