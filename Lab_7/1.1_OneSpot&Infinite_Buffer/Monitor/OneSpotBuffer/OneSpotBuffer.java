public class OneSpotBuffer {
	private int contents; //1 θέση οπότε απλή μεταβλητή int αντί για int[]
//	private boolean bufferEmpty = true; Δεν χρειάζονται γιατί εφόσον έχουμε synchronized μόνο ένα νήμα τη φορά μπορεί να 
//	private boolean bufferFull = false; προσπελάσει το buffer και το counter αρκεί για να ελέγξουμε αν είναι full ή empty
	//private int size; Δεν χρειάζεται αφού θα έχει μόνο μια θέση
	private int /*front, back,*/ counter = 0; // Επίσης δεν χρειάζονται

	// Constructor
	public OneSpotBuffer() {
	contents = 0;
	}

	// Put an item into buffer
	public synchronized void put(int data)
	{
		while (counter == 1) { //αντι για while(bufferFull)
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	//	back = (back + 1) % size; Δεν έχουμε size
		contents = data; //αντί για contents[back] = data
		counter++;
		System.out.println("Item " + data + ". Count = " + counter);
	//	bufferEmpty = false;
		if (counter==1)
		{
	//		bufferFull = true;
			System.out.println("The buffer is full");
		}
		//buffer was empty
		/*if (counter == 1)*/ notifyAll(); // μετά από κάθε put γιατί έχουμε μια μόνο θέση
	}

	// Get an item from bufffer
	public synchronized int get()
	{
		while (counter == 0) { //αντί για while(bufferEmpty)
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}
		int data = contents; // αντί για data = contens[front]
		counter--;
		System.out.println("Item " + data + " removed. Count = " + counter);	
	//	front = (front + 1) % size;	Δεν έχουμε size
	//	bufferFull = false;
		if (counter==0) 
		{
	//		bufferEmpty = true;
			System.out.println("The buffer is empty");
		}
		//buffer was full
		/*if (counter == size-1)*/ notifyAll(); //όπως στο get 
		return data;
	}
}

	
			
	
