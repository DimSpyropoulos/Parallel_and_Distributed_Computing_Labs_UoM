import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class CyclicBarrier {
	private int arrived = 0;
	private int totalThreads;
	private boolean waiting = false;
    private boolean leaving = true;
	private Lock lock = new ReentrantLock();
	private Condition cWait = lock.newCondition();
    private Condition cLeave = lock.newCondition();
	
	public CyclicBarrier (int total) {
		this.totalThreads = total;
	}		

	public void barrier() {
		// waiting
		lock.lock();
		try {
			arrived++;
		if (arrived == totalThreads) {
            waiting = true;
            leaving = false;
        }
		while (waiting==false)	{
		    try {
				cWait.await();
			} catch (InterruptedException e) { }
		}
        cWait.signalAll();
        
                
		// leaving
		arrived--;
		if (arrived == 0) {
            waiting = false;
            leaving = true;
        }
        while (leaving == false){
            try {
                cLeave.await();
            } catch (InterruptedException e) {}
        }
        cLeave.signal();
		} finally {
			lock.unlock() ;
		}
	}
}
