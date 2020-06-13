package lab5;

import java.util.Random;

public class ProcessGenerator implements Runnable
{
	
	private Processor processor;
	private int amount;
	
	private boolean generating = true;
	
	public ProcessGenerator(int amount, Processor processor)
	{
		
		this.amount = amount;
		this.processor = processor;
		
	}
	
	
	@Override
	public void run()
	{
		
		Random random = new Random();
		generating = true;
		for (int x = 0; x < amount; x++)
		{
			Process process = new Process();
			processor.addProcess(process);
			try
			{
				Thread.sleep(random.nextInt(5) * 1000);
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
