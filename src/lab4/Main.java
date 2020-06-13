package lab4;

import java.util.ArrayList;

public class Main
{
	
	public static void main(String[] args)
	{
		
		int amountOfProcesses = 30;
		int amountOfFrames = 400;
		int amountOfPages = 90;
		int range = 50;
		int controlRatePer10Pages = 5;
		int localityDelta = 30;
		System.out.println(
				faults(amountOfProcesses, amountOfFrames, amountOfPages, range, controlRatePer10Pages, localityDelta));
		
	}
	
	
	public static String faults(int amountOfProcesses, int amountOfFrames, int amountOfPages, int range,
			int controlRatePer10Pages, int localityDelta)
	{
		
		String string = "";
		
		ArrayList<Process> processes = ProcessGenerator.generate(amountOfProcesses, amountOfPages, range);
		
		Proportional proportional = new Proportional();
		Equal equal = new Equal();
		PFF pff = new PFF();
		WorkingSet workingSet = new WorkingSet();
		
		string = "Number of pages in each process: \n";
		for (Process p : processes) string += p.toString() + ", ";
		
		string += "\n\nAmount of faults for each process, for different algorithms\n";
		string += "Proportional:\t\t\t\t" + proportional.faults(processes, amountOfFrames);
		string += "\n";
		string += "Equal:\t\t\t\t\t" + equal.faults(processes, amountOfFrames);
		string += "\n";
		string += "Page Foult Frequency with rate " + controlRatePer10Pages + ":\t"
				+ pff.faults(processes, amountOfFrames, controlRatePer10Pages);
		string += "\n";
		string += "Working set with delta " + localityDelta + ":\t\t"
				+ workingSet.faults(processes, amountOfFrames, localityDelta);
		
		return string;
		
	}
	
}
