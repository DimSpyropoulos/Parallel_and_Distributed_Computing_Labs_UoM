public class SimpleSatThreads {
    
    public static void main(String[] args) {  
    
        // Circuit input size (number of bits)
        int size = 28;
        // Number of possible inputs (bit combinations)
        int iterations = (int) Math.pow(2, size);

        int numThreads = Runtime.getRuntime().availableProcessors();
        // Start timing
        long start = System.currentTimeMillis();
        
        MyThread threads[] = new MyThread[numThreads];
        for(int i=0; i<numThreads; i++){
            threads[i] = new MyThread(i, size, iterations, numThreads);
            threads[i].start();
        }
        
        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }
        
        // Stop timing   
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        
        System.out.println ("All done\n");
        System.out.println("time in ms = "+ elapsedTimeMillis);
        
    }
        
    
    
   
}
 class MyThread extends Thread {
    int id, start, stop, size;

    public MyThread(int id, int size, int iterations, int numThreads){
        this.id = id;
        this.size = size;
        start = id * (iterations/numThreads);
        stop = start + (iterations/numThreads);
        if(id == (numThreads -1)) {stop = iterations;}
    }

    public void run(){
        for (int i = start; i < stop; i++)
            check_circuit (i, size);
    }

    static void check_circuit (int z, int size) {
    
        // z: the combination number
        // v: the combination number in binar format 
        
		boolean[] v = new boolean[size];  
    
		for (int i = size-1; i >= 0; i--) 
			v[i] = (z & (1 << i)) != 0;
    
        // Check the ouptut of the circuit for input v
        boolean value = 
           (  v[0]  ||  v[1]  )
        && ( !v[1]  || !v[3]  )
        && (  v[2]  ||  v[3]  )
        && ( !v[3]  || !v[4]  )
        && (  v[4]  || !v[5]  )
        && (  v[5]  || !v[6]  )
        && (  v[5]  ||  v[6]  )
        && (  v[6]  || !v[15] )
        && (  v[7]  || !v[8]  )
        && ( !v[7]  || !v[13] )
        && (  v[8]  ||  v[9]  )
        && (  v[8]  || !v[9]  )
        && ( !v[9]  || !v[10] )
        && (  v[9]  ||  v[11] )
        && (  v[10] ||  v[11] )
        && (  v[12] ||  v[13] )
        && (  v[13] || !v[14] )
        && (  v[14] ||  v[15] )
        && (  v[14] ||  v[16] )
        && (  v[17] ||  v[1]  )
        && (  v[18] || !v[0]  )
        && (  v[19] ||  v[1]  )
        && (  v[19] || !v[18] )
        && ( !v[19] || !v[9]  )
        && (  v[0]  ||  v[17] )
        && ( !v[1]  ||  v[20] )
        && ( !v[21] ||  v[20] )
        && ( !v[22] ||  v[20] )
        && ( !v[21] || !v[20] )
        && (  v[22] || !v[20] );
        
        // If output == 1 print v and z
        if (value) {
			printResult(v, size, z);
		}	
    }
     // Printing utility
     static void printResult (boolean[] v, int size, int z) {
		
		String result = null;
		result = String.valueOf(z);

		for (int i=0; i< size; i++)
			if (v[i]) result += " 1";
			else result += " 0";
		
		System.out.println(result);
	}
    

 }