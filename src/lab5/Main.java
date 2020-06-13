package lab5;

import java.util.ArrayList;

public class Main
{
	
	/*
	 * Program simulates processors communicating in real time: For every processor
	 * there is a Process Generator, that generates random processes at random
	 * intervals of time. Every Process has two values: time needed to finish it,
	 * and CPU load it takes. Each processor takes the tasks and decides what to do
	 * with them (based on given parameters.) Every second processors "execute" one
	 * second of every task they have. If the task is executed (means it has been in
	 * processor for specified amount of time), it is being removed from processor,
	 * and it's load is freed.
	 * 
	 * Printing can be turned off!
	 * 
	 * Program uses multi-threading (which I haven't mastered yet); hence there may
	 * be some errors happening. If any occur, program has to be restarted :( The
	 * more migrations the more chance to get an error
	 */
	public static void main(String[] args)
	{
		
		// Processors
		int amountOfProcessors = 5;
		int minimumTreshold = 30;
		/*
		 * if minimum threshold will be set to 0 - program will work at maximum
		 * threshold only: "give" requests should be zero, because processors will not
		 * try to get more work. Also processors will never be underloaded.
		 */
		int maximumTreshold = 70;
		/*
		 * if maximum threshold is set to 100 and minimum to nonzero number, program
		 * will work at minimum threshold We can expect a lot of "give" requests and a
		 * lot of migration
		 * 
		 * if maximum threshold is set to 100, and minimum to 0 - program will work as
		 * random We can expect less "give"and "take" requests, but average CPU load
		 * will be relatively high. There also should be a lot less process migrations
		 */
		
		// Processes
		int amountOfProcesses = 10; // per processor
		int minimumTaskTime = 2;
		int maximumTaskTime = 10;
		int maximumTaskCPULoad = 50;
		
		// User experience
		boolean print = true;
		int timeBetweenNewProcesses = 3;
		int second = 500; // 1000 units is exactly one second (the bigger the value, the less likely for
							// errors to happen)
		
		go(amountOfProcessors, minimumTreshold, maximumTreshold, amountOfProcesses, minimumTaskTime, maximumTaskTime,
				maximumTaskCPULoad, timeBetweenNewProcesses, second, print);
		
	}
	
	
	private static void go(int amountOfProcessors, int minimumTreshold, int maximumTreshold, int amountOfProcesses,
			int minimumTaskTime, int maximumTaskTime, int maximumTaskCPULoad, int timeBetweenNewProcesses, int second,
			boolean print)
	{
		
		// setting up processors
		ArrayList<Processor> processors = new ArrayList<Processor>();
		for (int x = 0; x < amountOfProcessors; x++)
		{
			processors.add(new Processor("Processor" + x, minimumTreshold, maximumTreshold, amountOfProcesses,
					minimumTaskTime, maximumTaskTime - minimumTaskTime, maximumTaskCPULoad, timeBetweenNewProcesses,
					second, print));
		}
		// giving each processor information about other processors
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
