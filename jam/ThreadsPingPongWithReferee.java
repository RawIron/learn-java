import java.util.Random;

class Node {
    protected String[] messages = new String[4];
    protected int readFrom;
    protected int writeTo;

    public synchronized void give(String message) {
        while(writeTo - readFrom == 3) {
            try { wait(); } catch (Exception e) {}                
        }
        messages[writeTo&3] = message;
        ++writeTo;
        notifyAll();
    }
    public synchronized String pick() {
        String message;
        while(writeTo - readFrom == 0) {
            try { wait(); } catch (Exception e) {}
        }
        message = messages[readFrom&3];
        ++readFrom;
        notifyAll();
        return message;
    }    
}

class Player extends Node implements Runnable {
    private Thread t;
    private Player p;
    private Referee r;
    private boolean isRunning = true;
    private String name;
    private Random g;
    
    public Player(String name) {
        this.name = name;
        this.g = new Random();
    }
    
    public void playWith(Player p, Referee r) {
        this.p = p;
        this.r = r;
    }
    protected void serve() {
        System.out.println(name + " ping");
        p.give("ping");
    }
    protected void ping() {
        if (g.nextInt(2) == 1) {
            r.give("missed");
            System.out.println(name + " missed");
        } else {
            p.give("pong");
            System.out.println(name + " pong");
        }
    }
    protected void pong() {
        System.out.println(name + " ping");
        p.give("ping");
    }
    protected void matchOver() {
        isRunning = false;
    }
    
    public void play() {
        String action;
        r.give("ready");        
        while(isRunning) {
            action = pick();
            if (action.equals("serve")) { serve(); }
            if (action.equals("ping")) { ping(); }
            if (action.equals("pong")) { pong(); }
            if (action.equals("matchover")) { matchOver(); }
        }
    }
    public void run() {
        t = Thread.currentThread();
        play();
    }
}

class Referee extends Node implements Runnable {
    private Thread t;
    private Player p1;
    private Player p2;
    private boolean isRunning = true;
    private int readyCounter = 0;
    private Random g;
    
    public Referee() {
        this.g = new Random();
    }
    
    public void playWith(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    
    protected void serve() {
        if (g.nextInt(2) == 1) {
            p1.give("serve");            
        } else {
            p2.give("serve");            
        }
    }
    protected void ready() {
        ++readyCounter;
        if (readyCounter == 2) {
            serve();
        }
    }
    protected void missed() {
        serve();        
    }
    protected void matchOver() {
        isRunning = false;
        p1.give("matchover");
        p2.give("matchover");
    }
    public void run() {
        t = Thread.currentThread();
        int loopFor = 0;
        String action;
        while(isRunning) {
            action = pick();
            if (action.equals("ready")) { ready(); }
            if (action.equals("missed")) { missed(); }
            
            if (++loopFor > 10) {
                matchOver();
            }
        }
    }
}


public class ThreadsPingPongWithReferee {

    public static void main(String[] args) {
        Player p1 = new Player("Iron");
        Player p2 = new Player("Bat");
        Referee r = new Referee();
        p1.playWith(p2, r);
        p2.playWith(p1, r);
        r.playWith(p1, p2);
        
        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t3 = new Thread(r);
        
        t1.start();
        t2.start();
        t3.start();
    }
}
