import java.net.*;
import java.io.*;

public class ServerProtocol {

    Calculator calculator = new Calculator();

	public String processRequest(String theInput) {
		System.out.println("Received message from client: " + theInput);
		String theOutput = theInput;
        if (!theOutput.equals("CLOSE")){
            String[] opAttributes = theOutput.split(" ");
            calculator.set_ab(opAttributes[0],opAttributes[2]);
            calculator.set_op(opAttributes[1].charAt(0));
            theOutput = calculator.calculate();
        }
		System.out.println("Send message to client: " + theOutput);
		return theOutput;
	}
}