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
	private boolean print = false;
	
	private int load = 0;
	private boolean canIterate = true;
	boolean working = true;
	
	// for processes being processed
	Vector<Process> processes = new Vector<Process>();
	// for generated processes, that could not have been processed be neither this,
	// or any other processors
	Queue<Process> awaitingProcesses = new LinkedList<Process>();
	// list of other processors (for sending and receiving tasks)
	ArrayList<Processor> processors = new ArrayList<>();
	// process generator
	ProcessGenerator generator;
	
	public Processor(String name, int minimumTreshold, int maximumTreshold, int amountOfProcesses,
			int minimumProcessTime, int maximumProcessTime, int maximumProcessLoad, int timeBetweenNewProcesses,
			int second, boolean print)
	{
		
		this.name = name;
		this.minimumTreshold = minimumTreshold;
		this.maximumTreshold = maximumTreshold;
		this.second = second;
		this.print = print;
		generator = new ProcessGenerator(this, amountOfProcesses, minimumProcessTime, maximumProcessTime,
				maximumProcessLoad, timeBetweenNewProcesses, second, print);
		
	}
	
	
	public void setProcessors(ArrayList<Processor> processors)
	{
		
		for (Processor x : processors) if (x != this) this.processors.add(x);
		
	}
	
	
	public void run()
	{
		
		System.out.println(name + " started working...");
		
		working = true;
		
		// starting process generator
		Thread t = new Thread(generator, name + "'s generator");
		synchronized (this)
		{
			t.start();
			
		}
		
		/*
		 * processor can stop working only if all three conditions are met: 1. it has
		 * finished all processes it has 2. generator does not create any new processes
		 * 3. there are no awaiting processes
		 */
		
		while (!(processes.isEmpty()) || generator.getGenerating() || !(awaitingProcesses.isEmpty()))
		{
			if (!canIterate) continue; // my attempt at fixing some bugs due to multi-threading
			// take second of each task
			Iterator<Process> it = processes.iterator();
			while (it.hasNext())
			{
				try
				{
					Process process = it.next();
					// if task is finished, remove it
					if (!process.tick())
					{
						it.remove();
						load -= process.getLoad();
					}
				}
				catch (ConcurrentModificationException e)
				{
					System.err.println("error occured :( timing might be incorrect");
				}
			}
			
			// check if there are any processes awaiting, if there are, start processing
			// them
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
			
			// if current CPU load is smaller than minimum threshold, send requests for
			// processes
			if (load < minimumTreshold)
			{
				Process process = askForProcess();
				// if found process to take, add it
				if (process != null)
				{
					processes.add(process);
					load += process.getLoad();
				}
			}
			
			try
			{
				// simulating time
				Thread.sleep(second);
				averageCPULoad += load;
				howManySeconds++;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			if (print) System.out
					.println("\n" + name + "'s current load is: " + load + ", it's processes:\n" + processes + "\n");
		}
		working = false;
		System.out.println(name + " DONE WORKING");
		
	}
	
	
	// return true if any processor has "agreed" to take this task
	private boolean sendProcess(Process process)
	{
		
		for (Processor x : processors)
		{
			takeRequestsSent++;
			if (x.receiveProcess(process)) return true;
		}
		return false;
		
	}
	
	
	// return true if this processor has "agreed" to take the task
	// this is requested by other processor
	private boolean receiveProcess(Process process)
	{
		
		if (load + process.getLoad() < maximumTreshold)
		{
			// added to awaiting processes to eliminate bugs, it is getting immediately
			// added to main processes in run method
			awaitingProcesses.add(process);
			requestsAccepted++;
			if (print) System.out.println(name + " accepted " + process);
			return true;
		}
		return false;
		
	}
	
	
	// send request for Processes to each processor
	private Process askForProcess()
	{
		
		if (print) System.out.println(name + "is asking for processes");
		for (Processor processor : processors)
		{
			giveRequestsSent++;
			try
			{
				for (Process process : processor.processes)
				{
					// if by taking this process, the other processor will not go below it's minimum
					// threshold
					// and this processor will not go over maximum threshold
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
		// if none of the processors had any tasks to give, mark as underloaded
		underload++;
		return null;
		
	}
	
	
	// give one of processor's processes to other processor
	synchronized void giveProcess(Process process)
	{
		
		// attempt at fixing bugs
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
		if (print) System.out.println(name + " gave away a process " + process);
		canIterate = true;
		
	}
	
	
	// adding process from process generator (or awaiting queue)
	void addProcess(Process process)
	{
		
		// if by adding process processor will not go over threshold, can add
		if (load + process.getLoad() < maximumTreshold)
		{
			processes.add(process);
			load += process.getLoad();
		}
		else
		{
			// else try to send the process to other processors
			if (!sendProcess(process))
			{
				// if none of the processors is free, add process to awaiting queue, mark as
				// overloaded
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
