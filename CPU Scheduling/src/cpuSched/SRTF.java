package cpuSched;

public class SRTF {

	int n, time_lap, proc_completed = 0;
	int[] bt, at, rt, wt, ord;
	int minrt = Integer.MAX_VALUE; //stores minimum running time among processes in queue
	int min = 0; // index of minimum burst time at current time lap
	boolean found = false; //boolean to indicate whether the first process has been found
	double att, awt;
	double totStartTime, totEndTime, startTime, endTime;

	public SRTF(int nop, int[] but, int[] art) {
		totStartTime = (long) System.nanoTime();
		n = nop; //number of processes
		bt = but; //burst times
		at = art; //arrival times
		wt = new int[n]; //waiting times
		rt = new int[n]; //remaining times
		ord = new int [n]; //array to id of processes
		for (int i = 0; i < n; i++) {
			ord[i] = i;
		}
		for(int i = 0; i < n; i++) {
			rt[i] = bt[i];
		}
	}

	public void findmin() {
		for (int j = 0; j < n; j++) {
			if (at[j] <= time_lap && rt[j] < minrt && rt[j] > 0) {
				min = j;
				minrt = rt[j];
				found = true; 
			}
		}
	}

	public void execute() {
		startTime = (long) System.nanoTime();
		
		System.out.println();
		while (proc_completed < n) {
			System.out.println("Time: " + time_lap);
			findmin(); //find process with minimum running time, and whose arrival time is less than current time_lap
			if (!found) {
				time_lap++; //if first process not found, increment time_lap and continue
				continue;
			}
			System.out.println("Process " + (ord[min] + 1) + " running");
			rt[min]--; //decrement remaining time
			minrt = rt[min];
			time_lap++;
			if (rt[min] == 0) { //if remaining time = 0, means process is completed
				System.out.println("Process " + (ord[min] + 1) + " completed");
				proc_completed++; //increment number of processes completed
				wt[min] = time_lap - at[min] - bt[min]; //calculate waiting time
				if (wt[min] < 0) {
					wt[min] = 0;
				}
				minrt = Integer.MAX_VALUE; //takes maximum value again
			}
			System.out.println();
			
			endTime = (long) System.nanoTime();
		}
	}

	public void calculate() {
		double ttt = 0; //total turnaround time
		double twt = 0; //total waiting time
		for (int l = 0; l < n; l++) {
			ttt = wt[l] + bt[l] + ttt;
			twt = wt[l] + twt;
		}
		att = Math.round((ttt / n) * 100.0) / 100.0; //average turnaround time
		awt = Math.round((twt / n) * 100.0) / 100.0; //average waiting time
	}

	public void display() {
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("Process\t\tWaiting time\tTurnaround time");
		System.out.println("-----------------------------------------------");
		for(int i = 0; i < n; i++) {
			System.out.println("Process " + (i + 1) + "\t" + wt[i] + "\t\t" + (wt[i] + bt[i]));
		}
		System.out.println();
		System.out.println("Average waiting time: " + awt);
		System.out.println("Average turnaround time: " + att);
		totEndTime = (long) System.nanoTime();
		double dur = ((endTime - startTime) / (totEndTime - totStartTime)) * 100; //calculate cpu usage
		System.out.printf("Approximate CPU Usage: %.2f%%", dur);
	}

}
