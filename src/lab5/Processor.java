package lab5;

import java.util.ArrayList;

public class Processor
{
	
	int requestsSent = 0;
	int requestsAccepted = 0;
	int overload = 0;
	
	int load = 100;
	
	ArrayList<Process> processes = new ArrayList<Process>();
	
	ArrayList<Processor> processors = new ArrayList<>();
	
	ProcessGenerator generator;
	
	public void setup(ArrayList<Processor> processors, int amountOfProcesses)
	{
		
		for (Processor x : processors) if (x != this) this.processors.add(x);
		generator = new ProcessGenerator(amountOfProcesses, this);
		
	}
	
	
	private boolean sendProcess(Process process)
	{
		
		for (Processor x : processors)
		{
			if (x.receiveProcess(process)) return true;
		}
		return false;
		
	}
	
	
	private boolean receiveProcess(Process process)
	{
		
		if (load + process.load < 100)
		{
			load += process.load;
			processes.add(process);
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
			boolean sent = false;
			for (Processor processor : processors)
			{
				if (processor.sendProcess(process))
				{
					sent = true;
					break;
				}
			}
			if (!sent)
			{
				overload++;
				// TODO: what to do if overloaded
			}
		}
		
	}
	
}
