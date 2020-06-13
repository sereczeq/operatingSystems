package lab5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Processor implements Runnable
{
	
	private String name;
	int requestsSent = 0;
	int requestsAccepted = 0;
	int overload = 0;
	
	int load = 0;
	
	boolean working = true;
	
	Vector<Process> processes = new Vector<Process>();
	Queue<Process> awaitingProcesses = new LinkedList<Process>();
	
	ArrayList<Processor> processors = new ArrayList<>();
	
	ProcessGenerator generator;
	
	public Processor(String name)
	{
		
		this.name = name;
		
	}
	
	
	public void setup(ArrayList<Processor> processors, int amountOfProcesses)
	{
		
		for (Processor x : processors) if (x != this) this.processors.add(x);
		generator = new ProcessGenerator(amountOfProcesses, this);
		
	}
	
	
	public void run()
	{
		
		working = true;
		Thread t = new Thread(generator, name + "'s generator");
		synchronized (this)
		{
			t.start();
			
		}
		while (!(processes.isEmpty()) || generator.getGenerating() || !(awaitingProcesses.isEmpty()))
		{
			Iterator<Process> it = processes.iterator();
			while (it.hasNext())
			{
				Process process = it.next();
				if (!process.tick())
				{
					it.remove();
					load -= process.load;
				}
			}
			
			System.out.println(name + "\n" + processes);
			
			if (!awaitingProcesses.isEmpty())
			{
				addProcess(awaitingProcesses.poll());
			}
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		working = false;
		System.out.println("DONE WORKING");
		
	}
	
	
	private boolean sendProcess(Process process)
	{
		
		for (Processor x : processors)
		{
			requestsSent++;
			if (x.receiveProcess(process))
			{
				System.out.println(name + " sent");
				return true;
			}
		}
		return false;
		
	}
	
	
	private boolean receiveProcess(Process process)
	{
		
		if (load + process.load < 100)
		{
			// load += process.load;
			System.out.println(name + " received");
			awaitingProcesses.add(process);
			requestsAccepted++;
			return true;
		}
		return false;
		
	}
	
	
	void addProcess(Process process)
	{
		
		if (load + process.load < 100)
		{
			processes.add(process);
			load += process.load;
		}
		else
		{
			if (!sendProcess(process))
			{
				overload++;
				awaitingProcesses.add(process);
			}
		}
		
	}
	
	
	public boolean isWorking()
	{
		
		return working;
		
	}
	
	
	public String getName()
	{
		
		return name;
		
	}
	
}
