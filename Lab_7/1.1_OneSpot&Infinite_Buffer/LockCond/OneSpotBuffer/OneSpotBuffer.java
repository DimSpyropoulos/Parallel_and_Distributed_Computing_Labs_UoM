import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class OneSpotBuffer {
	private int contents; // αντι για int[] μια απλή μεταβλητη αφου υπάρχει μονο μια θέση
//  private int size;       size,front και back δεν χρειαζονται αφου υπαρχει μόνο μια θέση
//  private int front, back;
	private int counter = 0;
	private Lock lock = new ReentrantLock();
//	private Condition bufferFull = lock.newCondition();
//	private Condition bufferEmpty = lock.newCondition();
	private Condition bufferCondition = lock.newCondition(); //Μια condition αρκεί 

	// Constructor
	public OneSpotBuffer() {
	contents = 0;	
	}

	// Put an item into buffer
	public void put(int data) {

		lock.lock();
			try {
				while (counter == 1) {
				System.out.println("The buffer is full");
				try {
					bufferCondition.await();
				} catch (InterruptedException e) { }
			}
		//	back = (back + 1) % size; Δεν υπάρχει back
			contents = data;	
			counter++;
			System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Count = " + counter);
			//buffer was empty
			/*if (counter == 1)*/bufferCondition.signalAll(); //Εφόσον έχουμε μόνο μια θέση πρέπει να γίνεται μετά από κάθε put
		} finally {
			lock.unlock() ;
		}
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;

		lock.lock();
		try {
			while (counter == 0) {
				System.out.println("The buffer is empty");
				try {
					bufferCondition.await();
				} catch (InterruptedException e) { }
			}
			data = contents;
			
		//	front = (front + 1) % size; Δεν υπάρχει front
			counter--;
			System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Count = " + (counter));
			//buffer was full
			/*if (counter == 1)*/ bufferCondition.signalAll(); //Όπως στο και put πρέπει να γίνεται μετά από κάθε get
		} finally {
			lock.unlock() ;
		}
		return data;
	}
}

	
			
	
