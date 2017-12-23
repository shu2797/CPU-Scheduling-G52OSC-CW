package cpuSched;

public class Priotity_Scheduling_Preemptive {

	int n = 0;// number of processes
	int WT = 0;// time elapsed
	int[][] procArray;
	int[][] prioArray; // array storing priority and ID of processes
	double totStartTime, totEndTime, startTime, endTime;

	public Priotity_Scheduling_Preemptive(int nop, int[][] processes) {
		totStartTime = (long) System.nanoTime();
		n = nop;
		procArray = processes;
		prioArray = new int[n][2];
	}

	public void execute() {
		startTime = (long) System.nanoTime();
		int timeCounter = 0; // time counter
		int CompleteProc = 0;// number of completed processes
		int c = 0; // row number in prioArray
		boolean found = false; // becomes true when first process to enter prioArray is found
		for (int k = 0; k < n; k++) {
			prioArray[k][0] = 256;// presets priority to prevent taking up place of higher priority processes
		}
		while (CompleteProc != n) {
			for (int j = 0; j < n; j++) {
				if (procArray[4][j] == timeCounter) {
					prioArray[c][0] = procArray[3][j];// sends process priority to prioArray
					prioArray[c][1] = procArray[0][j];// sends corresponding process ID to prioArray
					System.out.println("Job entering priority array: " + prioArray[c][1]);// outputs ID of process
					c++;
					found = true;
				}
			}
			sort(prioArray);// call
			if (found) { // if array is not empty
				WT = timeCounter;
				int procIndex = 0;// stores index of process that has to be serviced
				while (procArray[1][(prioArray[procIndex][1]) - 1] == 0) {// if burst time is 0
					procIndex++;// move to next index
				}
				int id = (prioArray[procIndex][1]);// ID of process to be serviced
				procArray[2][id - 1] = WT - procArray[5][id - 1];// temporary waiting time = time elapsed - time processed
				System.out.println("ID of job processing: " + id);// output ID of process in CPU
				procArray[1][id - 1]--;// decrease runtime left of process
				WT = WT + 1;// increment time elapsed
				procArray[6][id - 1] = WT;// temporary turnaround time = time elapsed
				procArray[5][id - 1]++;// increment time processed
				if (procArray[1][id - 1] == 0) {// if burst time is 0
					CompleteProc++;// increment number of completed processes
					System.out.println("Process " + id + " has been completed");// output id of completed process
				}
			}
			timeCounter++;// increment time
		}
		endTime = (long) System.nanoTime();
	}

	// sort prioArray according to priority
	public void sort(int[][] a) {
		while (true) {
			boolean swapped = false;
			for (int i = 0; i < a.length - 1; i++) {
				if (a[i][0] > a[i + 1][0]) {
					swapped = true;
					for (int j = 0; j < a[0].length; j++) {
						int t = a[i][j];
						a[i][j] = a[i + 1][j];
						a[i + 1][j] = t;
					}
				}
			}
			if (!swapped) {
				return;
			}
		}
	}

	public void display() {
		double TWT = 0;// total waiting time
		double TTT = 0;// total turnaround time
		System.out.println();
		System.out.println("-----------------------------------------------");
		System.out.println("Process\t\tWaiting time\tTurnaround time");
		System.out.println("-----------------------------------------------");
		for (int i = 0; i < n; i++) {
			int wt = procArray[2][i] - procArray[4][i];// individual waiting time = temporary waiting time - start time
			wt = (wt < 0) ? 0 : wt;
			//output individual waiting time and turnaround time
			System.out.println("Process " + (i + 1) + "\t" + wt + "\t\t" + (procArray[6][i] - procArray[4][i]));
			TWT = TWT + wt;// calculate total waiting time
			TTT = TTT + (procArray[6][i] - procArray[4][i]);// calculate total turnaround time
		}
		System.out.println();
		System.out.println("Average waiting time: " + Math.round((TWT / n) * 100.0) / 100.0);// output average waiting time
		System.out.println("Average turnaround time: " + Math.round((TTT / n) * 100.0) / 100.0);// output average turnaround time
		totEndTime = (long) System.nanoTime();
		double dur = ((endTime - startTime) / (totEndTime - totStartTime)) * 100; //calculate cpu usage
		System.out.printf("Approximate CPU Usage: %.2f%%", dur);
	}
}