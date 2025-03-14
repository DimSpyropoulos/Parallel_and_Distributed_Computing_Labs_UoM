import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	

	public Request prepareRequest() throws IOException {	
		Request req = new Request();	
		while(true){
			System.out.print("Enter message to send to server(op-message-k):");
			String theOutput = user.readLine();
			String[] correctMessageFormat = theOutput.split("-");
			if(correctMessageFormat.length == 3) {
				req.setOpcode(correctMessageFormat[0]);
				req.setMessage(correctMessageFormat[1]);
				req.setK(Integer.parseInt(correctMessageFormat[2]));
				return req;
			}
			else if(theOutput.equals("CLOSE")){
				req.setOpcode(theOutput);
				return req;
			} else {System.out.println("Wrong message format(op-message-k).");}
			
		}
		
	}

	public void processReply(Reply r) throws IOException {
	
		System.out.println("Message received from server: " + r.getMessage());
	}
}
