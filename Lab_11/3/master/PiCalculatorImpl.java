import java.rmi.*;
import java.rmi.server.*;
import java.util.Hashtable;

public class PiCalculatorImpl extends UnicastRemoteObject implements PiCalculator {
	
    private int numWorkers;
    private int workersConnected = 0;
    private boolean done = false;
    private double myPi = 0;
    private long numSteps;
    

	public PiCalculatorImpl(long ns, int nw) throws RemoteException {
        super();
        numSteps = ns;
        numWorkers = nw;
	}

	
	public synchronized double calculate(long start, long stop) throws RemoteException {
               
        double sum = 0.0;
        double step = 1.0 / (double)numSteps;
        for (long i=start; i < stop; ++i) {
            double x = ((double)i+0.5)*step;
            sum += 4.0/(1.0+x*x);
        }
        double pi = sum * step;
        myPi += pi;
        return pi;
    }

    public synchronized int getId(){
        workersConnected++;
        return workersConnected-1;
    }

    public int getNumWorkers(){
        return numWorkers;
    }

    public long getNumSteps(){
        return numSteps;
    }

    public void done(){
        done = true;
    }

    public boolean calculationComplete(){
        return done;
    }

    public synchronized double pi(){
        return myPi;
    }


}
