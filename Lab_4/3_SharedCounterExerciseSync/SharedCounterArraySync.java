public class SharedCounterArraySync {

    public static void main(String[] args) {

		int numThreads = 4;
		CounterThread threads[] = new CounterThread[numThreads];
		SharedCounterSync aSharedCounter = new SharedCounterSync(1000);
		
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(aSharedCounter);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
		aSharedCounter.check_array (numThreads);
    }
   
}

class SharedCounterSync {
	  
    int end;
    int[] array;

	public SharedCounterSync(int e){
		end = e;
		array = new int[end];
	}

	public void check_array (int numThreads)  {
		int i, errors = 0;

		System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
			if (array[i] != numThreads*i) {
				errors++;
				System.out.printf("%d: %d should be %d\n", i, array[i], numThreads*i);
			}         
        }
        System.out.println (errors+" errors.");
    }

	public int getEnd() {
		return end;
	}

	public void inc(int index, int stop) {

	    for(int i=0; i<stop; i++){
			synchronized (this){
			array[index]++;
			}
		}
		 
	}


}

class CounterThread extends Thread {
  	
	SharedCounterSync mySharedCounter;

	public CounterThread(SharedCounterSync aSC) {
		mySharedCounter = aSC;
	}

	
   
	public void run() {

		int end = mySharedCounter.getEnd();

		for (int i = 0; i < end; i++) {
			mySharedCounter.inc(i,i);	
		} 
	}            	
}
