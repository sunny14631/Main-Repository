import java.util.concurrent.locks.*;
public class FoodProducer extends Thread {
	private FoodBank bank;
	private final Lock lock = new ReentrantLock();
	public FoodProducer(FoodBank newBank) {
		bank = newBank;
	}
	public void run() {	
		while(true) {
			//Random food generation
			int randomFood = (int)(Math.random() * 100 + 1);
			try {
				//Adds food to shared bank object
				lock.lock();
				bank.giveFood(randomFood);
				System.out.println("Giving " + randomFood + " items of food. The balance"
						+ " is now " + bank.getFood() + " items");
				lock.unlock();
			}
			catch (Exception e) {
				System.out.println("Exception occured");
			}
			//After each iteration, the thread sleeps for 100 ms
		    try {
		    	Thread.sleep(100);
		    }
		    catch(InterruptedException e) {
		    	System.out.println(e);
		    }  
		}
	}
}