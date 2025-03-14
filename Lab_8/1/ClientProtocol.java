import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	
	public String prepareRequest() throws IOException {
	
		
		while(true){
			System.out.print("Enter message to send to server(op-message-k):");
			String theOutput = user.readLine();
			String[] correctMessageFormat = theOutput.split("-");
			if(correctMessageFormat.length == 3 || theOutput.equals("CLOSE")) return theOutput;
			System.out.println("Wrong message format(op-message-k).");
		}
		
	}

	public void processReply(String theInput) throws IOException {
	
		System.out.println("Message received from server: " + theInput);
	}
}
