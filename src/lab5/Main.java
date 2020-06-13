package lab5;

import java.util.ArrayList;

public class Main
{
	
	public static void main(String[] args)
	{
		
		int amountOfProcessors = 5;
		
		ArrayList<Processor> processors = new ArrayList<Processor>();
		for (int x = 0; x < amountOfProcessors; x++)
		{
			processors.add(new Processor("P" + x));
		}
		for (Processor processor : processors)
		{
			processor.setup(processors, 10);
		}
		for (Processor processor : processors)
		{
			Thread t = new Thread(processor, processor.getName());
			t.start();
		}
		for (int x = 0; x < processors.size(); x++)
		{
			if (processors.get(x).isWorking()) x = -1;
		}
		int sentRequests = 0;
		for (Processor processor : processors)
		{
			sentRequests += processor.requestsSent;
		}
		System.out.println(sentRequests);
		
	}
	
}
