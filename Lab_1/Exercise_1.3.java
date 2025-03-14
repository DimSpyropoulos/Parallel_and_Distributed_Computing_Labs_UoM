public class Ergasia_1_3 {

    public static void main(String[] args) {

        
        int numThreads = 10;
        Thread[] threads = new Thread[numThreads];
    
        
        for (int i = 0; i < numThreads; ++i) {
            System.out.println("In main: create and start thread " + i);
            threads[i] = new MyThread(i);
            threads[i].start();
        }
        
        
        for (int i = 0; i < numThreads; ++i) {
            try {
                threads[i].join();
            }
             catch (InterruptedException e) {}
        }

        System.out.println("In main: threads all done");
    }
}


class MyThread extends Thread {
    int threadID;
    int temp1;
    int temp2;

    public MyThread (int id){
        threadID = id;
        temp1 = id + 1;
        temp2 = 0;
    }

    
    public void run() {
        for(int i=1; i<21; i++){
            temp2 = temp1*i;
            System.out.println("Thread " + threadID + ":" + i + "*" + temp1 + "=" + temp2);
        }
    } 

}

