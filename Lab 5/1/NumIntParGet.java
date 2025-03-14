public class NumIntParGet {
    public static void main(String[] args) {

        long numSteps = 10000;
        double sum = 0.0;
        double step = 1.0 / (double)numSteps;

        int numThreads = Runtime.getRuntime().availableProcessors();

        SumThread threads[] = new SumThread[numThreads];

        long startTime = System.currentTimeMillis();
        for(int i=0; i<numThreads; i++){
            threads[i] = new SumThread(i, numThreads, numSteps, step);
            threads[i].start();
        }

        for(int i=0; i<numThreads; i++){
            try {
                threads[i].join();
                sum += threads[i].getSum();
            } catch (InterruptedException e) { }
        }

        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
    
}

class SumThread extends Thread {
    int id, numThreads;
    long numSteps, start, stop;
    double mySum, step;

    public SumThread(int i, int nm, long ns, double s){
        id = i;
        numThreads = nm;
        numSteps = ns;
        step = s;
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
    }

    public double getSum(){
        return mySum;
    }

}