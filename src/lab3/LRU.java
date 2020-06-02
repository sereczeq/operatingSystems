package lab3;

import java.util.ArrayList;

public class LRU
{
	
	public static void main(String[] args)
	{
		
		int[] refString = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2 };
		System.out.print("Reference String - ");
		print(refString);
		int frames = 6;
		System.out.print("\nPage frames - " + frames);
		System.out.print("\nLRU - ");
		
		ArrayList<Integer> set = new ArrayList<>(frames);
		int count = 0;
		int pageFaults = 0;
		for (int i : refString)
		{
			if (!set.contains(i))
			{
				if (set.size() == frames)
				{
					set.remove(0);
					set.add(frames - 1, i);
				}
				else set.add(count, i);
				pageFaults++;
				++count;
				
			}
			else
			{
				set.remove((Object) i);
				set.add(set.size(), i);
			}
		}
		System.out.println(pageFaults);
		
	}
	
	
	static void print(int[] a)
	{
		
		for (int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		
	}
	
}
