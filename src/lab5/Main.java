package lab5;

import java.util.Arrays;

public class Main
{
	
	static void SJFfindWT(int bt[], int art[], int wt[])
	{
		
		int rt[] = new int[bt.length], complete = 0, t = 0, minm = Integer.MAX_VALUE, mini = 0, endTime;
		
		boolean cheker = false;
		
		for (int i = 0; i < bt.length; i++) rt[i] = bt[i];
		
		while (complete != bt.length)
		{
			
			for (int j = 0; j < bt.length; j++)
			{
				if ((art[j] <= t) && (rt[j] < minm) && (rt[j] > 0))
				{
					
					minm = rt[j];
					
					mini = j;
					
					cheker = true;
				}
			}
			
			if (!cheker)
			{
				
				t++;
				
				continue;
			}
			
			rt[mini]--;
			
			minm = rt[mini];
			
			if (minm == 0) minm = Integer.MAX_VALUE;
			
			if (rt[mini] == 0)
			{
				
				complete++;
				
				cheker = false;
				
				endTime = t + 1;
				
				wt[mini] = endTime - bt[mini] - art[mini];
				
				if (wt[mini] < 0) wt[mini] = 0;
			}
			t++;
		}
		
	}
	
	
	static void SJFfindTAT(int bt[], int wt[], int tat[])
	{
		
		for (int i = 0; i < bt.length; i++) tat[i] = bt[i] + wt[i];
		
	}
	
	
	static void SJFavgTime(int bt[], int at[])
	{
		
		int wt[] = new int[bt.length], tat[] = new int[bt.length], total_wt = 0, total_tat = 0;
		
		SJFfindWT(bt, at, wt);
		
		SJFfindTAT(bt, wt, tat);
		
		System.out.println("\nSFJ");
		
		System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");
		
		for (int i = 0; i < bt.length; i++)
		{
			
			total_wt += wt[i];
			
			total_tat += tat[i];
			
			System.out.println(" " + (i + 1) + "\t\t" + bt[i] + "\t\t " + wt[i] + "\t\t" + tat[i]);
			
		}
		System.out.println("Average waiting time = " + (float) total_wt / bt.length);
		
		System.out.println("Average turn around time = " + (float) total_tat / bt.length);
		
	}
	
	
	static void FCFSfindWT(int bt[], int wt[])
	{
		
		wt[0] = 0;
		
		for (int i = 1; i < bt.length; i++) wt[i] = bt[i - 1] + wt[i - 1];
		
	}
	
	
	static void ROTfindWT(int bt[], int wt[], int quantum)
	{
		
		int rem_bt[] = new int[bt.length], t = 0;
		
		for (int i = 0; i < bt.length; i++) rem_bt[i] = bt[i];
		
		while (true)
		{
			boolean done = true;
			
			for (int i = 0; i < bt.length; i++)
			{
				if (rem_bt[i] > 0)
				{
					done = false;
					
					if (rem_bt[i] > quantum)
					{
						t += quantum;
						
						rem_bt[i] -= quantum;
					}
					
					else
					{
						t = t + rem_bt[i];
						
						wt[i] = t - bt[i];
						
						rem_bt[i] = 0;
					}
				}
			}
			if (done) break;
		}
		
	}
	
	
	static void NSJFfindWT(int[] bt, int wt[])
	{
		
		wt[0] = 0;
		
		for (int i = 1; i < bt.length; i++) wt[i] = bt[i - 1] + wt[i - 1];
		
	}
	
	
	static void NSJfindTAT(int bt[], int wt[], int tat[])
	{
		
		for (int i = 0; i < bt.length; i++) tat[i] = bt[i] + wt[i];
		
	}
	
	
	static void NSJFavgTime(int bt2[])
	{
		
		int[] bt = new int[bt2.length];
		
		for (int i = 0; i < bt2.length; i++)
			
			bt[i] = bt2[i];
		
		Arrays.sort(bt);
		
		int wt[] = new int[bt.length], tat[] = new int[bt.length], NSJFtotalWT = 0, NSJFtotalTAT = 0;
		
		NSJFfindWT(bt, wt);
		
		NSJfindTAT(bt, wt, tat);
		
		System.out.println("\nNSJF");
		
		System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");
		
		for (int i = 0; i < bt.length; i++)
		{
			NSJFtotalWT = NSJFtotalWT + wt[i];
			
			NSJFtotalTAT = NSJFtotalTAT + tat[i];
			
			System.out.println(" " + (i + 1) + "\t\t" + bt[i] + "\t " + wt[i] + "\t\t " + tat[i]);
		}
		
		System.out.println("Average waiting time = " + (float) NSJFtotalWT / bt.length);
		
		System.out.println("Average turn around time = " + (float) NSJFtotalTAT / bt.length);
		
	}
	
	
	static void ROTfindTAT(int bt[], int wt[], int tat[])
	{
		
		for (int i = 0; i < bt.length; i++) tat[i] = bt[i] + wt[i];
		
	}
	
	
	static void ROTavgTime(int bt[], int quantum)
	{
		
		int wt[] = new int[bt.length], tat[] = new int[bt.length], ROTtotalWT = 0, ROTtotalTAT = 0;
		
		ROTfindWT(bt, wt, quantum);
		
		ROTfindTAT(bt, wt, tat);
		
		System.out.println("\nROT");
		
		System.out.println("Processes " + " Burst time " + " Waiting time " + " Turn around time");
		
		for (int i = 0; i < bt.length; i++)
		{
			ROTtotalWT = ROTtotalWT + wt[i];
			
			ROTtotalTAT = ROTtotalTAT + tat[i];
			
			System.out.printf(" %d ", (i + 1));
			System.out.printf("           %d ", bt[i]);
			System.out.printf("         %d", wt[i]);
			System.out.printf("             %d\n", tat[i]);
		}
		System.out.println("Average waiting time = " + (float) ROTtotalWT / bt.length);
		
		System.out.println("Average turn around time = " + (float) ROTtotalTAT / bt.length);
		
		System.out.println();
		
	}
	
	
	static void FCFSfindTAT(int bt[], int wt[], int tat[])
	{
		
		for (int i = 0; i < bt.length; i++) tat[i] = bt[i] + wt[i];
		
	}
	
	
	static void FCFSavgTime(int bt[])
	{
		
		int wt[] = new int[bt.length], tat[] = new int[bt.length], NSJFtotalWT = 0, NSJFtotalTAT = 0;
		
		FCFSfindWT(bt, wt);
		
		FCFSfindTAT(bt, wt, tat);
		
		System.out.println("\nFCFS");
		
		System.out.printf("Processes Burst time Waiting" + " time Turn around time\n");
		
		for (int i = 0; i < bt.length; i++)
		{
			
			NSJFtotalWT = NSJFtotalWT + wt[i];
			
			NSJFtotalTAT = NSJFtotalTAT + tat[i];
			
			System.out.printf(" %d ", (i + 1));
			System.out.printf("            %d ", bt[i]);
			System.out.printf("         %d", wt[i]);
			System.out.printf("             %d\n", tat[i]);
		}
		
		System.out.printf("Average waiting time = %f", (float) NSJFtotalWT / bt.length);
		System.out.printf("\n");
		System.out.printf("Average turn around time = %f ", (float) NSJFtotalTAT / bt.length);
		System.out.println();
		
	}
	
	
	public static void main(String[] args)
	{
		
		int burstTime[] = {10, 5, 8 }, quantum = 2, arrTime[] = {1, 1, 1 };
		
		FCFSavgTime(burstTime);
		
		// Arrays.sort(burstTime);
		
		NSJFavgTime(burstTime);
		
		SJFavgTime(burstTime, arrTime);
		
		ROTavgTime(burstTime, quantum);
		
	}
	
}
