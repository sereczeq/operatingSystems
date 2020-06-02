package lab3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PageReplacer
{
	
	public static void main(String[] args)
	{
		
		int[] referenceString = {1, 2, 3, 4, 5, 1, 7, 1, 3, 1, 2, 3, 3, 3, 1, 5, 1, 3 };
		int frames = 3;
		System.out.println("FIFO:\t" + FIFO(referenceString, frames));
		System.out.println("OPT:\t" + OPT(referenceString, frames));
		System.out.println("LRU:\t" + LRU(referenceString, frames));
		System.out.println("ARLU:\t" + ARLU(referenceString, frames));
		
	}
	
	
	private static int FIFO(int[] ref, int numberOfFrames)
	{
		
		Queue<Integer> frames = new LinkedList<Integer>()
		{
			
			@Override
			public boolean add(Integer elem)
			{
				
				if (size() >= numberOfFrames)
				{
					poll();
				}
				return super.add(elem);
				
			}
			
		};
		
		int faults = 0;
		for (int elem : ref)
		{
			if (!frames.contains(elem))
			{
				frames.add(elem);
				faults++;
			}
		}
		return faults;
		
	}
	
	
	private static int OPT(int[] ref, int numberOfFrames)
	{
		
		int faults = 0;
		
		// this acts as easy index getter (to know how far away certain reference is)
		ArrayList<Integer> references = new ArrayList<Integer>();
		for (int x : ref) references.add(x);
		
		LinkedList<Integer> frames = new LinkedList<Integer>();
		
		for (int x = 0; x < ref.length; x++)
		{
			// set reference to -1 so get wont return it after "passing it"
			references.set(x, -1);
			int elem = ref[x];
			
			if (frames.contains(elem)) continue;
			if (frames.size() < numberOfFrames)
			{
				frames.add(elem);
			}
			else
			{
				// find farthest
				int max = -1;
				for (int temp : frames)
				{
					int index = references.indexOf(temp);
					if (index > max) max = index;
					if (index == -1)
					{
						max = -1;
						// if there is no more reference to this page, remove it
						frames.remove(frames.indexOf(temp));
						break;
					}
				}
				// remove the elements farthest away
				if (max != -1) frames.remove(frames.indexOf(references.get(max)));
				frames.add(elem);
			}
			faults++;
		}
		
		return faults;
		
	}
	
	
	private static int LRU(int[] ref, int numberOfFrames)
	{
		
		int faults = 0;
		
		Queue<Integer> frames = new LinkedList<Integer>();
		
		for (int elem : ref)
		{
			// if is there, move it to the end of the queue, no fault
			if (frames.contains(elem))
			{
				frames.remove(elem);
				frames.add(elem);
				continue;
			}
			if (frames.size() >= numberOfFrames) frames.poll();
			frames.add(elem);
			faults++;
		}
		
		return faults;
		
	}
	
	
	private static int ARLU(int[] ref, int numberOfFrames)
	{
		
		int faults = 0;
		
		class Frame
		{
			
			int frame;
			boolean used = false;
			
			public Frame(int frame)
			{
				
				this.frame = frame;
				
			}
			
			
			@Override
			public boolean equals(Object other)
			{
				
				return other instanceof Frame && frame == ((Frame) other).frame;
				
			}
			
		}
		LinkedList<Frame> frames = new LinkedList<Frame>();
		
		for (int x : ref)
		{
			Frame elem = new Frame(x);
			
			// If elem is here, mark that he is used
			if (frames.contains(elem))
			{
				frames.get(frames.indexOf(elem)).used = true;
				continue;
			}
			if (frames.size() < numberOfFrames)
			{
				frames.add(elem);
			}
			else
			{
				// if every elem is used, we choose the one that is on the first place of the
				// list (added the longest time ago)
				int looser = 0;
				for (int y = 0; y < frames.size(); y++)
				{
					// if elem is not used change this one
					if (!frames.get(y).used)
					{
						looser = y;
						break;
					}
				}
				frames.remove(looser);
				frames.add(elem);
			}
			faults++;
		}
		return faults;
		
	}
	
}
