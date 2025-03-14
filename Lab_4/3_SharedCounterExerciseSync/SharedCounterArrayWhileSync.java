public class SharedCounterArrayWhileSync{
  
    public static void main(String[] args) {

        int numThreads = 4;
        CounterThread threads[] = new CounterThread[numThreads];
        SharedCounterSync aCounter = new SharedCounterSync(10000);
	
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(aCounter);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
        aCounter.check_array ();
    }
     
        
}

class SharedCounterSync {

    int end;
    int counter;
    int[] array;
    

    public SharedCounterSync(int e) {
        end = e;
        array = new int[end];
        counter = 0;
    }

    public void check_array ()  {
		int i, errors = 0;

		System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
			if (array[i] != 1) {
				errors++;
				System.out.printf("%d: %d should be 1\n", i, array[i]);
			}         
		}
        System.out.println (errors+" errors.");
    }

    public void inc() {
      
	    while (true) {
            synchronized (this){
            if (counter >= end) break;
            array[counter]++;
            counter++;
            }		
        }
    }

}

class CounterThread extends Thread {
  	
    SharedCounterSync myCounter;

    public CounterThread(SharedCounterSync myC) {
        myCounter = myC;
    }
   
    public void run() {        
         myCounter.inc();
    }            	
}
  
