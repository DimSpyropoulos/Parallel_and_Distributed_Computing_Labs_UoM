import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Connection {
    private Socket mySocket;
    private InputStream is;
    private OutputStream os;
    private BufferedReader in;
	private PrintWriter out;

    public Connection(Socket s){
        mySocket = s;
        try {
			is = mySocket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is));
			os = mySocket.getOutputStream();
			out = new PrintWriter(os,true);
		}
		catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
		}
    }

    public String receive(){
        String s="";
        try {
            s = in.readLine();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return s;
    }

    public synchronized void send(String s){
        
        try {
            out.println(s);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void closeSocket(){
        try {
            mySocket.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
    
}
