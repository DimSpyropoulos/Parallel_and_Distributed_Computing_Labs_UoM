import java.rmi.*;

public interface PiCalculator extends Remote {
	
	// Ypografh ths apomakrysmenhs methodoy.
	public  double calculate(long ns) throws RemoteException;
	
}
