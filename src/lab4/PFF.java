package lab4;

import java.util.ArrayList;

public class PFF implements IAllocator
{
	
	public ArrayList<Integer> faults(ArrayList<Process> processes, int amountOfFrames, int rate)
	{
		
		setAllFrames(processes, getNumberOfPages(processes) / amountOfFrames);
		calculateFaults(processes, rate);
		return getFaults(processes);
		
	}
	
	
	@Override
	public int getNumberOfPages(ArrayList<Process> processes)
	{
		
		int sum = 0;
		for (Process process : processes)
		{
			sum += process.getNumberOfPages();
		}
		return sum;
		
	}
	
	
	@Override
	public void setAllFrames(ArrayList<Process> processes, int amountOfFramesGiven)
	{
		
		Process current;
		for (int i = 0; i < processes.size(); i++)
		{
			current = processes.get(i);
			current.setFrames(amountOfFramesGiven);
		}
		
	}
	
	
	@Override
	public void calculateFaults(ArrayList<Process> processes, int rate)
	{
		
		boolean goOn = true;
		while (goOn)
		{
			for (int i = 0; i < processes.size(); i++)
			{
				goOn = false;
				if (processes.get(i).LRUOnFrames(rate)) goOn = true;
			}
		}
		
	}
	
	
	@Override
	public ArrayList<Integer> getFaults(ArrayList<Process> processes)
	{
		
		ArrayList<Integer> results = new ArrayList<>();
		for (int i = 0; i < processes.size(); i++)
		{
			results.add(processes.get(i).getNumberOfReplacements());
			processes.get(i).reset();
		}
		return results;
		
	}
	
}
