import java.rmi.*;
import java.rmi.server.*;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
	
	private static final long serialVersionUID = 1;

	public CalculatorImpl() throws RemoteException {
	}
	
	public String calculate(double a, double b, String c) throws RemoteException {
		String result = "";
        
        switch (c) {
            case "+": result = Double.toString(a + b);               
                break;
            case "-": result = Double.toString(a - b);
                break;
            case "/": if(b==0) result = "Not possible"; else result = Double.toString(a / b);
                break;
            case "*": result = Double.toString(a * b);
                break;        
            default: result = "Unknown opperation";
                break;
        }
        
        return result;
    }
}
