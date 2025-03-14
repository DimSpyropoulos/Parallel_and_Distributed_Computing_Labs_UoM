import java.net.*;
import java.io.*;

public class WorkerProtocol {

	private int numWorkers;
	
	public WorkerProtocol(int num) {
		numWorkers = num;
	}	

	public String compute(String theInput) throws IOException {
	
		String[] splited = theInput.split("\\s+");
		int numSteps = Integer.parseInt(splited[0]);
		int id = Integer.parseInt(splited[1]);
		double sum = 0.0;
        double step = 1.0 / (double)numSteps;
		int numThreads = Runtime.getRuntime().availableProcessors();

        WorkerThread threads[] = new WorkerThread[numThreads];
		
		
		int block = numSteps / numWorkers;
		int start = id * block;
		int stop = start + block;
		if (id == numWorkers-1) stop = numSteps;
		System.out.println("Worker "+ id +" calculates pi from " + start + "to " +stop);
		
		for(int i=0; i<numThreads; i++){
            int threadStart = start + i * (block / numThreads);
            int threadStop = threadStart + (block / numThreads);
            if (i == numThreads - 1) threadStop = stop; 
            threads[i] = new WorkerThread(threadStart, threadStop, step);
            threads[i].start();
        }

        for(int i=0; i<numThreads; i++){
            try {
                threads[i].join();
                sum += threads[i].getSum();
            } catch (InterruptedException e) { }
        }

        double pi = sum * step;
		String theOutput = String.valueOf(pi);	
		System.out.println("Worker "+ id +" result " + theOutput);
		
		return theOutput;
	}
}

class WorkerThread extends Thread {
	private int start, stop;
    private double mySum, step;

    public WorkerThread(int st, int sp, double s){
        start = st;
        stop = sp;
        step = s;
        mySum = 0.0;
    }

    public void run(){
        for (long i = start; i < stop; ++i) {
            double x = ((double)i + 0.5) * step;
            mySum += 4.0 / (1.0 + x * x);
        }
    }

    public double getSum(){
        return mySum;
    }

}
