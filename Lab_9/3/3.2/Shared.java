
import java.util.Hashtable;

public class Shared {
    Hashtable<Long,Double> numInt;

    public Shared() {
        numInt = new Hashtable<Long,Double>();
    }

    public synchronized double getPi(Long l){
        return numInt.get(l);
    }

    public synchronized void addPi(Long l, double d){
        numInt.put(l, d);
        System.out.println("Added Successfully!");     
    }

    public synchronized boolean checkPi(Long l){
        if(numInt.get(l) != null) return true;
        return false;
    }
}
