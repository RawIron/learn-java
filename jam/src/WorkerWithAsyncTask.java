
class WorkerWithAsyncCall implements Runnable {
	protected static final int LOOPS = 30;
	
	protected void workAsyncOnTask() {
		(new Thread() {
			public void run() {
				for (int served=0; served < LOOPS; ++served) {
					try { Thread.sleep(2); } catch (Exception e) {}
					System.out.println("served" + served);
				}
			}
		}).start();	
	}
	public void workOnTask() {
		workAsyncOnTask();
		for (int i=0; i<LOOPS; ++i) {
			try { Thread.sleep(3); } catch (Exception e) {}
			System.out.println("worked"+i);
		}
	}
	
	@Override
	public void run() {
		workOnTask();
	}
}


public class WorkerWithAsyncTask {

	public static void main(String[] args) {
		new Thread(new WorkerWithAsyncCall()).start();
	}

}
