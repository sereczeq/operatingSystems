package lab5;

import java.util.Random;

public class Process
{
	
	int time;
	int load;
	
	public Process()
	{
		
		Random random = new Random();
		time = random.nextInt(5);
		load = random.nextInt(10) * 10;
		
	}
	
	
	public Process(int time, int load)
	{
		
		this.time = time;
		this.load = load;
		
	}
	
	
	// if false, delete this process (it's done)
	public boolean tick()
	{
		
		return time-- > 0;
		
	}
	
}
