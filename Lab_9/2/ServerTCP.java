import java.net.*;
import java.io.*;

public class ServerTCP {
    private static final int PORT = 1234;
	private static final String EXIT = "CLOSE";

	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = new ServerSocket(PORT);
		System.out.println("Server is listening to port: " + PORT);

		Socket dataSocket = connectionSocket.accept();
		System.out.println("Received request from " + dataSocket.getInetAddress());

		
		ObjectInputStream serverInputStream = new    
			ObjectInputStream(dataSocket.getInputStream());

	 	ObjectOutputStream serverOutputStream = new 
			ObjectOutputStream(dataSocket.getOutputStream());
		
		
		Request req = new Request();
		Reply rep = new Reply();

		ServerProtocol app = new ServerProtocol();
		try {
            req = (Request) serverInputStream.readObject();
            while (!req.getOpcode().equals(EXIT)) {
                rep = app.processRequest(req);
                serverOutputStream.writeObject(rep);
                req = (Request) serverInputStream.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
            serverInputStream.close();
            serverOutputStream.close();
            dataSocket.close();
            System.out.println("Data socket closed");
        }
		
	}
    
}
