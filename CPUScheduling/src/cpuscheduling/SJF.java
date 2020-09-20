package cpuscheduling;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class Priority implements Comparator<Process> { 
    @Override
    public int compare(Process p1, Process p2) 
    { 
        if (p1.getBurstTime() > p2.getBurstTime()) 
                    return 1; 
                else if (p1.getBurstTime() < p2.getBurstTime()) 
                    return -1;
        return 0; 
    } 
}
public class SJF{
    

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Process> processList = new ArrayList<>();
        int j = n;
        while (j > 0) {

            int id = sc.nextInt();
            int arr = sc.nextInt();
            int burst = sc.nextInt();
            processList.add(new Process(id, arr, burst));
            j--;
        }
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(new Priority());
        ArrayDeque<Process> terminatedQueue = new ArrayDeque<Process>();
        Process runningProcess = null;
        int CPU_time = 0;
        //boolean preempt = true;
        while (n != terminatedQueue.size()) {
            // Check for new arrivals of Processes to add to ready queue
            for (int i = 0; i < processList.size(); ++i) {
                if (processList.get(i).getArrivalTime() <= CPU_time) {
                    readyQueue.add(processList.get(i));
                    System.out.println("Process with Arrival Time: " + processList.get(i).getArrivalTime());
                    System.out.println("Process with Burst Time: " + processList.get(i).getBurstTime());
                    System.out.println("Has arrived into the ready queue");
                    processList.remove(i);
                    --i;
                }
            }
            //check for processes with higher priority
            try{
                if(runningProcess == null){
                runningProcess = readyQueue.poll();
                System.out.println("Process: "+runningProcess.id+" with Burst Time: "+runningProcess.getBurstTime()+" is running now!");
                }
            }catch(Exception e){
                System.out.println("Empty Queue trying to poll");
            }
            try{
                if(!readyQueue.isEmpty() && ((readyQueue.peek().getBurstTime()-readyQueue.peek().progressTime)<(runningProcess.getBurstTime()-runningProcess.progressTime))){
                    readyQueue.add(runningProcess);
                    runningProcess = readyQueue.poll();
                    System.out.println("!!Prempted!!");
             }
           }catch(Exception e){
               System.out.println("Empty Queue trying to Preempt");
           }
           runningProcess.progress();
           System.out.println("Progress done: " + runningProcess.progressTime + " Burst Time: "
                        + runningProcess.getBurstTime());
            CPU_time++;
            // if done then put into terminated and go to next process on next cycle
            if (runningProcess.isDone()) {
                System.out.println("Process is done, Preempting");
                System.out.println("!!Prempted!!");
                runningProcess.setCompletionTime(CPU_time);
                runningProcess.setTurnaroundTime();
                runningProcess.setWaitingTime();
                terminatedQueue.add(runningProcess);
                runningProcess = readyQueue.poll();
            }
        }
        System.err.println();
        System.out.println("ALL PROCESSES ARE COMPLETE");
        System.err.println();
        double avgTAtime = 0.0;
        double avgWtime = 0.0;
        for (Process p : terminatedQueue) {
            System.out.println("Process ID: " + p.id);

            System.out.println("==================================");
            System.out.println("Process Completion Time: " + p.getCompletionTime());
            System.out.println("Process Waiting Time: " + p.getWaitingTime());
            avgWtime += p.getWaitingTime();
            System.out.println("Process Turnaround Time: " + p.getTurnaroundTime());
            avgTAtime += p.getTurnaroundTime();
            System.out.println("==================================");
        }
        avgTAtime = avgTAtime / n;
        avgWtime = avgWtime / n;
        System.out.println("Average Waiting Time: " + avgWtime + "\nAverage Turnaround Time: " + avgTAtime);
    }
}