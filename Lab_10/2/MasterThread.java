import java.io.*;
import java.net.*;

class MasterThread extends Thread
{
	private Socket dataSocket;
	private int myId;
	private InputStream is;
   	private BufferedReader in;
	private OutputStream os;
   	private PrintWriter out;
	private Pi myPi;
	private int numSteps;
	

   	public MasterThread(Socket socket, int id, Pi p, int ns)
   	{
		dataSocket = socket;
		myId = id;
		numSteps = ns;
		try {
			is = dataSocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os,true);
			myPi = p;
		}
		catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}

	public void run()
	{
   		String inmsg, outmsg;
		
		try {
			MasterProtocol app = new MasterProtocol(myPi, myId, numSteps);
			outmsg = app.prepareRequest();
			out.println(outmsg);
			inmsg = in.readLine();
			app.processReply(inmsg);	
			dataSocket.close();	

		} catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
	}	
}	
			
		
