public class MatrixAddThread {
    public static void main(String args[])
    {  
      int size = 10;
      int numThreads = Runtime.getRuntime().availableProcessors();

      double[][] a = new double[size][size];
      double[][] b = new double[size][size];
      double[][] c = new double[size][size];
      for(int i = 0; i < size; i++) 
        for(int j = 0; j < size; j++) { 
          a[i][j] = 0.1;
          b[i][j] = 0.3;
          c[i][j] = 0.5;
        }   
      
      
      MyThread threads[] = new MyThread[numThreads];
          
      for(int i = 0; i < numThreads; i++) {
              threads[i] = new MyThread(i, a, b, c, size, numThreads);
              threads[i].start();
      }
  
      for(int i = 0; i < numThreads; i++) {
          try {
              threads[i].join();
          } catch (InterruptedException e) {}
      }
      
  
      
      // for debugging 
    for(int i = 0; i < size; i++) { 
        for(int j = 0; j < size; j++) 
          System.out.print(a[i][j]+" "); 
        System.out.println();
      }
    }
    
}

class MyThread extends Thread{
    int id, start, stop, size;
    double[][] m;
    double[][] m1;
    double[][] m2;
 

    public MyThread(int id, double[][] m, double[][] m1, double[][] m2, int size, int numThreads){
        this.id = id;
        this.m = m;
        this.m1 = m1;
        this.m2 = m2;
        this.size = size;
        start = id * (size / numThreads);
	    stop = start + (size / numThreads);
		if (id == (numThreads - 1)) stop = size;
    }

    public void run()
	{
        for(int i = start; i < stop; i++){
            for(int j = 0; j < size; j++)  
                m[i][j] = m1[i][j] + m2[i][j];
        }
		
           
	}
}
