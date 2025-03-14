import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
public class SPSCBuffer {
	private int[] contents;
	private int size;
	private int front, back;
	private int counter = 0;
	private Lock lock = new ReentrantLock();
/*	private Condition bufferFull = lock.newCondition();
 *  private Condition bufferEmpty = lock.newCondition(); 
 *  Στην περίπτωση που έχουμε μόνο 1 παραγωγό και 1 καταναλωτή μπορεί να 
 *  δουλέψει και με 1 Condition ανάλογα με το counter(0 ή size) για να 
 *  ειδοποιούμε το νήμα που περιμένει.*/
    private Condition bufferCondition = lock.newCondition();

	// Constructor
	public SPSCBuffer(int s) {
	this.size = s;
	contents = new int[size];
	for (int i=0; i<size; i++)
		contents[i] = 0;
		this.front = 0;
		this.back = size - 1;
	}

	// Put an item into buffer
	public void put(int data) {

		lock.lock();
			try {
				while (counter == size) {
				System.out.println("The buffer is full");
				try {
					bufferCondition.await();
				} catch (InterruptedException e) { }
			}
			back = (back + 1) % size;
			contents[back] = data;
			counter++;
			System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Loc " + back + " Count = " + counter);
			//buffer was empty
			if (counter == 1) bufferCondition.signal(); // signal() αντί για signalAll() αφού μόνο 1 νήμα περιμένει, ο καταναλωτής
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
			data = contents[front];
			System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Loc " + front + " Count = " + (counter-1));
			front = (front + 1) % size;
			counter--;
			//buffer was full
			if (counter == size-1) bufferCondition.signal(); // signal() αντί για signalAll() αφού μόνο 1 νήμα περιμένει, ο παραγωγός
		} finally {
			lock.unlock() ;
		}
		return data;
	}
}

	
			
	
