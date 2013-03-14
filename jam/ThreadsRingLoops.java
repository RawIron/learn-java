import java.util.Random;


interface RingConnector {
	public void in(String message);
	public String out();
}


class MessageBuffer implements RingConnector {
	protected static final int BUFFERSIZE = 4;
	protected String[] messages = new String[BUFFERSIZE];
	protected int readFrom = 0;
	protected int writeTo = 0;

	public synchronized void in(String message) {
		while(writeTo - readFrom == BUFFERSIZE) {
			try { wait(); } catch (Exception e) {}				
		}
		messages[writeTo&(BUFFERSIZE-1)] = message;
		++writeTo;
		notifyAll();
	}
	public synchronized String out() {
		String message;
		while(writeTo - readFrom == 0) {
			try { wait(); } catch (Exception e) {}
		}
		message = messages[readFrom&(BUFFERSIZE-1)];
		++readFrom;
		notifyAll();
		return message;
	}
}


class RingNode implements Runnable, RingConnector {
	private RingConnector nic;
	private RingNode neighbor;
	private boolean isStopped = false;
	private String name;
	private String id;
	private Random g;
	private int closedLoops = 0;
	private boolean debug = false;
	
	public RingNode(String name, RingConnector nic) {
		this.nic = nic;
		this.name = name;
		this.g = new Random();
		this.id = name + g.nextInt(10);
	}
	
	public void neighborIs(RingNode node) {
		this.neighbor = node;
	}
	public void debugOn() {
		this.debug = true;
	}
	
	protected void serve() {
		String message = id;
		if (debug) { System.out.println("send " + message); }
		neighbor.in(message);
	}
	protected void forward(String message) {
		if (debug) { System.out.println("forward " + name + " " + message); }
		neighbor.in(message);
	}
	protected void loopClosed() {
		if (debug) { System.out.println(name + " closed"); }
		++closedLoops;
		if (closedLoops > 10) {
			matchOver();
		}
	}
	protected void matchOver() {
		if (debug) { System.out.println(name + " done"); }
		neighbor.in("matchover");
		isStopped = true;
	}
	
	protected void deadlock() {
		for (int served=0; served <= 10; ++served) {
			serve();
		}		
	}
	protected void async() {
		(new Thread() {
			public void run() {
				for (int served=0; served <= 10; ++served) {
					serve();
				}
			}
		}).start();		
	}
	public void play() {
		//async();
		String action;
		while(!isStopped) {
			serve();
			action = out();
			if (action.startsWith(id)) { loopClosed(); }
			else if (action.equals("matchover")) { matchOver(); }
			else { forward(action); }
		}
	}
	
	@Override
	public void run() {
		try { Thread.sleep(2); } catch (Exception e) {}
		play();
		System.out.println(name + " worked " + closedLoops);
	}
	@Override
	public void in(String message) {
		nic.in(message);
	}
	@Override
	public String out() {
		return nic.out();
	}
}


public class ThreadsRingLoops {
	protected static final int NNODES = 4;
	protected static final RingNode[] ring = new RingNode[NNODES];
	protected static final String[] NAMES = {"Iron", "Bat", "Thor", "Marvel"};
	
	public static void main(String[] args) {
		RingConnector nic = null;
		for (int i=0; i<NNODES; ++i) {
			nic = new MessageBuffer();
			ring[i] = new RingNode(NAMES[i], nic);
		}
		for (int i=0; i<NNODES; ++i) {
			System.out.println("neighbors " + i + " " + ((i+1)&(NNODES-1)));
			ring[i].neighborIs(ring[(i+1)&(NNODES-1)]);
			if (i == 3) { ring[i].debugOn(); }
			new Thread(ring[i]).start();
		}
	}
}
