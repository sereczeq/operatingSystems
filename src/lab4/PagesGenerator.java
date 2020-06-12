package lab4;

import java.util.ArrayList;
import java.util.Random;

public class PagesGenerator
{
	
	ArrayList<Integer> generate(int amount, int range)
	{
		
		ArrayList<Integer> list = new ArrayList<>();
		Random random = new Random();
		
		for (int i = 0; i < amount; i++)
		{
			list.add(random.nextInt(range));
		}
		return list;
		
	}
	
}
