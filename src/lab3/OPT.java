package lab3;

import java.util.ArrayList;

class OPT
{
	
	public static void main(String[] args)
	{
		
		int[] refString = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1 };
		System.out.print("Reference String - ");
		print(refString);
		int length = refString.length;
		int frames = 3;
		System.out.print("\nPage frames - " + frames);
		System.out.print("\nOPT - ");
		optimalPage(refString, length, frames);
		
	}
	
	
	private static boolean exist(int key, ArrayList<Integer> frames)
	{
		
		for (int i = 0; i < frames.size(); i++)
		{
			if (frames.get(i) == key)
			{
				return true;
			}
		}
		return false;
		
	}
	
	
	private static int notUsedFrame(int[] refString, ArrayList<Integer> frames, int length, int index)
	{
		
		int res = -1;
		int farthest = index;
		for (int i = 0; i < frames.size(); i++)
		{
			int j;
			for (j = index; j < length; j++)
			{
				if (frames.get(i) == refString[j])
				{
					if (j > farthest)
					{
						farthest = j;
						res = i;
					}
					break;
				}
			}
			
			if (j == length)
			{
				return i;
			}
		}
		return (res == -1) ? 0 : res;
		
	}
	
	
	private static void optimalPage(int[] refString, int length, int frames)
	{
		
		ArrayList<Integer> fr = new ArrayList<Integer>();
		int hit = 0;
		for (int i = 0; i < length; i++)
		{
			if (exist(refString[i], fr))
			{
				hit++;
				continue;
			}
			if (fr.size() < frames)
			{
				fr.add(refString[i]);
			}
			else
			{
				int j = notUsedFrame(refString, fr, length, i + 1);
				fr.set(j, refString[i]);
			}
		}
		System.out.println(length - hit);
		System.out.print("\n");
		
	}
	
	
	static void print(int[] a)
	{
		
		for (int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
		
	}
	
}
