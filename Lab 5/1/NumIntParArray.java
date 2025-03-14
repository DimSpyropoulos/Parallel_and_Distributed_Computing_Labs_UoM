public class NumIntParArray {
    public static void main(String[] args) {   
        
        long numSteps = 10000;

        if (args.length != 1) {
            System.out.println("arguments:  number_of_steps");
                    System.exit(1);
        }
        try {
            numSteps = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("argument "+ args[0] +" must be long int");
            System.exit(1);
        }

        double sum = 0.0;
        int numThreads = Runtime.getRuntime().availableProcessors();
        double step = 1.0 / (double)numSteps;
        double[] tSum = new double[numThreads];

		for(int i = 0; i < numThreads; i++)
			tSum[i] = 0.0;
        
        SumThread threads[] = new SumThread[numThreads];

        long startTime = System.currentTimeMillis();
        for(int i=0; i<numThreads; i++){
            threads[i] = new SumThread(i, numThreads, numSteps, step, tSum);
            threads[i].start();
        }

        for(int i=0; i<numThreads; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) { }
        }

		for (int i = 0; i < numThreads; i++) {
			sum = sum + tSum[i];
		}

        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
    
}

class SumThread extends Thread {
    int id, numThreads;
    long numSteps, start, stop;
    double mySum, step;
    double[] tSum;

    public SumThread(int i, int nm, long ns, double s, double[] ts){
        id = i;
        numThreads = nm;
        numSteps = ns;
        step = s;
        tSum = ts;
        mySum = 0.0;
        start = id * (numSteps/numThreads);
        stop = start + (numSteps/numThreads);
        if(id == (numThreads -1 )) stop = numSteps;
    }

    public void run(){
        for (long i=start; i <stop; ++i) {
            double x = ((double)i+0.5)*step;
            mySum += 4.0/(1.0+x*x);
        }

        tSum[id] = mySum;

    }

}