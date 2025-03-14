import java.util.ArrayList;
import java.util.concurrent.Semaphore;
public class InfiniteBuffer {
	private ArrayList<Integer> contents;
  //private int size; Δεν χρειάζεται αφού ο buffer είναι άπειρος
	private int front /*back*/; //Δεν χρειαζόμαστε back 
	private int counter = 0;
	private Semaphore mutex = new Semaphore(1);
  //private Semaphore bufferFull = new Semaphore(0); Ποτέ δεν θα είναι full ο buffer
	private Semaphore bufferEmpty = new Semaphore(0); // αρχικοποιείται στο 0 

	// Constructor
	public InfiniteBuffer() {
		contents = new ArrayList<Integer>();
		front = 0;		
	}

	// Put an item into buffer
	public void put(int data) {
	/*	try {
			bufferEmpty.acquire();
		} catch (InterruptedException e) { }
		Δεν υπάρχει χρειάζεται κάποια προστασία αφού ο buffer δεν μπορεί να γεμίσει */
		try {
			mutex.acquire();
		} catch (InterruptedException e) { }
	//	back = (back + 1) % size;
	//	contents[back] = data; αντί γι αυτό απλά κάνουμε add
		contents.add(data);
		counter++;
		System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Loc " + contents.size() + " Count = " + counter);
	  //if (counter == size) System.out.println("The buffer is full"); Δεν θα είναι ποτέ full
		mutex.release(); 
		bufferEmpty.release(); //αυξάνεται για να φαίνεται πως ο buffer δεν είναι empty
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;
		try {
			bufferEmpty.acquire(); // μειώνεται αφού κάποιο στοιχείο θα καταναλωθεί
		} catch (InterruptedException e) { }
		try {
			mutex.acquire();
		} catch (InterruptedException e) { }
	//  data = contents[front]; αντί γι αυτό απλά γίνεται get
		data = contents.get(front);
		System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Loc " + front + " Count = " + (counter-1));
		front = (front + 1)/* %size */; //Δεν έχουμε size πλέον
		counter--;	
		if (counter == 0) System.out.println("The buffer is empty");	
		mutex.release();		
	  //bufferEmpty.release(); Δεν χρειάζεται κάτι εδώ 
		return data;
	}
}

	
			
	
