import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class RoadSim {

    public RoadSim() {
    }

    public void init() {
        Frame frame = new Frame();
        frame.setSize(800, 200);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

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

        canvas = new Canvas();
        canvas.setBackground(Color.black);

        frame.setLayout(new BorderLayout());
        frame.add("North", p);
        frame.add("Center", canvas);

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
    }

    public void run() {
        freeway = new Road();
        for ( ;; ) {
            double probabilitySlowdown = 0.01 * slowdown.getValue();
            double probabilityArrival = 0.01 * arrival.getValue();

            Graphics g = buffer.getDrawGraphics();
            freeway.clear(g, ROW, XDOTDIST, DOTSIZE);
            freeway.update(probabilitySlowdown, probabilityArrival);
            freeway.paint(g, ROW, XDOTDIST, DOTSIZE);
            g.dispose();
            buffer.show();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
        }
     }

    public static void main(String[] args) {
        RoadSim sim = new RoadSim();
        sim.init();
        sim.run();
   }

    private Scrollbar slowdown;
    private Scrollbar arrival;
    private Canvas canvas;
    private BufferStrategy buffer;
    private static final int DOTSIZE = 4;
    private static final int XDOTDIST = 5;
    private static final int ROW = 29;
    private Road freeway;
}


class Road {

    public Road() {
        speed = new int[LENGTH];
        colors = new Color[LENGTH];
        for (int i = 0; i < LENGTH; i++) { speed[i] = EMPTY; }
        count = 0;
    }

    public void update(double probabilitySlowdown, double probabilityArrival) {
        int i = 0;

        // skip location with no vehicle
        while (i < LENGTH && speed[i] == EMPTY)
            i++;

        while (i < LENGTH) {
            // randomly adjust speed of vehicle at current location
            if (Math.random() <= probabilitySlowdown && speed[i] > 0)
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
        if (Math.random() <= probabilityArrival && speed[0] == EMPTY) {
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

    private static final int EMPTY = -1;
    private static final int LENGTH = 160;
    private static final int MAXSPEED = 5;

    private int[] speed;
    private Color[] colors;
    private int count;
}

