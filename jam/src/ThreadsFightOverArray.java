import java.util.Random;


class ArrayRandomInit {
	static public void erase(int[] job) {
		for (int i=0; i<job.length; ++i) {
			job[i] = 0;
		}		
	}
	
	static public int fill(int[] job) {
		Random g = new Random();
		int totalEntries = g.nextInt(job.length);
		for (int i=0; i<totalEntries; ++i) {
			job[i] = 1;
		}
		System.out.println("should find " + totalEntries);
		return totalEntries;
	}

	static public int count(int[] job) {
		int total = 0;
		for (int i=0; i<job.length; ++i) {
			if (job[i] != 0) {
				++total;
			}
		}
		System.out.println("found " + total);
		return total;
	}
}


class ArrayWriter implements Runnable {
	private static final int LOOPS = 50;
	private static final int START_DELAY_MILLIS = 500;
	private static final int ITERATE_DELAY_MILLIS = 2;
	private int[] job;
	private Random g;

	public ArrayWriter(int[] job) {
		this.job = job;
		this.g = new Random();
	}
	
	protected void refill() {
		synchronized(job) {
			ArrayRandomInit.erase(job);
			ArrayRandomInit.fill(job);		
		}
		try { Thread.sleep(g.nextInt(ITERATE_DELAY_MILLIS)); } catch (Exception e) {}
	}
	
	public void run() {
		try { Thread.sleep(g.nextInt(START_DELAY_MILLIS)); } catch (Exception e) {}
		for (int i=0; i<LOOPS; ++i) {
			refill();
		}
		return;
	}
}

class ArrayReader implements Runnable {
	private static final int LOOPS = 50;
	private static final int START_DELAY_MILLIS = 500;
	private static final int ITERATE_DELAY_MILLIS = 4;
	private int[] job;
	private Random g;
	
	public ArrayReader(int[] job) {
		this.job = job;
		this.g = new Random();
	}

	protected int countJob() {
		int total = 0;
		synchronized(job) {
			total = ArrayRandomInit.count(job);
			try { Thread.sleep(g.nextInt(ITERATE_DELAY_MILLIS)); } catch (Exception e) {}
		}
		return total;
	}
	
	public void run() {
		try { Thread.sleep(g.nextInt(START_DELAY_MILLIS)); } catch (Exception e) {}		
		for (int i=0; i<LOOPS; ++i) {
			countJob();
		}
		return;
	}
}


public class ThreadsFightOverArray {
	private static final int ENTRIES = 100;

	public static void main(String[] args) {	
		int[] test1 = new int[ENTRIES];
		ArrayRandomInit.fill(test1);
		
		System.out.println("start threads");		
		Thread r = new Thread(new ArrayReader(test1));
		r.start();
		Thread w = new Thread(new ArrayWriter(test1));
		w.start();
	}
}
