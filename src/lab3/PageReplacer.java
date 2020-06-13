package lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class PageReplacer
{
	
	public static void main(String[] args)
	{
		
		int frames = 3;
		
		int amountOfReferences = 50;
		int locality = 8;
		
		int[] referenceString = {1, 2, 3, 4, 5, 1, 7, 1, 3, 1, 2, 3, 3, 3, 1, 5, 1, 3 };
		// can use either pre-generated reference string or generate new one
		referenceString = generateReferences(amountOfReferences, locality);
		
		System.out.println("FIFO:\t" + FIFO(referenceString, frames));
		System.out.println("OPT:\t" + OPT(referenceString, frames));
		System.out.println("LRU:\t" + LRU(referenceString, frames));
		System.out.println("ARLU:\t" + ARLU(referenceString, frames));
		System.out.println("RAND:\t" + RAND(referenceString, frames));
		
	}
	
	
	// Due to chosen aproach the references could be negative
	// I know that in real case scenarios it doesn't happen, but it does not change
	// anyting in this simulation
	private static int[] generateReferences(int amount, int locality)
	{
		
		int[] reference = new int[amount];
		Random random = new Random();
		reference[0] = amount / locality;
		System.out.println("references: ");
		for (int x = 1; x < amount; x++)
		{
			reference[x] = reference[x - 1] + random.nextInt(locality) - random.nextInt(locality);
			System.out.print(reference[x - 1] + ", ");
		}
		System.out.println();
		return reference;
		
	}
	
	
	// First in first out
	// if all frames are taken, removes the one that was added the longest time ago
	private static int FIFO(int[] ref, int numberOfFrames)
	{
		
		Queue<Integer> frames = new LinkedList<Integer>()
		{
			
			@Override
			public boolean add(Integer elem)
			{
				
				// every time program tries to add new frame, but there is no place to add it
				// first frame is cleared and new frame is added
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
	
	
	// the most optimal algorithm
	// it finds the frame that will be used last, and removes it
	public static int OPT(int[] ref, int numberOfFrames)
	{
		
		int faults = 0;
		
		// this acts as easy index getter (to know how far away certain reference is)
		ArrayList<Integer> references = new ArrayList<Integer>();
		for (int x : ref) references.add(x);
		
		LinkedList<Integer> frames = new LinkedList<Integer>();
		
		for (int x = 0; x < ref.length; x++)
		{
			// set reference to -1 so "get" won't return it after "passing it"
			references.set(x, -1);
			int elem = ref[x];
			
			// if the element is there, find another one
			if (frames.contains(elem)) continue;
			// if not all frames are taken, just add element
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
	
	
	// Least Recently Used:
	// algorithm assumes that if page was not in use for a long time, the process
	// will not need it any more
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
	
	
	// Second chance algorithm
	// Works similarly to FIFO, but marks how often pages are used,
	// FIFO replaces the page that was added the longest time ago,
	// ALRU replaces the page that was added the longest time ago and hasn't been
	// used since
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
			
			// If elem is here, change it's "used" field to true
			if (frames.contains(elem))
			{
				frames.get(frames.indexOf(elem)).used = true;
				continue;
			}
			// if not all frames are in use, add element without removing others
			if (frames.size() < numberOfFrames)
			{
				frames.add(elem);
			}
			else
			{
				// if every frame is used, we choose the one that is on the first place of the
				// list (added the longest time ago)
				int looser = 0;
				for (int y = 0; y < frames.size(); y++)
				{
					// find element that hasn't been used, mark it as looser
					if (!frames.get(y).used)
					{
						looser = y;
						break;
					}
				}
				// remove looser element and add new
				frames.remove(looser);
				frames.add(elem);
			}
			faults++;
		}
		return faults;
		
	}
	
	
	// RAND: algorithm chooses random page to replace
	private static int RAND(int[] ref, int numberOfFrames)
	{
		
		int faults = 0;
		ArrayList<Integer> frames = new ArrayList<Integer>();
		
		for (int elem : ref)
		{
			if (!(frames.contains(elem)))
			{
				if (frames.size() < numberOfFrames)
				{
					frames.add(elem);
				}
				else
				{
					// the randomizing part
					Collections.shuffle(frames);
					frames.set(0, elem);
				}
				faults++;
			}
		}
		return faults;
		
	}
	
}
