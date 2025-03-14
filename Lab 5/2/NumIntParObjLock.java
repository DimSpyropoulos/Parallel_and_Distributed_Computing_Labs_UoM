import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NumIntParObjLock {
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

        Sum sum = new Sum();
        double step = 1.0 / (double)numSteps;

        int numThreads = Runtime.getRuntime().availableProcessors();

        SumThread threads[] = new SumThread[numThreads];

        long startTime = System.currentTimeMillis();
        for(int i=0; i<numThreads; i++){
            threads[i] = new SumThread(i, numThreads, numSteps, step, sum);
            threads[i].start();
        }

        for(int i=0; i<numThreads; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) { }
        }

        double pi = sum.getSum() * step;

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
    Sum sum;

    public SumThread(int i, int nm, long ns, double s, Sum sum){
        id = i;
        numThreads = nm;
        numSteps = ns;
        step = s;
        mySum = 0.0;
        this.sum = sum;
        start = id * (numSteps/numThreads);
        stop = start + (numSteps/numThreads);
        if(id == (numThreads -1 )) stop = numSteps;
    }

    public void run(){
        for (long i=start; i <stop; ++i) {
            double x = ((double)i+0.5)*step;
            mySum += 4.0/(1.0+x*x);
        }

        sum.addToSum(mySum);
    }
}

class Sum {

    double sum;
    Lock lock = new ReentrantLock();

    public Sum() {
        sum = 0; 
    }

    public void addToSum(double n){
        lock.lock();
        try{
            sum += n;
        } finally {
            lock.unlock();
        }
    }

    public double getSum(){
        return sum;
    }


}