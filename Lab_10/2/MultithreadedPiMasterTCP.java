import java.net.*;
import java.io.*;

public class MultithreadedPiMasterTCP {
	private static final int PORT = 1234;
	private static final int numWorkers = 4;
	private static Pi p = new Pi();
	private static int numSteps = 10000;


	public static void main(String args[]) throws IOException {


		ServerSocket connectionSocket = new ServerSocket(PORT);
		MasterThread mthread[] = new MasterThread[numWorkers];
		
		for (int i=0; i<numWorkers; i++) {	

			Socket dataSocket = connectionSocket.accept();
			mthread[i] = new MasterThread(dataSocket, i, p, numSteps);
			mthread[i].start();
		}
		System.out.println("All Started");
		
		for (int i=0; i<numWorkers; i++) {
			try {
				mthread[i].join();
			} catch (InterruptedException e) {}
		}
		 
		p.printResult(); 
		 	
	}
}


