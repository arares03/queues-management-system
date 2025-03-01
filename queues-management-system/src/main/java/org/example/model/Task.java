package org.example.model;

public class Task {
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int taskWaitTime;

    private int initServiceTime;


    public Task(int id, int arrivalTime, int serviceTime) {
        this.taskWaitTime = 0; //cat il tine pe el intr o coada X
        this.initServiceTime = serviceTime; // global pt suma
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public synchronized void setServiceTime(int serviceTime)
    {
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public synchronized int getServiceTime() {
        return serviceTime;
    }

    public int getTaskWaitTime() {
        return taskWaitTime;
    }

    public int getInitServiceTime(){ return initServiceTime; }

    public void setTaskWaitTime(int taskWaitTime) {
        this.taskWaitTime = taskWaitTime;
    }
}
