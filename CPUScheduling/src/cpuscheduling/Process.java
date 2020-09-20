/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpuscheduling;

/**
 *
 * @author Aryan
 */

public class Process {
    int id = 0;
    private int arrivalTime = 0;
    private int burstTime = 0;
    private int completionTime = 0;
    private int priority = 0;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getBurstTime() {
        return this.burstTime;
    }

    public int getCompletionTime() {
        return this.completionTime;
    }

    private int waitingTime;
    private int turnaroundTime;
    int progressTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        progressTime = 0;
    }

    public void progress() {
        this.progressTime += 1;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getWaitingTime() {
        return this.waitingTime;
    }

    public void setWaitingTime() {
        this.waitingTime = turnaroundTime - burstTime;
    }

    public int getTurnaroundTime() {
        return this.turnaroundTime;
    }

    public void setTurnaroundTime() {
        this.turnaroundTime = completionTime - arrivalTime;
    }

    public void terminate(int completionTime) {
        this.completionTime = completionTime;
    }

    public boolean isDone() {
        return progressTime == burstTime;
    }
}