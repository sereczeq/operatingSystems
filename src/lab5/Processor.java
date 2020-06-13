package lab5;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Processor implements Runnable
{
	
	private String name;
	private int takeRequestsSent = 0;
	private int giveRequestsSent = 0;
	private int requestsAccepted = 0;
	private int overload = 0;
	private int underload = 0;
	private int averageCPULoad = 0;
	private int howManySeconds = 0;
	private int second = 1000;
	private int minimumTreshold = 0;
	private int maximumTreshold = 100;
	
	private int load = 0;
	private boolean canIterate = true;
	boolean working = true;
	
	Vector<Process> processes = new Vector<Process>();
	Queue<Process> awaitingProcesses = new LinkedList<Process>();
	
	ArrayList<Processor> processors = new ArrayList<>();
	
	ProcessGenerator generator;
	
	public Processor(String name, int minimumTreshold, int maximumTreshold, int amountOfProcesses,
			int minimumProcessTime, int maximumProcessTime, int maximumProcessLoad, int timeBetweenNewProcesses,
			int second)
	{
		
		this.name = name;
		this.minimumTreshold = minimumTreshold;
		this.maximumTreshold = maximumTreshold;
		this.second = second;
		generator = new ProcessGenerator(this, amountOfProcesses, minimumProcessTime, maximumProcessTime,
				maximumProcessLoad, timeBetweenNewProcesses, second);
		
	}
	
	
	public void setProcessors(ArrayList<Processor> processors)
	{
		
		for (Processor x : processors) if (x != this) this.processors.add(x);
		
	}
	
	
	public void run()
	{
		
		System.out.println(name + " started working...");
		working = true;
		Thread t = new Thread(generator, name + "'s generator");
		synchronized (this)
		{
			t.start();
			
		}
		while (!(processes.isEmpty()) || generator.getGenerating() || !(awaitingProcesses.isEmpty()))
		{
			if (!canIterate) continue;
			Iterator<Process> it = processes.iterator();
			while (it.hasNext())
			{
				Process process = it.next();
				if (!process.tick())
				{
					it.remove();
					load -= process.getLoad();
				}
			}
			
			// synchronized (this)
			// {
			if (!awaitingProcesses.isEmpty())
			{
				try
				{
					addProcess(awaitingProcesses.poll());
				}
				catch (NullPointerException e)
				{
					e.printStackTrace();
				}
			}
			// }
			
			if (load < minimumTreshold)
			{
				Process process = askForProcess();
				if (process != null)
				{
					processes.add(process);
					load += process.getLoad();
				}
			}
			
			try
			{
				Thread.sleep(second);
				averageCPULoad += load;
				howManySeconds++;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		working = false;
		System.out.println(name + " DONE WORKING");
		
	}
	
	
	private boolean sendProcess(Process process)
	{
		
		for (Processor x : processors)
		{
			takeRequestsSent++;
			if (x.receiveProcess(process)) return true;
		}
		return false;
		
	}
	
	
	private boolean receiveProcess(Process process)
	{
		
		if (load + process.getLoad() < 100)
		{
			awaitingProcesses.add(process);
			requestsAccepted++;
			return true;
		}
		return false;
		
	}
	
	
	private Process askForProcess()
	{
		
		for (Processor processor : processors)
		{
			giveRequestsSent++;
			try
			{
				for (Process process : processor.processes)
				{
					if (processor.load - process.getLoad() > minimumTreshold
							&& load + process.getLoad() < maximumTreshold)
					{
						processor.giveProcess(process);
						return process;
					}
				}
			}
			catch (ConcurrentModificationException e)
			{
				continue;
			}
		}
		underload++;
		return null;
		
	}
	
	
	synchronized void giveProcess(Process process)
	{
		
		canIterate = false;
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		requestsAccepted++;
		processes.remove(process);
		load -= process.getLoad();
		canIterate = true;
		
	}
	
	
	void addProcess(Process process)
	{
		
		if (load + process.getLoad() < maximumTreshold)
		{
			processes.add(process);
			load += process.getLoad();
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
	
	
	public int getTakeRequestsSent()
	{
		
		return takeRequestsSent;
		
	}
	
	
	public int getGiveRequestsSent()
	{
		
		return giveRequestsSent;
		
	}
	
	
	public int getRequestsAccepted()
	{
		
		return requestsAccepted;
		
	}
	
	
	public int getOverload()
	{
		
		return overload;
		
	}
	
	
	public int getUnderLoad()
	{
		
		return underload;
		
	}
	
	
	public int getAverageCPULoad()
	{
		
		return averageCPULoad / howManySeconds;
		
	}
	
}
