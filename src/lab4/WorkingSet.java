package lab4;

import java.util.ArrayList;

public class WorkingSet implements IAllocator
{
	
	ArrayList<Integer> faults(ArrayList<Process> processes, int amountOfFrames, int delta)
	{
		
		setAllFrames(processes, getNumberOfPages(processes) / amountOfFrames);
		calculateFaults(processes, amountOfFrames, delta);
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
	
	
	public void calculateFaults(ArrayList<Process> processes, int amountOfFrames, int delta)
	{
		
		ArrayList<Integer> giveAway = new ArrayList<>();
		boolean goOn = true;
		while (goOn)
		{
			for (int i = 0; i < processes.size(); i++)
			{
				goOn = false;
				if (!checkForOverflow(processes, amountOfFrames, delta))
				{
					if (processes.get(i).LRUOnFrames(-1)) goOn = true;
					takeFrame(giveAway, processes);
				}
				else
				{
					giveAway = giveFrame(processes, processes.get(i).getFramesSize(), i);
				}
			}
		}
		
	}
	
	
	private ArrayList<Integer> giveFrame(ArrayList<Process> processes, int frames, int jump)
	{
		
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < frames && i < processes.size(); i++)
		{
			if (i != jump) processes.get(i).addFrame();
			list.add(i);
		}
		return list;
		
	}
	
	
	private void takeFrame(ArrayList<Integer> list, ArrayList<Process> processes)
	{
		
		for (int i = 0; i < list.size(); i++)
		{
			processes.get(list.remove(0)).removeFrame();
		}
		
	}
	
	
	private boolean checkForOverflow(ArrayList<Process> processes, int amountOfFrames, int delta)
	{
		
		int sum = 0;
		for (int i = 0; i < processes.size(); i++)
		{
			sum += processes.get(i).getDivide(delta).size();
		}
		if (sum > amountOfFrames) return true;
		return false;
		
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
	
	
	@Override
	public void calculateFaults(ArrayList<Process> processes, int rate)
	{
		
		// TODO Auto-generated method stub
		
	}
	
}
