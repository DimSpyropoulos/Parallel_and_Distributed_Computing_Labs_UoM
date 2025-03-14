
public class Ergasia_1_1 {

    public static void main(String[] args) {
    
        System.out.println("In main: create and start a thread ");
        
        AThread aThread = new AThread();
        aThread.start();  

        AnotherThread anotherThread = new AnotherThread();
        anotherThread.start();

        try {
            aThread.join(); 
            anotherThread.join();;
        }
        catch (InterruptedException e) {}

        System.out.println("In main: thread is done");
    }
}

/* class containing code for thread to execute */
class AThread extends Thread {

    /* thread code */
    public void run() {
    
        System.out.println("Hello from a thread");
    } 

}
class AnotherThread extends Thread {

    /* thread code */
    public void run() {
    
        System.out.println("Hello from another thread");
    } 

}
