import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;


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

        Label slowdownLabel = new Label();
        slowdownLabel.setText("Slowdown  " + slowdown.getValue());
        slowdown.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                slowdownLabel.setText("Slowdown  " + slowdown.getValue());
            }
        });

        arrival = new Scrollbar(Scrollbar.HORIZONTAL, 0, 0, 0, 100);
        arrival.setUnitIncrement(10);
        arrival.setValue(10);

        Label arrivalLabel = new Label();
        arrivalLabel.setText("Arrival  " + arrival.getValue());
        arrival.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                arrivalLabel.setText("Arrival  " + arrival.getValue());
            }
        });

        Panel parameterPanel = new Panel();
        parameterPanel.setLayout(new GridLayout(1, 4));
        parameterPanel.add(slowdownLabel);
        parameterPanel.add(slowdown);
        parameterPanel.add(arrivalLabel);
        parameterPanel.add(arrival);

        canvas = new Canvas();
        canvas.setBackground(Color.black);

        countLabel = new Label();
        countLabel.setText("Count  0");
        timeLabel = new Label();
        timeLabel.setText("Time  0");
        distanceLabel = new Label();
        distanceLabel.setText("Distance  0");
        minDurationLabel = new Label();
        minDurationLabel.setText("MinDuration  0");
        maxDurationLabel = new Label();
        maxDurationLabel.setText("MaxDuration  0");
        throughputLabel = new Label();
        throughputLabel.setText("Throughput  0");
        latencyLabel = new Label();
        latencyLabel.setText("Latency  0");

        Panel metricPanel = new Panel();
        metricPanel.setLayout(new GridLayout(3, 3));
        metricPanel.add(countLabel);
        metricPanel.add(timeLabel);
        metricPanel.add(distanceLabel);
        metricPanel.add(minDurationLabel);
        metricPanel.add(maxDurationLabel);
        metricPanel.add(new Label(""));
        metricPanel.add(throughputLabel);
        metricPanel.add(latencyLabel);

        frame.setLayout(new BorderLayout());
        frame.add("North", parameterPanel);
        frame.add("Center", canvas);
        frame.add("South", metricPanel);

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

            countLabel.setText("Count  " + freeway.count);
            timeLabel.setText("Time  " + freeway.ticks);
            distanceLabel.setText("Distance  " + freeway.distance);
            minDurationLabel.setText("MinDuration  " + freeway.minDuration);
            maxDurationLabel.setText("MaxDuration  " + freeway.maxDuration);
            throughputLabel.setText("Throughput  " + freeway.throughput);
            latencyLabel.setText("Latency  " + freeway.latency);

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
    private Label countLabel;
    private Label timeLabel;
    private Label distanceLabel;
    private Label minDurationLabel;
    private Label maxDurationLabel;
    private Label throughputLabel;
    private Label latencyLabel;
    private static final int DOTSIZE = 4;
    private static final int XDOTDIST = 5;
    private static final int ROW = 29;
    private Road freeway;
}


class Road {
    private class Car {
        public Car(Color _color, int _speed, int _duration) {
            color = _color;
            speed = _speed;
            duration = _duration;
        }
        public Color color;
        public int speed;
        public int duration;
    }

    public Road() {
        speed = new Car[LENGTH];
        for (int i = 0; i < LENGTH; i++) { speed[i] = null; }

        count = 0;
        ticks = 0;
        distance = LENGTH;
        maxDuration = 0;
        minDuration = LENGTH;
        throughput = 0;
        latency = 0;
    }

    public void update(double probabilitySlowdown, double probabilityArrival) {
        int i = 0;

        // skip location with no vehicle
        while (i < LENGTH && speed[i] == null)
            i++;

        while (i < LENGTH) {
            Car driveCar = speed[i];
            driveCar.duration++;
            // randomly adjust speed of vehicle at current location
            if (Math.random() <= probabilitySlowdown && driveCar.speed > 0)
                driveCar.speed--;
            else if (driveCar.speed < MAXSPEED)
                driveCar.speed++;

            // reduce speed of vehicle at current location
            // depending on speed of vehicle in front
            int inext = i + 1;
            // skip location with no vehicle
            while (inext < LENGTH && speed[inext] == null)
                inext++;
            // in case there is another vehicle ..
            if (inext < LENGTH) {
                // reduce speed to avoid a crash
                if (driveCar.speed >= inext - i)
                    driveCar.speed = inext - i - 1;
            }

            // move vehicle to new location
            if (driveCar.speed > 0) {
                if (i + driveCar.speed < LENGTH) {
                    int ni = i + driveCar.speed;
                    speed[ni] = driveCar;
                }
                else {
                    if (driveCar.duration > maxDuration) {
                        maxDuration = driveCar.duration;
                    }
                    if (driveCar.duration < minDuration) {
                        minDuration = driveCar.duration;
                    }
                    driveCar = null;
                }
                speed[i] = null;
            }

            // continue with next vehicle
            i = inext;
        }

        // randomly decide whether a new vehicle arrives
        // new vehicle has random speed
        if (Math.random() <= probabilityArrival && speed[0] == null) {
            speed[0] = new Car(
                ++count % 10 == 0 ? Color.red : Color.blue,
                (int) (5.99 * Math.random()),
                1);
        }

        ++ticks;
    }

    public void clear(Graphics g, int row, int dotdist, int dotsize) {
        for (int i = 0; i < LENGTH; i++) {
            if (speed[i] != null) {
                g.setColor(Color.black);
                g.fillRect(i * dotdist, row, dotsize, dotsize);
            }
        }
    }

    public void paint(Graphics g, int row, int dotdist, int dotsize) {
        for (int i = 0; i < LENGTH; i++) {
            if (speed[i] != null) {
                g.setColor(speed[i].color);
                g.fillRect(i * dotdist, row, dotsize, dotsize);
            }
        }
    }

    private final int LENGTH = 160;
    private final int MAXSPEED = 5;
    private Car[] speed;

    public int count;
    public int ticks;
    public int distance;
    public int minDuration;
    public int maxDuration;
    public int throughput;
    public int latency;
}

