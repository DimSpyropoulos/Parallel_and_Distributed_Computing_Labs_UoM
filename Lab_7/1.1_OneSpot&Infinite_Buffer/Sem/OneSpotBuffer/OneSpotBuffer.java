import java.util.concurrent.Semaphore;

public class OneSpotBuffer {
	
	private int contents;  //Για buffer μιας θέσης αρκεί απλή μεταβλητή int
//	private int size;
//	private int front, back; Δεν χρειαζόμαστε size,front και back
	private int counter = 0; //Επίσης περιττό 
//	private Semaphore mutex = new Semaphore(1); Δεν χρειάζεται γιατί οι άλλοι δύο σηματοφόροι (bufferFull και bufferEmpty) παρέχουν τον απαραίτητο έλεγχο για τη συγχρονισμένη πρόσβαση στο buffer
	private Semaphore bufferFull = new Semaphore(0);
	private Semaphore bufferEmpty = new Semaphore(1); //Το size ουσιαστικά δηλώνεται εδώ που λέμε ότι είναι άδειο αν χωράει 1 "αντικείμενο"

	// Constructor
	public OneSpotBuffer() {
	
	contents = 0;
    }

	// Put an item into buffer
	public void put(int data) {
		try {
			bufferEmpty.acquire();
		} catch (InterruptedException e) { }
	/*	try {
			mutex.acquire();
		} catch (InterruptedException e) { }*/
	//	back = (back + 1) % size; Δεν υπάρχει back
		contents = data; // αντί για contents[back] = data
		counter++;
		System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Count = " + counter);
		if (counter == 1) System.out.println("The buffer is full");
	//	mutex.release(); 
		bufferFull.release(); 
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;
		try {
			bufferFull.acquire();
		} catch (InterruptedException e) { }
	/* 	try {
			mutex.acquire();
		} catch (InterruptedException e) { }*/
		data = contents; //αντί για data = contents[front]
		System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Count = " + (counter-1));
    //  front = (front + 1) % size; δεν υπάρχει front
		counter--;	
		if (counter == 0) System.out.println("The buffer is empty");	
	//	mutex.release();		
		bufferEmpty.release();
		return data;
	}
}

	
			
	
