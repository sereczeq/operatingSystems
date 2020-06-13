package lab4;

import java.util.ArrayList;

/*
 * Equal algorithm distributes frames evenly to every process, no matter how many frames it needs
 * This is not ideal, because sometimes it may even leave some unused frames, if processes don't need this many,
 * while other processes might not get enough frames
 */
public class Random implements IAllocator
{
	
	public ArrayList<Integer> faults(ArrayList<Process> processes, int amountOfFrames)
	{
		
		// giving each process random
		setAllFrames(processes, getNumberOfPages(processes) / amountOfFrames);
		calculateFaults(processes);
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
	
	
	public void calculateFaults(ArrayList<Process> processes)
	{
		
		calculateFaults(processes, 0);
		
	}
	
	
	@Override
	public void calculateFaults(ArrayList<Process> processes, int unused)
	{
		
		boolean goOn = true;
		while (goOn)
		{
			for (int i = 0; i < processes.size(); i++)
			{
				goOn = false;
				if (processes.get(i).LRU(-1)) goOn = true;
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
