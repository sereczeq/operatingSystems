package lab5;

import java.util.Random;

public class ProcessGenerator implements Runnable
{
	
	private boolean generating = true;
	private Processor processor;
	
	private int amountOfProcesses = 10;
	private int minimumProcessTime = 2;
	private int maximumProcessTime = 5;
	private int maximumProcessCPUload = 50;
	private int timeBetweenNewProcesses = 3;
	private int second = 1000;
	
	public ProcessGenerator(Processor processor, int amountOfProcesses, int minimumProcessTime, int maximumProcessTime,
			int maximumProcessCPUload, int timeBetweenNewProcesses, int second)
	{
		
		this.processor = processor;
		this.amountOfProcesses = amountOfProcesses;
		this.minimumProcessTime = minimumProcessTime;
		this.maximumProcessTime = maximumProcessTime;
		this.maximumProcessCPUload = maximumProcessCPUload;
		this.timeBetweenNewProcesses = timeBetweenNewProcesses;
		this.second = second;
		
	}
	
	
	@Override
	public void run()
	{
		
		Random random = new Random();
		generating = true;
		for (int x = 0; x < amountOfProcesses; x++)
		{
			Process process = new Process(minimumProcessTime, maximumProcessTime, maximumProcessCPUload);
			processor.addProcess(process);
			try
			{
				Thread.sleep(random.nextInt(timeBetweenNewProcesses) * second);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		generating = false;
		
	}
	
	
	public boolean getGenerating()
	{
		
		return generating;
		
	}
	
}
