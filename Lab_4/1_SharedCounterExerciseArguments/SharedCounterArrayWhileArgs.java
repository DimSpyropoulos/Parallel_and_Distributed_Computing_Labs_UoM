

public class SharedCounterArrayWhileArgs {

    public static void main(String[] args) {

        int numThreads = 4;
        SharedCounter aCounter = new SharedCounter(0);
        int end = 10000;
        int[] array = new int[end];
        CounterThread threads[] = new CounterThread[numThreads];
	
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(array,end, aCounter);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
        check_array (end, array);
    }
     
    static void check_array (int end, int[] array)  {
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

}

class SharedCounter{
    int counter;

    public SharedCounter(int c){
        counter = c;
    }
}

class CounterThread extends Thread {

    int[] array;
    int end;
    SharedCounter myCounter;
  	
    public CounterThread(int[] a, int e, SharedCounter myC) {
        array = a;
        end = e;
        myCounter = myC;
    }
   
    public void run() {
    
         while (true) {
             if (myCounter.counter >= end) 
                 break;
             array[myCounter.counter]++;
             myCounter.counter++;		
         } 
     }            	
 }
  
