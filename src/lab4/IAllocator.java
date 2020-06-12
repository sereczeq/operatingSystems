package lab4;

import java.util.ArrayList;

interface IAllocator
{
	
	// I initially thought that making an interface would be useful,
	// It only cause more problems and confusion :/
	
	public int getNumberOfPages(ArrayList<Process> processes);
	
	public void calculateFaults(ArrayList<Process> processes, int rate);
	
	public ArrayList<Integer> getFaults(ArrayList<Process> processes);
	
	public void setAllFrames(ArrayList<Process> processes, int amountOfFramesGiven);
	
}
