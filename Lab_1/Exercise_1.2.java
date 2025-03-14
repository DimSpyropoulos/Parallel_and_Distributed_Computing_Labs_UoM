
public class Ergasia_1_2 {

    public static void main(String[] args) {
        
        int numThreads = 10;
        Thread[] aThreads = new Thread[numThreads];
        Thread[] anotherThreads = new Thread[numThreads];

          /* create and start threads */
          for (int i = 0; i < numThreads; ++i) {
            System.out.println("In main: create and start a thread " + i);
            aThreads[i] = new AThread();
            aThreads[i].start();

            System.out.println("In main: create and start another thread " + i);
            anotherThreads[i] = new AnotherThread();
            anotherThreads[i].start();
        }
        /* wait for threads to finish */
        for (int i = 0; i < numThreads; ++i) {
            try {
                aThreads[i].join();
                anotherThreads[i].join();
            }
             catch (InterruptedException e) {}
        }
        System.out.println("In main: threads are done");
    }
}

/* class containing code for thread to execute */
class AThread extends Thread {

    /* thread code */
    public void run() {
    
        System.out.println("Hello from a thread " + Thread.currentThread().getName());
    } 

}
class AnotherThread extends Thread {

    /* thread code */
    public void run() {
    
        System.out.println("Hello from another thread " + Thread.currentThread().getName());
    } 

}
