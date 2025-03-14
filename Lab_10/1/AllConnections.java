import java.util.Enumeration;
import java.util.Hashtable;

public class AllConnections {

    private Hashtable<Integer,Connection> cons;

    public AllConnections() {
        cons = new Hashtable<Integer,Connection>();
    }

    public synchronized void addCon(int i, Connection c) {
        cons.put(i, c);
        
    }

    public synchronized void removeCon(int i){
        cons.get(i).closeSocket();
        cons.remove(i);
    }

    public Connection getCon(int i){
        return cons.get(i);
    }

    public int getSize(){
        return cons.size();
    }  
    
    public Enumeration<Integer> getKeys(){
        return cons.keys();
    }
    
}
