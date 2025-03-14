import java.io.*;
import java.net.*;

class ServerThread extends Thread
{
	private Socket dataSocket;
	private Shared shared;
	private InputStream is;
   	private BufferedReader in;
	private OutputStream os;
   	private PrintWriter out;
	private static final String EXIT = "CLOSE";

   	public ServerThread(Socket socket, Shared s)
   	{
		dataSocket = socket;
		shared = s;
		try {
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os,true);
		}
		catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}

	public void run()
	{
   		String inmsg, outmsg;
		
		try {
			inmsg = in.readLine();
			ServerProtocol app = new ServerProtocol();
			outmsg = app.processRequest(inmsg, shared);
			while(!outmsg.equals(EXIT)) {
				out.println(outmsg);
				inmsg = in.readLine();
				outmsg = app.processRequest(inmsg, shared);
			}		

			dataSocket.close();
			System.out.println("Data socket closed");

		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	
}	
			
		
