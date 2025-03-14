import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;

public class PiMaster {
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	private static final int numWorkers = 4;	
	private static int numSteps = 10000;


	public static void main(String args[]) throws IOException {
		System.setProperty("java.rmi.server.hostname", HOST);
                
		// Remote object creation
		PiCalculator robj = new PiCalculatorImpl(numSteps, numWorkers);
		
		
		// Choose the appropriate method:
		// get if rmi registry is alreadt running, create otherwise 
		// Registry registry = LocateRegistry.getRegistry(HOST, PORT);
		Registry registry = LocateRegistry.createRegistry(PORT);
		
		// Bind remote object to a name and publish in rmi registry
                
		String rmiObjectName = "MyPiCalculator";
		registry.rebind(rmiObjectName, robj);
		System.out.println("Remote object bounded.");
		
		// Server is running until we press a key
		
		System.out.println("Waiting for workers to finish.");
		while(!robj.calculationComplete()){
			try {
				Thread.sleep(100);				
			} catch (Exception e) {
				// TODO: handle exception
			}
				
		}
		
		System.out.println("Result = " + robj.pi());
		try {
			UnicastRemoteObject.unexportObject(robj, true);
			registry.unbind(rmiObjectName);
			System.out.println("Remote object unbounded.");
			
		} catch (NotBoundException e) {
			// TODO: handle exception
		}	
		


		//p.printResult(); 
		 	
	}
}


