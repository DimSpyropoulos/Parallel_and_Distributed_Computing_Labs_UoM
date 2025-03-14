import java.rmi.*;
import java.rmi.server.*;
import java.util.Hashtable;

public class PiCalculatorImpl extends UnicastRemoteObject implements PiCalculator {
	
	private static final long serialVersionUID = 1;
    private static Hashtable<Long,Double> numInt = new Hashtable<Long,Double>();

	public PiCalculatorImpl() throws RemoteException {
	}
	
	public synchronized double calculate(long numSteps) throws RemoteException {
        if(numInt.get(numSteps) != null) {return numInt.get(numSteps);}
        
        double sum = 0.0;
        double step = 1.0 / (double)numSteps;
        for (long i=0; i < numSteps; ++i) {
            double x = ((double)i+0.5)*step;
            sum += 4.0/(1.0+x*x);
        }
        double pi = sum * step;
        numInt.put(numSteps, pi);
        return pi;
    }
}
