import java.net.*;
import java.io.*;

public class ClientProtocol {

	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	
	public String prepareRequest() throws IOException {
	
		
		while(true){
			System.out.print("Calculate: ");
			String theOutput = user.readLine();
			String[] correctMessageFormat = theOutput.split(" ");
			if((correctMessageFormat.length == 3 && isNumeric(correctMessageFormat[0]) && isNumeric(correctMessageFormat[2])) 
			|| theOutput.equals("CLOSE")) return theOutput;
			System.out.println("Wrong message format(a <space> op <space> b).");
		}
		
	}

	public  boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  
	}

	public void processReply(String theInput) throws IOException {
	
		System.out.println("Result: " + theInput);
	}
}
