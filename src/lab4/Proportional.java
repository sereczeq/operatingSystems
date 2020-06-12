package lab4;

import java.util.ArrayList;

public class Proportional implements IAllocator
{
	
	ArrayList<Integer> faults(ArrayList<Process> processes, int amountOfFrames)
	{
		
		// giving each process proportional amount of frames
		setAllFrames(processes, getNumberOfPages(processes), amountOfFrames);
		calculateFaults(processes, 0);
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
	
	
	public void setAllFrames(ArrayList<Process> processes, int numberOfAllPages, int framesAmount)
	{
		
		for (int i = 0; i < processes.size(); i++)
		{
			processes.get(i).setFrames(processes.get(i).getNumberOfPages() * framesAmount / numberOfAllPages);
		}
		
	}
	
	
	@Override
	public void setAllFrames(ArrayList<Process> processes, int amountOfFramesGiven)
	{
		
		// TODO Auto-generated method stub
		
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
				if (processes.get(i).LRUOnFrames(-1)) goOn = true;
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
