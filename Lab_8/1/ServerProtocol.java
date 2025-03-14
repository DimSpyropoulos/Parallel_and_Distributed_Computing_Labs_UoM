import java.net.*;
import java.io.*;

public class ServerProtocol {

    Data data = new Data();

	public String processRequest(String theInput) {
		System.out.println("Received message from client: " + theInput);
		String theOutput = theInput;
        if (!theOutput.equals("CLOSE")){
            String[] dataAttributes = theOutput.split("-");
            data.set_op(dataAttributes[0]);
            data.set_message(dataAttributes[1]);
            data.set_k(dataAttributes[2]);
            theOutput = data.opperation();
        }
		System.out.println("Send message to client: " + theOutput);
		return theOutput;
	}
}