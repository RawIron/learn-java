import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class RoadFrame extends Frame {

    public RoadFrame() {
        setSize(800, 200);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

     public void init() {
        slowdown = new Scrollbar(Scrollbar.HORIZONTAL, 0, 0, 0, 100);
        slowdown.setUnitIncrement(10);
        slowdown.setValue(5);

        arrival = new Scrollbar(Scrollbar.HORIZONTAL, 0, 0, 0, 100);
        arrival.setUnitIncrement(10);
        arrival.setValue(10);

        Panel p = new Panel();
        p.setLayout(new GridLayout(1, 6));
        p.add(new Label("Slowdown"));
        p.add(slowdown);
        p.add(new Label(""));
        p.add(new Label("Arrival"));
        p.add(arrival);
        p.add(new Label(""));

        canvas = new RoadCanvas();
        canvas.setBackground(Color.black);

        setLayout(new BorderLayout());
        add("North", p);
        add("Center", canvas);

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
    }

    public double getSlowdown() {
        return 0.01 * slowdown.getValue();
    }

    public double getArrival() {
        return 0.01 * arrival.getValue();

    }

    public void run() {
        for ( ;; ) {
            canvas.update(buffer.getDrawGraphics(), getSlowdown(), getArrival());
            buffer.show();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
        }
     }

    public static void main(String[] args) {
        RoadFrame f = new RoadFrame();
        f.init();
        f.run();
   }

    private RoadCanvas canvas;
    private BufferStrategy buffer;
    private Scrollbar slowdown;
    private Scrollbar arrival;
    private Thread runner;
}


class RoadCanvas extends Canvas {
    public RoadCanvas() {
        freeway = new Road();
        row = 29;
    }

    public void update(Graphics g, double slowdown, double arrival) {
        //xsize = getWidth();
        //ysize = getHeight;
        freeway.clear(g, row, XDOTDIST, DOTSIZE);
        freeway.update(slowdown, arrival);
        freeway.paint(g, row, XDOTDIST, DOTSIZE);
        g.dispose();
    }

    private final int DOTSIZE = 4;
    private final int XDOTDIST = 5;
    private Road freeway;
    private int row;
    private int xsize;
    private int ysize;
}


class Road {
    private static final int EMPTY = -1;

    public Road() {
        speed = new int[LENGTH];
        colors = new Color[LENGTH];
        count = 0;
        for (int i = 0; i < LENGTH; i++) { speed[i] = EMPTY; }
    }

    public void update(double prob_slowdown, double prob_create) {
        int i = 0;

        // skip location with no vehicle
        while (i < LENGTH && speed[i] == EMPTY)
            i++;

        while (i < LENGTH) {
            // randomly adjust speed of vehicle at current location
            if (Math.random() <= prob_slowdown && speed[i] > 0)
                speed[i]--;
            else if (speed[i] < MAXSPEED)
                speed[i]++;

            // reduce speed of vehicle at current location
            // depending on speed of vehicle in front
            int inext = i + 1;
            // skip location with no vehicle
            while (inext < LENGTH && speed[inext] == EMPTY)
                inext++;

            // in case there is another vehicle ..
            if (inext < LENGTH) {
                // reduce speed to avoid a crash
                if (speed[i] >= inext - i)
                    speed[i] = inext - i - 1;
            }

            // move vehicle to new location
            if (speed[i] > 0) {
                if (i + speed[i] < LENGTH) {
                    int ni = i + speed[i];
                    speed[ni] = speed[i];
                    colors[ni] = colors[i];
                }
                speed[i] = EMPTY;
            }

            // continue with next vehicle
            i = inext;
        }

        // randomly decide whether a new vehicle arrives
        // new vehicle has random speed
        if (Math.random() <= prob_create && speed[0] == EMPTY) {
            speed[0] = (int) (5.99 * Math.random());
            colors[0] = ++count % 10 == 0 ? Color.red : Color.blue;
        }
    }

    public void clear(Graphics g, int row, int dotdist, int dotsize) {
        for (int i = 0; i < LENGTH; i++) {
            if (speed[i] >= 0) {
                g.setColor(Color.black);
                g.fillRect(i * dotdist, row, dotsize, dotsize);
            }
        }
    }

    public void paint(Graphics g, int row, int dotdist, int dotsize) {
        for (int i = 0; i < LENGTH; i++) {
            if (speed[i] >= 0) {
                g.setColor(colors[i]);
                g.fillRect(i * dotdist, row, dotsize, dotsize);
            }
        }
    }

    public static final int LENGTH = 160;
    public static final int MAXSPEED = 5;

    private int[] speed;
    private Color[] colors;
    private int count;
}

