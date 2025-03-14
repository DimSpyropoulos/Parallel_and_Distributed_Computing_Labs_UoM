import java.net.*;
import java.io.*;
public class ClientTCP {
	private static final String HOST = "localhost";
	private static final int PORT = 1234;
	private static final String EXIT = "CLOSE";

	public static void main(String args[]) throws IOException {

		Socket dataSocket = new Socket(HOST, PORT);
		
		ObjectOutputStream clientOutputStream = new
			ObjectOutputStream(dataSocket.getOutputStream());
		ObjectInputStream clientInputStream = new 
			ObjectInputStream(dataSocket.getInputStream());
		System.out.println("Connection to " + HOST + " established");

		
		Request req = new Request();
		Reply rep = new Reply();
		ClientProtocol app = new ClientProtocol();
		
		try {
			req = app.prepareRequest();
			while (!req.getOpcode().equals(EXIT)) {
				clientOutputStream.writeObject(req);
				rep = (Reply) clientInputStream.readObject();
				app.processReply(rep);
				req = app.prepareRequest();
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally {
			clientOutputStream.close();
			clientInputStream.close();
			dataSocket.close();
			System.out.println("Data Socket closed");
		}

	}
}			

