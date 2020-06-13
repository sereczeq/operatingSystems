package lab5;

import java.util.Random;

public class ProcessGenerator implements Runnable
{
	
	Processor processor;
	int amount;
	
	public ProcessGenerator(int amount, Processor processor)
	{
		
		this.amount = amount;
		this.processor = processor;
		
	}
	
	
	public void start()
	{
		
	}
	
	
	@Override
	public void run()
	{
		
		Random random = new Random();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
