package lab5;

import java.util.ArrayList;

public class Main
{
	
	public static void main(String[] args)
	{
		
		int amountOfProcessors = 5;
		int minimumTreshold = 0; // if set to 0 - program will work at maximum treshold
		int maximumTreshold = 70; // if set to 100 - program will work as random
		int amountOfProcesses = 10; // per processor
		int minimumTaskTime = 2;
		int maximumTaskTime = 10;
		int maximumTaskCPULoad = 50;
		int timeBetweenNewProcesses = 3;
		int second = 500;
		
		// seting up processors
		ArrayList<Processor> processors = new ArrayList<Processor>();
		for (int x = 0; x < amountOfProcessors; x++)
		{
			processors.add(
					new Processor("Processor" + x, minimumTreshold, maximumTreshold, amountOfProcesses, minimumTaskTime,
							maximumTaskTime - minimumTaskTime, maximumTaskCPULoad, timeBetweenNewProcesses, second));
		}
		// giving each procesor information about other procesors
		for (Processor processor : processors)
		{
			processor.setProcessors(processors);
		}
		// starting up all processors
		for (Processor processor : processors)
		{
			Thread t = new Thread(processor, processor.getName());
			t.start();
		}
		
		// if all of them are done working, can proceed
		for (int x = 0; x < processors.size(); x++)
		{
			if (processors.get(x).isWorking()) x = -1;
		}
		
		// counting up all the numbers
		int takeRequestsSent = 0;
		int giveRequestsSent = 0;
		int requestsAccepted = 0;
		int overload = 0;
		int underload = 0;
		int averageCPULoad = 0;
		for (Processor processor : processors)
		{
			takeRequestsSent += processor.getTakeRequestsSent();
			giveRequestsSent += processor.getGiveRequestsSent();
			requestsAccepted += processor.getRequestsAccepted();
			overload += processor.getOverload();
			underload += processor.getUnderLoad();
			averageCPULoad += processor.getAverageCPULoad();
		}
		System.out.println("total \"take\" requests sent (take from me): " + takeRequestsSent);
		System.out.println("total \"give\" requests sent (give me): " + giveRequestsSent);
		System.out.println("total requests accepted (migration): " + requestsAccepted);
		System.out.println("amount of times processor was overloaded: " + overload);
		System.out.println("amount of times processor was underloaded: " + underload);
		System.out.println("average CPU load: " + averageCPULoad / amountOfProcessors);
		
	}
	
}
