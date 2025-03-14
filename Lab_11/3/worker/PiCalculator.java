import java.rmi.*;

public interface PiCalculator extends Remote {
	
	// Ypografh ths apomakrysmenhs methodoy.
	public  double calculate(long start, long stop) throws RemoteException;
	public int getId()throws RemoteException;
	public int getNumWorkers()throws RemoteException;
	public long getNumSteps()throws RemoteException;
	public void done()throws RemoteException;
	public boolean calculationComplete()throws RemoteException;
	public double pi()throws RemoteException;
}
