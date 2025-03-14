import java.net.*;
import java.io.*;

public class MasterProtocol {

	private Pi myPi;
	private int myId;
	private int numSteps;

	public MasterProtocol (Pi p, int id, int ns) {
		
		myPi = p;
		myId = id;
		numSteps = ns;
	}

	public String prepareRequest() {
	 
		String theOutput = String.valueOf(numSteps) + " " + String.valueOf(myId);
		return theOutput;
	}
	
	public void processReply(String theInput) {
	
		double repl = Double.parseDouble(theInput);
		myPi.addTo(repl);
	}	
}
