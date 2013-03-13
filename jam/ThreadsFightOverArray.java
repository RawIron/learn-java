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
		return totalEntries;
	}
}


class ArrayWriter implements Runnable {
	private int[] job;
	private Random g;
	
	public ArrayWriter(int[] job) {
		this.job = job;
		this.g = new Random();
	}
	
	protected void refill() {
		for (int i=0; i<100; ++i) {
			synchronized(job) {
				ArrayRandomInit.erase(job);
				ArrayRandomInit.fill(job);
			}
			try { Thread.sleep(g.nextInt(2)); } catch (Exception e) {}
		}
	}
	
	public void run() {
		refill();
		return;
	}
}

class ArrayReader implements Runnable {
	private int[] job;
	private Random g;
	
	public ArrayReader(int[] job) {
		this.job = job;
		this.g = new Random();
	}
	
	protected int countJob() {
		return countJob(10);
	}
	protected int countJob(int high) {
		int total = 0;
		synchronized(job) {
			for (int i=0; i<job.length; ++i) {
				if (job[i] != 0) {
					++total;
				}
				try { Thread.sleep(g.nextInt(2)); } catch (Exception e) {}
			}
		}
		return total;
	}
	
	public void run() {
		int found = countJob(100);
		System.out.println("read:" + found);
		return;
	}
}


public class ThreadsFightOverArray {

	public static void main(String[] args) {
		int[] test1 = new int[30];
		int totalFilled = ArrayRandomInit.fill(test1);
		System.out.println("array has " + totalFilled);
		
		System.out.println("start threads");		
		Thread r = new Thread(new ArrayReader(test1));
		r.start();
		Thread w = new Thread(new ArrayWriter(test1));
		w.start();
	}
}
