package cpuSched;

public class FCFS {

	int n, proc_completed, time_lap = 0;
	double att, awt;
	int[] bt, at, tt, rt, wt, ord;
	double totStartTime, totEndTime, startTime, endTime;

	public FCFS(int nop, int[] but, int[] art) {
		totStartTime = (long) System.nanoTime();
		n = nop; // number of processes
		bt = but; // array of burst times
		at = art; // array of arrival times
		tt = new int[n]; // array of individual turnaround times
		rt = new int[n]; // array of individual remaining times to be processed
		wt = new int[n]; // array of individual waiting times
		ord = new int[n]; // array to store order in which the processes were input (used to reorder the arrays later)
		for (int i = 0; i < n; i++) {
			ord[i] = i;
		}
	}

	public void execute() {
		startTime = (long) System.nanoTime();
		sortat(); // sort lists according to arrival times
		for (int i = 0; i < n; i++) {
			rt[i] = bt[i]; // copy burst times into remaining times
		}
		wt[0] = 0; // waiting time of first process will be 0
		System.out.println();
		time_lap = at[0]; //time_lap will take value of lowest arrival time
		while (proc_completed < n) {
			System.out.println("Time: " + time_lap);
			time_lap++; // time_lap acts like a clock
			System.out.println("Process " + (ord[proc_completed] + 1) + " running");
			if (--rt[proc_completed] == 0) { // decrement remaining time and see if it is 0 (meaning that the process has completed)
				System.out.println("Process " + (ord[proc_completed] + 1) + " completed");
				tt[proc_completed] = wt[proc_completed] + bt[proc_completed]; // calculate turnaround time
				proc_completed++; // proc_completed stores number of processes completed
				if (proc_completed < n) {
					wt[proc_completed] = time_lap - at[proc_completed]; // calculate waiting time
					if (wt[proc_completed] < 0) {
						wt[proc_completed] = 0;
					}
				}
			}
			System.out.println();
		}
		sortid(); // sort lists according to original order in which processes were input
		endTime = (long) System.nanoTime();
	}

	public void sortat() {
		int temp = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {
				if (at[j - 1] > at[j]) {
					// swap elements
					temp = at[j - 1];
					at[j - 1] = at[j];
					at[j] = temp;
					temp = bt[j - 1];
					bt[j - 1] = bt[j];
					bt[j] = temp;
					temp = ord[j - 1];
					ord[j - 1] = ord[j];
					ord[j] = temp;
				}
			}
		}
	}

	public void sortid() {
		int temp = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {
				if (ord[j - 1] > ord[j]) {
					// swap elements
					temp = at[j - 1];
					at[j - 1] = at[j];
					at[j] = temp;
					temp = bt[j - 1];
					bt[j - 1] = bt[j];
					bt[j] = temp;
					temp = ord[j - 1];
					ord[j - 1] = ord[j];
					ord[j] = temp;
					temp = wt[j - 1];
					wt[j - 1] = wt[j];
					wt[j] = temp;
					temp = tt[j - 1];
					tt[j - 1] = tt[j];
					tt[j] = temp;
				}
			}
		}
	}

	public void calculate() {
		double ttt = 0; // total turnaround time
		double twt = 0; // total waiting time
		for (int l = 0; l < n; l++) {
			ttt = wt[l] + bt[l] + ttt;
			twt = wt[l] + twt;
		}
		att = Math.round((ttt / n) * 100.0) / 100.0; // average turnaround time
		awt = Math.round((twt / n) * 100.0) / 100.0; // average waiting time
	}

	public void display() {
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("Process\t\tWaiting time\tTurnaround time");
		System.out.println("-----------------------------------------------");
		for (int i = 0; i < n; i++) {
			System.out.println("Process " + (i + 1) + "\t" + wt[i] + "\t\t" + (wt[i] + bt[i]));
		}
		System.out.println();
		System.out.println("Average waiting time: " + awt);
		System.out.println("Average turnaround time: " + att);
		totEndTime = (long) System.nanoTime();
		double dur = ((endTime - startTime) / (totEndTime - totStartTime)) * 100; //calculating cpu usage
		System.out.printf("Approximate CPU Usage: %.2f%%", dur);
	}
}