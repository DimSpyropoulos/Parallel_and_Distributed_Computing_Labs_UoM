public class Pi {
	private static double result;
	
	public Pi () {	
		result = 0.0;
	}

	public synchronized void addTo(double toAdd) {
		result += toAdd;
	}

	public synchronized void printResult () {
	    System.out.println("Result =" + result);
	}
	
	
        
}
