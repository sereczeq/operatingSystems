package lab2;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

public class DiscScheduler
{
	
	class Task implements Comparable<Task>
	{
		
		int position;
		int deadline;
		private final Random rand = new Random();
		
		Task(int position)
		{
			
			this.position = position;
			deadline = rand.nextInt(1000);
			
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
	
	private int discSize = 200;
	private Vector<Task> tasks = new Vector<Task>();
	
	DiscScheduler(int discSize, int howManyTasks)
	{
		
		this.discSize = discSize;
		addRandom(howManyTasks);
		
	}
	
	
	@SuppressWarnings("unused")
	private void addRandom()
	{
		
		addRandom(100);
		
	}
	
	
	private void addRandom(int howMany)
	{
		
		Random rand = new Random();
		for (int x = 0; x < howMany; x++) tasks.add(new Task(rand.nextInt(discSize)));
		
	}
	
	
	// Kind of like first in first out, position of the task doesn't matter
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
	
	
	// This algorithm takes "distance" between two tasks into account and moves
	// accordingly to closest task possible
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
	
	
	// This algorithm moves the head from the first task to the very left side of
	// the
	// disc, then to the right and so on
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
		for (int x = 0, pos = 0; x <= discSize; x++, pos = curr.position)
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
	
	
	// This algorithm moves the head from the first task to the task that is closest
	// to the left side of the disc, and then to the most right task, and so on
	private int CSCAN()
	{
		
		int time = 0;
		Vector<Task> tasks = new Vector<Task>();
		tasks.addAll(this.tasks);
		
		Task curr = tasks.remove(0);
		for (int x = curr.position, pos = x; !tasks.isEmpty(); x++, pos = curr.position)
		{
			if (x > discSize)
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
	
	
	// This algorithm moved head according to task's deadline, takes longer to
	// complete, but important tasks don't wait too long
	private int EDF()
	{
		
		Collections.sort(tasks, new Comparator<Task>()
		{
			
			@Override
			public int compare(Task task1, Task task2)
			{
				
				return task1.deadline - task2.deadline;
				
			}
			
		});
		return FCFS();
		
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
		
		int discSize = 200;
		int howManyTasks = 100;
		
		String FCFS = "FSFC\t ";
		String SSTF = "SSTF\t ";
		String SCAN = "SCAN\t ";
		String CSCAN = "C-SCAN\t ";
		String EDF = "EDF\t ";
		
		for (int x = 0; x < 100; x++)
		{
			DiscScheduler disc = new DiscScheduler(discSize, howManyTasks);
			FCFS += disc.FCFS() + "\t";
			SSTF += disc.SSTF() + "\t";
			SCAN += disc.SCAN() + "\t";
			CSCAN += disc.CSCAN() + "\t";
			EDF += disc.EDF() + "\t";
		}
		System.out.println(FCFS);
		System.out.println(SSTF);
		System.out.println(SCAN);
		System.out.println(CSCAN);
		System.out.println(EDF);
		
	}
	
}
