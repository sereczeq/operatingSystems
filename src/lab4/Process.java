package lab4;

import java.util.ArrayList;
import java.util.TreeSet;

public class Process
{
	
	public ArrayList<Integer> pages;
	private ArrayList<Integer> frames;
	private ArrayList<Integer> counter;
	private int numberOfReplacements;
	private int i;
	
	public Process(ArrayList<Integer> pages)
	{
		
		this.pages = pages;
		numberOfReplacements = 0;
		i = 0;
		
	}
	
	
	public void setFrames(int amountOfFrames)
	{
		
		frames = new ArrayList<>();
		counter = new ArrayList<>();
		frames.clear();
		counter.clear();
		
		for (int i = 0; i <= amountOfFrames; i++)
		{
			frames.add(0);
			counter.add(0);
		}
		
	}
	
	
	public TreeSet<Integer> getDivide(int delta)
	{
		
		TreeSet<Integer> tree = new TreeSet<>();
		for (int i = this.i; i >= i - delta && i >= 0; i--)
		{
			tree.add(pages.get(i));
		}
		
		while (tree.size() > frames.size()) addFrame();
		
		return tree;
		
	}
	
	
	public boolean LRUOnFrames(double rate)
	{
		
		if (i == pages.size()) return false;
		int current = pages.get(i);
		int currentFrame;
		if (!isNotExist(frames, current))
		{
			for (int j = 0; j < frames.size(); j++)
			{
				if (current == frames.get(j)) counter.set(j, 0);
			}
		}
		else
		{
			currentFrame = getHighestFrame();
			frames.set(currentFrame, current);
			counter.set(currentFrame, 0);
			numberOfReplacements++;
		}
		tickCounter(counter);
		i++;
		if (rate != -1)
		{
			if (rate > numberOfReplacements % 10) removeFrame();
			if (rate < numberOfReplacements % 10) addFrame();
		}
		return true;
		
	}
	
	
	private int getHighestFrame()
	{
		
		int index = 0;
		int length = 0;
		for (int i = counter.size() - 1; i >= 0; i--)
		{
			if (length <= counter.get(i))
			{
				length = counter.get(i);
				index = i;
			}
		}
		return index;
		
	}
	
	
	public int getFramesSize()
	{
		
		return frames.size();
		
	}
	
	
	private boolean isNotExist(ArrayList<Integer> frames, int current)
	{
		
		for (int i = 0; i < frames.size(); i++)
		{
			if (current == frames.get(i)) return false;
		}
		return true;
		
	}
	
	
	private void tickCounter(ArrayList<Integer> counter)
	{
		
		for (int i = 0; i < frames.size(); i++) counter.set(i, counter.get(i) + 1);
		
	}
	
	
	public void addFrame()
	{
		
		frames.add(0);
		counter.add(i + 1);
		
	}
	
	
	public void removeFrame()
	{
		
		if (frames.size() > 1)
		{
			frames.remove(frames.size() - 1);
			counter.remove(frames.size() - 1);
		}
		
	}
	
	
	public int getNumberOfPages()
	{
		
		return pages.size();
		
	}
	
	
	public int getNumberOfReplacements()
	{
		
		return numberOfReplacements;
		
	}
	
	
	public void reset()
	{
		
		frames.clear();
		counter.clear();
		i = 0;
		numberOfReplacements = 0;
		
	}
	
	
	public String toString()
	{
		
		String ret = "";
		ret += getNumberOfPages();
		return ret;
		
	}
	
}
