package lab5;

public class ProcessGenerator implements Runnable
{
	
	boolean done = true;
	
	public static void main(String[] args)
	{
		
		System.out.println("a");
		ProcessGenerator pr = new ProcessGenerator();
		Thread t = new Thread(pr);
		t.start();
		for (int x = 0; x < 100; x++) System.out.println(x);
		pr.done = false;
		
	}
	
	
	@Override
	public void run()
	{
		
		while (done) System.out.println("working");
		
	}
	
}
