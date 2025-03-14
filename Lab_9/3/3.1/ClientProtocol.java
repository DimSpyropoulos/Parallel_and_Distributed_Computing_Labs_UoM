import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	
	public String prepareRequest() throws IOException {
	
		
		while(true){
			System.out.print("Give NumInt: ");
			String theOutput = user.readLine();
			if(isNumeric(theOutput)	|| theOutput.equals("CLOSE")) return theOutput;
			System.out.println("Wrong.");
		}
		
	}

	public  boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  
	}

	public void processReply(String theInput) throws IOException {
	
		System.out.println("Result: " + theInput);
	}
}
