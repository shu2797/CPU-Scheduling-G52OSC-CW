package cpuSched;

import java.util.*;

public class RR {

	int n = 0;
	int procComp = 0;
	int timeCounter = 0;
	int x;
	Queue<Integer> procQueue = new LinkedList<>();// queue to store processes
	static int[][] procArray;// array storing processes and their information
	int TQ, WT = 0;
	double totStartTime, totEndTime, startTime, endTime;

	public RR(int nop, int[] but, int[] art, int tq) {
		totStartTime = (long) System.nanoTime();
		TQ = tq;//time quantum
		n = nop; // number of processes
		procArray = new int[6][n];
		for (int i = 0; i < n; i++) {
			procArray[0][i] = i + 1; // process ID
			procArray[1][i] = but[i]; // process burst time
			procArray[2][i] = 0; // process temporary waiting time
			procArray[3][i] = art[i]; // process start time
			procArray[4][i] = 0;//time lap process has been processed
			procArray[5][i] = 0;//process turnaround time
		}
	}

	public void execute() {
		startTime = (long) System.nanoTime();
		if (procArray.length > 1) {// no sorting if only one element is in array
			sort(procArray);// call
		}

		while (procComp != n) {//if number of completed processes is not equal to number of processes
			for (int i = 0; i < n; i++) {
				if (procArray[3][i] == timeCounter) {//check whether arrival time matches with time reached
					if (!procQueue.contains(i)) {//if queue does not already contain process
						procQueue.add(i);
						System.out.println("Process " + procArray[0][i] + " has been enqueued");
					}
				}
			}
			if (procQueue.isEmpty() == true) {//if queue is empty
				timeCounter++;
				WT++;
			} 
			else {
				x = procQueue.remove();// dequeue process
				System.out.println("Time: " + WT);// output time reached
				System.out.println("ID of job processing: " + procArray[0][x]);
				procArray[2][x] = WT - procArray[4][x];// temporary waiting time = time elapsed - time processed

				if (procArray[1][x] > TQ) { // if runtime left is greater than time quantum
					procArray[4][x] = procArray[4][x] + TQ;// time processed = time processed + time quantum
					procArray[1][x] = procArray[1][x] - TQ;// runtime left = runtime - time quantum
					timeCounter += TQ;

					enqueueProc();
					procQueue.add(x);// enqueue again since process not completed
					WT = WT + TQ;// time elapsed = time elapsed + time quantum

				} else {// if runtime left is less than or equal to time quantum
					procArray[4][x] = procArray[4][x] + procArray[1][x];// time processed = time processed + runtime
					WT = WT + procArray[1][x];// time elapsed = time elapsed + runtime
					timeCounter += procArray[1][x];
					enqueueProc();
					procArray[5][x] = WT - procArray[3][x]; // turnaround time = time elapsed
					procArray[1][x] = 0;// process over (runtime left = 0)
					System.out.println("Process " + procArray[0][x] + " has been completed");
					procComp++;
				}
			}
		}
		sortid(procArray);//sort array 
		endTime = (long) System.nanoTime();
	}
	
	public void enqueueProc() {//enqueue processes
		for (int i = 0; i < n; i++) {
			if (procArray[3][i] <= timeCounter && i != x && procArray[1][i] > 0) {//check whether process has to be enqueued
				if (!procQueue.contains(i)) {
					procQueue.add(i);
					System.out.println("Process " + procArray[0][i] + " has been enqueued");
				}
			}
		}
	}

	//sort array according to arrival time
	public static void sort(int[][] a) {
		while (true) {
			boolean swapped = false;
			for (int i = 0; i < a[0].length - 1; i++) {
				if (a[3][i] > a[3][i + 1]) {
					swapped = true;
					for (int j = 0; j < a.length; j++) {
						int t = a[j][i];
						a[j][i] = a[j][i + 1];
						a[j][i + 1] = t;
					}
				}
			}
			if (!swapped) {
				return;
			}
		}
	}
	
	//sort array according to process ID
	public static void sortid(int[][] a) {
		while (true) {
			boolean swapped = false;
			for (int i = 0; i < a[0].length - 1; i++) {
				if (a[0][i] > a[0][i + 1]) {
					swapped = true;
					for (int j = 0; j < a.length; j++) {
						int t = a[j][i];
						a[j][i] = a[j][i + 1];
						a[j][i + 1] = t;
					}
				}
			}
			if (!swapped) {
				return;
			}
		}
	}
	
	public void display() {
		double TWT = 0;//total waiting time
		double TTT = 0;//total turnaround time
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("Process\t\tWaiting time\tTurnaround time");
		System.out.println("-----------------------------------------------");
		for(int i = 0; i < n; i++) {
			int wt = procArray[2][i] - procArray[3][i];//individual waiting time = temporary waiting time - start time
			if (wt < 0) {
				wt = 0;
			}
			System.out.println("Process " + procArray[0][i] + "\t" + wt + "\t\t" + procArray[5][i]); //output individual waiting time and turnaround time
			TWT = TWT + wt;//calculate total waiting time
			TTT = TTT + procArray[5][i];//calculate total turnaround time
		}
		System.out.println();
		System.out.println("Average waiting time: " + Math.round((TWT/n)*100.0)/100.0);//output average waiting time
		System.out.println("Average turnaround time: " + Math.round((TTT/n)*100.0)/100.0);//output average turnaround time
		totEndTime = (long) System.nanoTime();
		double dur = ((endTime - startTime) / (totEndTime - totStartTime)) * 100; //calculate cpu usage
		System.out.printf("Approximate CPU Usage: %.2f%%", dur);
	}
}