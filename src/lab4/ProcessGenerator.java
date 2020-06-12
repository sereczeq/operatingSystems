package lab4;

import java.util.ArrayList;
import java.util.Random;

public class ProcessGenerator
{
	
	public static ArrayList<Process> generate(int amountOfProcesses, int amountOfPages, int range)
	{
		
		ArrayList<Process> processes = new ArrayList<>();
		PagesGenerator pg = new PagesGenerator();
		Random random = new Random();
		
		for (int i = 0; i < amountOfProcesses; i++)
		{
			processes.add(new Process(pg.generate(amountOfPages + random.nextInt(amountOfPages), range)));
		}
		return processes;
		
	}
	
}
