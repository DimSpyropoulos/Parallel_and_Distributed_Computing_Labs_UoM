import java.rmi.*;

public interface Calculator extends Remote {
	
	// Ypografh ths apomakrysmenhs methodoy.
	public String calculate(double a, double b, String c) throws RemoteException;
}
