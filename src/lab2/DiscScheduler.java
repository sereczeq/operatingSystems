package lab2;

import java.util.Random;
import java.util.Vector;

public class DiscScheduler
{
	
	class Task implements Comparable<Task>
	{
		
		int position;
		
		Task(int position)
		{
			
			this.position = position;
			
		}
		
		
		@Override
		public int compareTo(Task other)
		{
			
			return position - other.position;
			
		}
		
		
		@Override
		public boolean equals(Object other)
		{
			
			return other instanceof Task && position == ((Task) other).position;
			
		}
		
		
		@Override
		public String toString()
		{
			
			return "" + position;
			
		}
		
	}
	
	private Vector<Task> tasks = new Vector<Task>();
	
	@SuppressWarnings("unused")
	private void addRandom()
	{
		
		addRandom(100);
		
	}
	
	
	private void addRandom(int howMany)
	{
		
		Random rand = new Random();
		for (int x = 0; x < howMany; x++) tasks.add(new Task(rand.nextInt(200)));
		
	}
	
	
	private int FCFS()
	{
		
		int time = 0;
		Vector<Task> tasks = new Vector<Task>();
		tasks.addAll(this.tasks);
		int pos = tasks.remove(0).position;
		for (Task task : tasks)
		{
			time += Math.abs(task.position - pos);
			pos = task.position;
		}
		return time;
		
	}
	
	
	private int SSTF()
	{
		
		int time = 0;
		Vector<Task> tasks = new Vector<Task>();
		tasks.addAll(this.tasks);
		
		Task curr = tasks.remove(0);
		for (int x = 0, pos = curr.position; !tasks.isEmpty(); x++, pos = curr.position)
		{
			if (tasks.contains(new Task(pos + x)))
			{
				time += x;
				curr = new Task(pos + x);
				tasks.remove(new Task(pos + x));
				x = -1;
			}
			else if (tasks.contains(new Task(pos - x)))
			{
				time += x;
				curr = new Task(pos - x);
				tasks.remove(new Task(pos - x));
				x = -1;
			}
		}
		return time;
		
	}
	
	
	private int SCAN()
	{
		
		int time = 0;
		Vector<Task> tasks = new Vector<Task>();
		tasks.addAll(this.tasks);
		
		Task curr = tasks.remove(0);
		for (int x = curr.position, pos = x; x >= 0; x--, pos = curr.position)
		{
			if (tasks.contains(new Task(x)))
			{
				time += pos - x;
				curr = new Task(x);
				tasks.remove(curr);
				x++;
			}
		}
		curr = new Task(0);
		for (int x = 0, pos = 0; x <= 200; x++, pos = curr.position)
		{
			if (tasks.contains(new Task(x)))
			{
				time += x - pos;
				curr = new Task(x);
				tasks.remove(curr);
				x--;
			}
		}
		return time;
		
	}
	
	
	private int CSCAN()
	{
		
		int time = 0;
		Vector<Task> tasks = new Vector<Task>();
		tasks.addAll(this.tasks);
		
		Task curr = tasks.remove(0);
		for (int x = curr.position, pos = x; !tasks.isEmpty(); x++, pos = curr.position)
		{
			if (x > 200)
			{
				x = 0;
				curr = new Task(0);
			}
			if (tasks.contains(new Task(x)))
			{
				time += Math.abs(x - pos);
				curr = new Task(x);
				tasks.remove(curr);
				x--;
			}
		}
		return time;
		
	}
	
	
	@Override
	public String toString()
	{
		
		String string = "Disc has " + tasks.size() + " tasks.\n";
		for (Task task : tasks) string += task + ", ";
		return string;
		
	}
	
	
	public static void main(String[] args)
	{
		
		// System.out.println(disc.toString());
		String FCFS = "FSFC\t ";
		String SSTF = "SSTF\t ";
		String SCAN = "SCAN\t ";
		String CSCAN = "C-SCAN\t ";
		for (int x = 0; x < 100; x++)
		{
			DiscScheduler disc = new DiscScheduler();
			disc.addRandom(100);
			FCFS += disc.FCFS() + "\t";
			SSTF += disc.SSTF() + "\t";
			SCAN += disc.SCAN() + "\t";
			CSCAN += disc.CSCAN() + "\t";
			// System.out.println("FCFS\t " + disc.FCFS());
			// System.out.println("SSTF\t " + disc.SSTF());
			// System.out.println("SCAN\t " + disc.SCAN());
			// System.out.println("C-SCAN\t " + disc.CSCAN());
		}
		System.out.println(FCFS);
		System.out.println(SSTF);
		System.out.println(SCAN);
		System.out.println(CSCAN);
		
	}
	
}
