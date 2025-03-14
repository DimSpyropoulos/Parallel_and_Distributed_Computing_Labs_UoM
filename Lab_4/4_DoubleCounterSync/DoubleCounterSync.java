public class DoubleCounterSync {

    public static void main(String[] args){

        int numThreads = Runtime.getRuntime().availableProcessors();
        MyThread threads[] = new MyThread[numThreads];
        DoubleCounter counter = new DoubleCounter();

        for(int i=0; i<numThreads; i++){
            threads[i] = new MyThread(i, counter);
            threads[i].start();
        }

        for(int i=0; i<numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        for(int i=1; i<3; i++)
        System.out.print("n" + i + " = " + counter.getN(i) + "\n");

    }   
    
}

class DoubleCounter {
    int n1, n2;
    Object n1_lock = new Object();
    Object n2_lock = new Object();

    public DoubleCounter(){
        n1 = 0;
        n2 = 0;
    }

    public void inc_n1(int n){
        synchronized (n1_lock){
            n1 = n1 + n;
        }
    }

    public void inc_n2(int n) {
        synchronized (n2_lock){
            n2 = n2 + n;
        }
    }

    public int getN(int which){
        if(which == 1) return n1;
        return n2;
    }

    
}

class MyThread extends Thread {
    int id;
    DoubleCounter counter;

    public MyThread (int i, DoubleCounter c) {
        id = i;
        counter = c;
    }

    public void run() {
        counter.inc_n1(2*id);
        counter.inc_n2(id*id);
    }
}