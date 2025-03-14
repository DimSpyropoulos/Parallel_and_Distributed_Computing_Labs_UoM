import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.registry.*;

public class PiWorker {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
		
		try {
		// Locate rmi registry
			
		Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
		// Look up for a soecific name and get remote reference (stub)
		String rmiObjectName = "MyPiCalculator";
		PiCalculator ref = (PiCalculator)registry.lookup(rmiObjectName);
			
		int myID = ref.getId();
		long numSteps = ref.getNumSteps();
		int numWorkers = ref.getNumWorkers();
		long start = myID * (numSteps/numWorkers);
		long stop = start + (numSteps/numWorkers);
		if(myID == (numWorkers -1 )) stop = numSteps;
		System.out.println("Worker" +myID +" calculates pi from " +start +" to " +stop);
		double myPi = ref.calculate(start,stop);
		System.out.println("My result is " +myPi);
		if(myID == (numWorkers -1 )) ref.done();	

		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}

		
	}
}

