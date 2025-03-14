import java.net.*;
import java.io.*;

public class ServerProtocol {
    
	public String processRequest(String theInput, Shared s) {
		System.out.println("Received message from client: " + theInput);
		String theOutput = theInput;
        if (!theOutput.equals("CLOSE")){
            long numSteps = Long.parseLong(theInput);
            if(s.checkPi(numSteps)) {theOutput = String.valueOf(s.getPi(numSteps));}
            else{
                double sum = 0.0;
                double step = 1.0 / (double)numSteps;
                for (long i=0; i < numSteps; ++i) {
                    double x = ((double)i+0.5)*step;
                    sum += 4.0/(1.0+x*x);
                }
                double pi = sum * step; 
                s.addPi(numSteps, pi);
                theOutput = String.valueOf(pi);
            }
        }
		System.out.println("Send message to client: " + theOutput);
		return theOutput;
	}
}
