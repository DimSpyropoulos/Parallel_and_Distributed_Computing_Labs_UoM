import java.io.*;
import java.net.*;
import java.util.Enumeration;

class ServerThread extends Thread
{
	private AllConnections cons;
	private int myId;	
	private static final String EXIT = "CLOSE";

   	public ServerThread(int i, AllConnections c)
   	{
      myId = i;
	  cons = c;
	}
	
	public void run()
	{		
   		String inmsg, outmsg;
		inmsg = cons.getCon(myId).receive();
		ServerProtocol app = new ServerProtocol();
		outmsg = app.processRequest(inmsg);
		int key = 0;
		while(!outmsg.equals(EXIT)) {
			/*Όχι for loop για αποστολή μηνυμάτων 
			 *γιατί όσο μπαίνουν-αποχωρούν χρήστες
			 *αλλάζει το πλήθος των connections και έτσι κάποιοι
			 "χάνονται" και δεν μπορούν να στείλουν-λάβουν μηνύματα.*/
			Enumeration<Integer> keys = cons.getKeys();
			while(keys.hasMoreElements()){
				key = keys.nextElement();
				if(key != myId) {cons.getCon(key).send(outmsg +"(Client"+myId +")");}
			}
			inmsg = cons.getCon(myId).receive();
			outmsg = app.processRequest(inmsg);
		}		

		cons.removeCon(myId);

		//Μήνυμα ότι κάποιος χρήστης αποχώρησε προς του υπόλοιπους
		Enumeration<Integer> newKeys = cons.getKeys();
		while(newKeys.hasMoreElements()){
			key = newKeys.nextElement();
			cons.getCon(key).send("Client" +myId +" left...");
		}			
		System.out.println("Data socket closed");
	}	
}	
			
		
