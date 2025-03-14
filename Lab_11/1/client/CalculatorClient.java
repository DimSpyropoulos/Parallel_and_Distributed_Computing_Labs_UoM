import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.registry.*;

public class CalculatorClient {
	
	private static final String HOST = "localhost";
	private static final int PORT = Registry.REGISTRY_PORT; // 1099
	
	public static void main(String[] args) {
		String in = "";
		double a = 0;
		double b = 0;
		String result = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("This app calculates(+,-,*,/) the result between two numbers.");
		System.out.println("*Message Format: a <space> op <space> b or CLOSE to close.*");
		
		try {
			// Locate rmi registry
			
			Registry registry = LocateRegistry.getRegistry(HOST, PORT);
			
			// Look up for a soecific name and get remote reference (stub)
			String rmiObjectName = "MyCalculator";
			Calculator ref = (Calculator)registry.lookup(rmiObjectName);
			System.out.print("Calculate: ");
			// Do remote method invocation
			
			in = reader.readLine();
			while(!in.equals("CLOSE")){
				String[] message = in.split(" ");
				if (message.length == 3 && message[0].matches("-?\\d+(\\.\\d+)?")
										&& message[2].matches("-?\\d+(\\.\\d+)?")){
					
					a = Double.parseDouble(message[0]);
					b = Double.parseDouble(message[2]);
					result = ref.calculate(a,b,message[1]);
					System.out.println("The result is: " + result);
				} else { 
					System.out.println("*Message Format: a <space> op <space> b or CLOSE to close.*");
				}
				System.out.print("Calculate: ");
				in = reader.readLine();
			}
			System.out.println("Calculator closed.");
		} catch (RemoteException re) {
			System.out.println("Remote Exception");
			re.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other Exception");
			e.printStackTrace();
		}

		
	}
}

