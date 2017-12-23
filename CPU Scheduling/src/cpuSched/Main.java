package cpuSched;

import java.util.Scanner;

public class Main {
	static Scanner input = new Scanner(System.in);
	static int[] bt; //array to store burst times of processes
	static int[] at; //array to store arrival times of processes
	static int nop; //number of processes
	static int tq; //time quantum
	static int ch = 0; //choice
	static int [][] processes; //array of processes with data
	
	public static void main(String[] args) {
		
		System.out.println("1. First Come First Serve\n2. Shortest Job First (Preemptive)"
				+ "\n3. Round Robin\n4. Priority Scheduling (Preemptive)");
		ch = input.nextInt(); //Selection made by user

		
		switch(ch) {
		case 1:
			input1(); //call input screen interface 1
			FCFS jobf = new FCFS(nop, bt, at); //create a new job in First Come First Served
			jobf.execute(); //process the jobs
			jobf.calculate(); //calculate turnaround and waiting times
			jobf.display(); //output information on screen
			break;
			
		case 2:
			input1();
			SRTF jobs = new SRTF(nop, bt, at); //create a new job in Shortest Remaining Time First
			jobs.execute(); 
			jobs.calculate(); 
			jobs.display();
			break;
			
		case 3:
			input1();
			RR jobr = new RR(nop, bt, at, tq);
			jobr.execute();
			jobr.display();
			break;
			
		case 4:
			input2();
			Priotity_Scheduling_Preemptive jobp = new Priotity_Scheduling_Preemptive(nop, processes);
			jobp.execute();
			jobp.display();
			break;
			
		default:
			System.out.println("Error.");	
		}
		input.close();
	}
	
	public static void input1() {
		System.out.println("Enter number of processes:");
		nop = input.nextInt();
		bt = new int[nop];
		at = new int[nop];
		for(int i = 0; i < nop; i++) {
			System.out.println("Enter burst time for process " + (i + 1) + ":");
			bt[i] = input.nextInt();
			System.out.println("Enter arrival time for process " + (i + 1) + ":");
			at[i] = input.nextInt();
		}
		if(ch==3) {
			System.out.println("Enter time quantum:");
			tq = input.nextInt();
		}
	}

	public static void input2() {
		System.out.println("Enter number of processes:");
		nop = input.nextInt();
		bt = new int[nop];
		at = new int[nop];
		processes = new int[7][nop];
		for(int i = 0; i < nop; i++) {
			processes[0][i] = i + 1; //process id
			System.out.println("Enter burst time for process " + (i + 1) + ":");
			processes[1][i] = input.nextInt();//process burst time
			processes[2][i] = 0;//process temporary waiting time
			System.out.println("Enter priority for process " + (i + 1) + ":");
			System.out.println("Note: Priority Range 0-255 (smaller number has higher priority)");
			processes[3][i] = input.nextInt();//process priority
			System.out.println("Enter arrival time for process " + (i + 1) + ":");
			processes[4][i] = input.nextInt();//process start time
			processes[5][i] = 0;//time lap process has been processed
			processes[6][i] = 0;//process temporary turnaround time
		}
	}
}
