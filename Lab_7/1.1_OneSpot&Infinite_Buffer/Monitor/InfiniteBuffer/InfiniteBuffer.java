import java.util.ArrayList;

public class InfiniteBuffer {
	private ArrayList<Integer> contents; // Δυναμική δομή αντί για int[]
	private boolean bufferEmpty = true;
//	private boolean bufferFull = false; Δεν θα είναι ποτέ full
//	private int size; Δεν χρειάζεται αφού είναι άπειρος
	private int front, /*back,*/counter = 0; //Δεν back χρειάζεται γιατί θα αυξάνεται μόνο 

	// Constructor
	public InfiniteBuffer() {

	contents = new ArrayList<Integer>(0);
	
	}

	// Put an item into buffer
	public synchronized void put(int data)
	{
		/*while (bufferFull) {
			try {
				wait();
			} catch (InterruptedException e) {}
		} */ //Δεν χρειάζεται γιατί δεν θα γίνει ποτέ full
	//	back = (back + 1) % size;
	//	contents[back] = data; αντί γι αυτό απλά κάνουμε add
		contents.add(data);
		counter++;
		System.out.println("Item " + data + " added in loc " + contents.size() + ". Count = " + counter);
		bufferEmpty = false;
		/*if (counter==size)
		{
			bufferFull = true;
			System.out.println("The buffer is full");
		} */ //Δεν θα γίνει ποτέ full
		//buffer was empty
		if (counter == 1) notifyAll();
	}

	// Get an item from bufffer
	public synchronized int get()
	{
		while (bufferEmpty) {
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
	//	int data = contents[front]; αντί γι αυτό απλά get
		int data = contents.get(front);
		counter--;
		System.out.println("Item " + data + " removed from loc " + front + ". Count = " + counter);	
		front = (front + 1)/*%size*/; // Δεν υπάρχει πλέον size	
		//bufferFull = false; Δεν θα είναι ποτέ full
		if (counter==0) 
		{
			bufferEmpty = true;
			System.out.println("The buffer is empty");
		}
		//buffer was full
		/*if (counter == size-1) notifyAll(); 
		Δεν χρειάζεται notifyAll() εδώ γιατί εφόσον ο buffer δεν μπορεί να γεμίσει οι παραγωγοί
		δεν θα είναι σε wait */
		return data;
	}
}

	
			
	
