import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
public class InfiniteBuffer {
	private ArrayList<Integer> contents; //Δυναμική δομή για αναπαράσταση άπειρου buffer
//	private int size; Δεν χρειάζεται
	private int front;
/*  private int front, back; */	private int counter = 0; // Το back δεν χρειάζεται αφού απλά θα αυξάνεται
	private Lock lock = new ReentrantLock();
//	private Condition bufferFull = lock.newCondition();
	private Condition bufferEmpty = lock.newCondition();

	// Constructor
	public InfiniteBuffer() {
		contents = new ArrayList<Integer>();
	
	}

	// Put an item into buffer
	public void put(int data) {

		lock.lock();
		try {
			/*while (counter == size) {
				System.out.println("The buffer is full");
				try {
					bufferFull.await();
				} catch (InterruptedException e) { }
			}*/ //Δεν χρειάζεται γιατί δεν γίνεται να είναι full ο buffer
		//	back = (back + 1) % size;
		//	contents[back] = data; αντί γι αυτό απλά κάνουμε add στο τέλος της λίστας με το add
			contents.add(data);
			counter++;
			System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Loc " + contents.size() + " Count = " + counter);
			//buffer was empty
			if (counter == 1) bufferEmpty.signalAll();
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
					bufferEmpty.await();
				} catch (InterruptedException e) { }
			}
		//	data = contents[front]; αντί γι αυτό κάνουμε απλά get 
			data = contents.get(front);
			System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Loc " + front + " Count = " + (counter-1));
			front = front + 1;
			counter--;
			//buffer was full
		//	if (counter == size-1) bufferFull.signalAll(); Δεν θα είναι ποτέ full για γίνει await οπότε δεν χρειάζεται και signalAll
		} finally {
			lock.unlock() ;
		}
		return data;
	}
}

	
			
	
