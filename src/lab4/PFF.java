package lab4;

import java.util.ArrayList;

/*
 * Page Fault Frequency algorithm:
 * This algorithm takes the amount of faults into consideration.
 * If process is generating a lot of faults, more frames are given to it, in order to reduce the amount of faults
 * If process is generating very few faults, frames are taken from it, to give them to more needing processes
 * Important to note: this algorithm does not prevent faults; it keeps them in control, to ensure no trashing will happen
 */

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
				if (processes.get(i).LRU(rate)) goOn = true;
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
