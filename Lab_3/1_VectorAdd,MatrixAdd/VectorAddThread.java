public class VectorAddThread {
    public static void main(String args[])
  {
    int size = 1000;
    int numThreads = Runtime.getRuntime().availableProcessors();

    double[] a = new double[size];
    double[] b = new double[size];
    double[] c = new double[size];
    for(int i = 0; i < size; i++) {
        a[i] = 0.0;
		b[i] = 1.0;
        c[i] = 0.5;
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
    

    
    for(int i = 0; i < size; i++) 
        System.out.println(a[i]); 
  }
}

class MyThread extends Thread{
    int id;
    double[] v;
    double[] v1;
    double[] v2;
    int start, stop;

    public MyThread(int id, double[] v, double[] v1, double[] v2, int size, int numThreads){
        this.id = id;
        this.v = v;
        this.v1 = v1;
        this.v2 = v2;
        start = id * (size / numThreads);
	    stop = start + (size / numThreads);
		if (id == (numThreads - 1)) stop = size;
    }

    public void run()
	{
		for(int i = start; i < stop; i++) 
            v[i] = v1[i] + v2[i];
           
	}
}
