import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FoodConsumer extends Thread {
	private FoodBank bank;
	private final Lock lock = new ReentrantLock();

	public FoodConsumer(FoodBank newBank) {
		bank = newBank;
	}
	public void run() {	
		while(true) {
			//Random food generation
			int randomFood = (int)(Math.random() * 100 + 1);
			//Condition for consumer attempting to take more food than available
			while(bank.getFood() < randomFood) {
				System.out.println("Waiting to take food");
				try {
					Thread.sleep(100);
				}
				catch(InterruptedException e) {
					System.out.println(e);
				}
			}
			//Takes food from shared bank object
			try {
				lock.lock();
				bank.takeFood(randomFood);
				System.out.println("Taking " + randomFood + " items of food. The balance"
						+ " is now " + bank.getFood() + " items.");
				lock.unlock();
			}
			catch (Exception e) {
				System.out.println("Exception occured");
			}
			//Thread sleeps for 100 ms after each iteration
		    try {
		    	Thread.sleep(100);
		    }
		    catch(InterruptedException e) {
		    	System.out.println(e);
		    }  
		}
	}
}