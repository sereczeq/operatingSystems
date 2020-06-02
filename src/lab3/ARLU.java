package lab3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class ARLU
{
	
	public static void main(String[] args)
	{
		
		String refString = "";
		int frames = 0;
		refString = "0 3 2 3 5 1 0 2 4 6 0 2 3 4 2 4 1 4";
		System.out.println("Reference string - " + refString);
		frames = 3;
		System.out.println("Frames - " + frames);
		System.out.println("ARLU Second Chance - " + faults(refString, frames));
		
	}
	
	
	static boolean findAndUpdate(int x, int[] arr, boolean[] secondChance, int frames)
	{
		
		int i;
		for (i = 0; i < frames; i++)
		{
			if (arr[i] == x)
			{
				secondChance[i] = true;
				return true;
			}
		}
		return false;
		
	}
	
	
	static int replaceAndUpdate(int x, int[] arr, boolean[] second_chance, int frames, int pointer)
	{
		
		while (true)
		{
			if (!second_chance[pointer])
			{
				arr[pointer] = x;
				return (pointer + 1) % frames;
			}
			second_chance[pointer] = false;
			pointer = (pointer + 1) % frames;
		}
		
	}
	
	
	static int faults(String refString, int frames)
	{
		
		int pointer, i, l, x, pf;
		pointer = 0;
		pf = 0;
		int[] arr = new int[frames];
		Arrays.fill(arr, -1);
		boolean[] second_chance = new boolean[frames];
		String[] str = refString.split(" ");
		l = str.length;
		
		for (i = 0; i < l; i++)
		{
			
			x = Integer.parseInt(str[i]);
			if (!findAndUpdate(x, arr, second_chance, frames))
			{
				
				pointer = replaceAndUpdate(x, arr, second_chance, frames, pointer);
				pf++;
			}
		}
		
		return pf;
		
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
