package lab3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class FIFO
{
	
	public static void main(String[] args)
	{
		
		int[] refString = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2 };
		System.out.print("Reference String - ");
		print(refString);
		int frames = 6;
		System.out.print("\nPage frames - " + frames);
		System.out.print("\nFIFO - ");
		System.out.println(pageFaults(refString, refString.length, frames));
		
	}
	
	
	static void print(int[] a)
	{
		
		for (int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		
	}
	
	
	static int pageFaults(int[] refString, int n, int frames)
	{
		
		HashSet<Integer> s = new HashSet<>(frames);
		Queue<Integer> indexes = new LinkedList<>();
		int pageFaults = 0;
		for (int i = 0; i < n; i++)
		{
			if (s.size() < frames)
			{
				if (!s.contains(refString[i]))
				{
					s.add(refString[i]);
					pageFaults++;
					indexes.add(refString[i]);
				}
			}
			else
			{
				if (!s.contains(refString[i]))
				{
					int val = indexes.peek();
					indexes.poll();
					s.remove(val);
					s.add(refString[i]);
					indexes.add(refString[i]);
					pageFaults++;
				}
			}
		}
		return pageFaults;
		
	}
	
}
