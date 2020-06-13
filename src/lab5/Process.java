package lab5;

import java.util.Random;

public class Process
{
	
	private int time;
	private int load;
	
	public Process(int minimumProcessTime, int maximumProcessTime, int maximumProcessCPULoad)
	{
		
		Random random = new Random();
		time = random.nextInt(maximumProcessTime - minimumProcessTime) + minimumProcessTime;
		load = random.nextInt(maximumProcessCPULoad / 10) * 10; // so it's always multiple of 10
		
	}
	
	
	// if false, delete this process (it's done)
	public boolean tick()
	{
		
		return time-- > 0;
		
	}
	
	
	public int getLoad()
	{
		
		return load;
		
	}
	
	
	@Override
	public String toString()
	{
		
		return "Process: time - " + time + ", load - " + load;
		
	}
	
}
