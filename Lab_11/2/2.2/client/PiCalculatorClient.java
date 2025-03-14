import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.registry.*;

public class PiCalculatorClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
		String in = "";
		double result = 0;
		long numSteps = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			// Locate rmi registry
			
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			// Look up for a soecific name and get remote reference (stub)
			String rmiObjectName = "MyPiCalculator";
			PiCalculator ref = (PiCalculator)registry.lookup(rmiObjectName);
			System.out.print("Give number of steps: ");
			// Do remote method invocation
			
			in = reader.readLine();
			while(!in.equals("CLOSE")){
				if (in.matches("-?\\d+(\\.\\d+)?")){
					numSteps = Long.parseLong(in);
					result = ref.calculate(numSteps);
					System.out.println("The result is: " + result);
				} else { 
					System.out.println("*Invalid input.Give number of steps, or CLOSE to close.*");
				}
				System.out.print("Calculate: ");
				in = reader.readLine();
			}

		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}

		
	}
}

