import java.net.*;
import java.util.Enumeration;
import java.io.*;

public class MultithreadedChatServerTCP {
	private static final int PORT = 1234;
	
	public static void main(String args[]) throws IOException {

		ServerSocket connectionSocket = new ServerSocket(PORT);
		AllConnections cons = new AllConnections(); //Κλάση που "κρατάει" όλες τις συνδέσεις
		int client = 0;
		
		while (true) {	

			System.out.println("Server is waiting first client in port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			Connection c = new Connection(dataSocket);
			cons.addCon(client, c);
			System.out.println("Received request from " + dataSocket.getInetAddress());
			
			ServerThread sthread = new ServerThread(client,cons);
			sthread.start();
			//Μήνυμα εισόδου νέου χρήστη στους υπόλοιπους
			for(int i=0; i<client; i++){
				if(cons.getCon(i) != null) {cons.getCon(i).send("Client" +client +" has joined.");}
				}
			
			client++;
		}
	}
}


