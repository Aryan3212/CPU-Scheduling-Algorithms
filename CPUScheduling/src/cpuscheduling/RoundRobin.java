package cpuscheduling;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;


public class RoundRobin {
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
        ArrayDeque<Process> readyQueue = new ArrayDeque<>();
        ArrayDeque<Process> terminatedQueue = new ArrayDeque<>();
        Process runningProcess = null;
        int CPU_time = 0;
        final int TIME_QUANTUM = 4;
        boolean preempt = true;
        while (n != terminatedQueue.size()) {
            // Check for new arrivals of Processes
            for (int i = 0; i < processList.size(); ++i) {
                if (processList.get(i).getArrivalTime() <= CPU_time) {
                    readyQueue.offer(processList.get(i));
                    System.out.println("Process with Arrival Time: " + processList.get(i).getArrivalTime());
                    System.out.println("Process with Burst Time: " + processList.get(i).getBurstTime());
                    System.out.println("Has arrived into the ready queue");
                    processList.remove(i);
                    --i;
                }
            }
            try {
                if (CPU_time > 0 && CPU_time % TIME_QUANTUM == 0 && !runningProcess.isDone()) {
                    // time to pre-empt
                    preempt = true;
                    readyQueue.offer(runningProcess);
                }
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
            // if time_quantum is finished then preempt
            if (preempt == false) {
                runningProcess.progress();
                System.out.println("Progress done: " + runningProcess.progressTime + " Burst Time: "
                        + runningProcess.getBurstTime());
            } else {
                preempt = false;
                runningProcess = readyQueue.poll();
                runningProcess.progress();
                System.out.println("Progress done: " + runningProcess.progressTime + " Burst Time: "
                        + runningProcess.getBurstTime());
            }
            CPU_time++;
            // if done then put into terminated and go to next process on next cycle
            if (runningProcess.isDone()) {
                System.out.println("Process is done, Preempting");
                preempt = true;
                runningProcess.setCompletionTime(CPU_time);
                runningProcess.setTurnaroundTime();
                runningProcess.setWaitingTime();
                terminatedQueue.add(runningProcess);
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
