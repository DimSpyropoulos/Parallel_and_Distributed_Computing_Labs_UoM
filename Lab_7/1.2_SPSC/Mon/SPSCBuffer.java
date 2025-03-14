/*Η μόνη αλλαγή που μπορώ να σκεφτώ είναι το notifyAll() να γίνει notify()
 * γιατί σε κάθε χρονική στιγμή 1 νήμα θα περιμένει για τη συνθήκη, είτε ο
 * παραγωγός είτε ο καταναλωτής */

public class SPSCBuffer {
	private int[] contents;
	private boolean bufferEmpty = true;
	private boolean bufferFull = false;
	private int size;
	private int front, back, counter = 0;

	// Constructor
	public SPSCBuffer(int s) {
	this.size = s;
	contents = new int[size];
	for (int i=0; i<size; i++)
		contents[i] = 0;
		this.front = 0;
		this.back = -1;
	}

	// Put an item into buffer
	public synchronized void put(int data)
	{
		while (bufferFull) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		back = (back + 1) % size;
		contents[back] = data;
		counter++;
		System.out.println("Item " + data + " added in loc " + back + ". Count = " + counter);
		bufferEmpty = false;
		if (counter==size)
		{
			bufferFull = true;
			System.out.println("The buffer is full");
		}
		//buffer was empty
		if (counter == 1) notify(); //αντί για notifyAll επειδή υπάρχει μόνο 1 νήμα στην αναμονή
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
		int data = contents[front];
		counter--;
		System.out.println("Item " + data + " removed from loc " + front + ". Count = " + counter);	
		front = (front + 1) % size;	
		bufferFull = false;
		if (counter==0) 
		{
			bufferEmpty = true;
			System.out.println("The buffer is empty");
		}
		//buffer was full
		if (counter == size-1) notify(); //αντί για notifyAll επειδή υπάρχει μόνο 1 νήμα στην αναμονή
		return data;
	}
}

	
			
	
