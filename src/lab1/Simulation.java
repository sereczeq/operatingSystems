package lab1;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Simulation
{
	
	private class Task implements Comparable<Task>
	{
		
		private int time;
		private String name;
		
		private Task(int time, String name)
		{
			
			this.time = time;
			this.name = name;
			
		}
		
		
		public String toString()
		{
			
			return name + " (" + time + ")";
			
		}
		
		
		@Override
		public int compareTo(Task o)
		{
			
			return this.time - o.time;
			
		}
		
	}
	
	private Vector<Task> tasks = new Vector<Task>();
	
	private void addTasks(Scanner scanner)
	{
		
		System.out.println("Input task time. When finished type: \"end\"");
		int num = 0;
		while (scanner.hasNext())
		{
			if (scanner.hasNextInt())
			{
				tasks.add(new Task(scanner.nextInt(), "task" + num++));
			}
			else if (scanner.next().contains("end")) break;
			
		}
		
	}
	
	
	private void randomTasks(int howMany)
	{
		
		int num = 0;
		Random rand = new Random();
		for (int x = 0; x < howMany; x++)
		{
			tasks.add(new Task((int) (rand.nextInt(400)) + 1, "task" + num++));
		}
		
	}
	
	
	private void compare()
	{
		
		System.out.println("FIFO");
		FIFO();
		System.out.println("SJF");
		SJF();
		System.out.println("ROT");
		ROT();
		
	}
	
	
	private void FIFO() // first in first out
	{
		
		Vector<Task> tasks = this.tasks;
		double time = 0;
		int prevTime = 0;
		
		Iterator<Task> it = tasks.iterator();
		while (it.hasNext())
		{
			var task = it.next();
			System.out.print(task + ", ");
			if (it.hasNext())
			{
				time += prevTime + task.time;
				prevTime += task.time;
			}
		}
		
		System.out.println("\naverage waiting time = " + time / tasks.size() + "\n");
		
	}
	
	
	private void SJF() // shortest job first
	{
		
		Vector<Task> tasks = this.tasks;
		Collections.sort(tasks);
		FIFO();
		
	}
	
	
	private void ROT()
	{
		
		Collections.sort(tasks);
		double ROTtime = tasks.get(0).time * 0.7;
		double time = 0;
		System.out.println("average waiting time = " + ROTtime);
		for (Task task : tasks)
		{
			time += (task.time / ROTtime) * ROTtime;
		}
		System.out.println("average waiting time until the end of last execution = " + time + "\n");
		
	}
	
	
	public static void main(String[] args)
	{
		
		var sim = new Simulation();
		Scanner scan = new Scanner(System.in);
		// sim.addTasks(scan);
		sim.randomTasks(20);
		sim.compare();
		scan.close();
		
	}
	
}
