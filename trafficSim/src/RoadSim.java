import java.util.LinkedList;
import java.util.Queue;
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
        minLatencyLabel = new Label();
        minLatencyLabel.setText("MinLatency  0");
        maxLatencyLabel = new Label();
        maxLatencyLabel.setText("MaxLatency  0");
        avgLatencyLabel = new Label();
        avgLatencyLabel.setText("AvgLatency  0");
        throughputLabel = new Label();
        throughputLabel.setText("Throughput  0");

        Panel metricPanel = new Panel();
        metricPanel.setLayout(new GridLayout(3, 3));
        metricPanel.add(countLabel);
        metricPanel.add(timeLabel);
        metricPanel.add(distanceLabel);
        metricPanel.add(minLatencyLabel);
        metricPanel.add(maxLatencyLabel);
        metricPanel.add(avgLatencyLabel);
        metricPanel.add(throughputLabel);

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
            minLatencyLabel.setText("MinLatency  " + freeway.minLatency);
            maxLatencyLabel.setText("MaxLatency  " + freeway.maxLatency);
            avgLatencyLabel.setText("AvgLatency  " + freeway.avgLatency);
            throughputLabel.setText("Throughput  " + freeway.throughput);

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
    private Label minLatencyLabel;
    private Label maxLatencyLabel;
    private Label avgLatencyLabel;
    private Label throughputLabel;
    private static final int DOTSIZE = 4;
    private static final int XDOTDIST = 5;
    private static final int ROW = 44;

    private Road freeway;
}


interface Vehicle {
    int slowdown(int decrement);
    int accelerate(int increment);
    int elapsed(int increment);
    Color showColor();
}


class Car implements Vehicle {
    public static Car create(String category) {
        switch(category) {
        case "regular":
            return new Car(category, Color.blue, Color.yellow, Color.green);
        case "pacer" :
            return new Car(category, Color.red, Color.orange, Color.pink);
        }
        return null;
    }

    private Car(String _called, Color _color, Color _slowerColor, Color _fasterColor) {
        called = _called;
        color = _color;
        slowerColor = _slowerColor;
        fasterColor = _fasterColor;
        speed = 0;
        speedChange = 0;
        latency = 0;
    }

    public String called;
    private Color color;       // used to mark begin and end of measurements for example
    private Color slowerColor;       // color signals car is slowing down
    private Color fasterColor;       // color signals car is accelerating
    public int speed;         // actual speed
    private int speedChange;   // change in speed from previous actual speed
    public int latency;       // traveltime

    @Override
    public int slowdown(int decr) {
        speed -= decr;
        speedChange -= decr;
        return speed;
    }

    @Override
    public int accelerate(int incr) {
        speed += incr;
        speedChange += incr;
        return speed;
    }

    @Override
    public int elapsed(int incr) {
        speedChange = 0;
        latency += incr;
        return latency;
    }

    @Override
    public Color showColor() {
        if (speedChange < 0) {
            return slowerColor;
        }
        else if (speedChange > 0) {
            return fasterColor;
        }
        else {
            return color;
        }
    }
}


class Road {
    public Road() {
        road = new Car[LENGTH];
        for (int i = 0; i < LENGTH; i++) { road[i] = null; }

        count = 0;
        receivedCount = 0;
        ticks = 0;
        distance = LENGTH;
        maxLatency = 0;
        minLatency = LENGTH;
        avgLatency = 0;
        sumLatency = 0;
        throughput = 0;
        timerFlag = true;
        timers = new LinkedList<>();
    }

    public void update(double probabilitySlowdown, double probabilityArrival) {
        int i = 0;

        // skip location with no vehicle
        while (i < LENGTH && road[i] == null)
            i++;

        while (i < LENGTH) {
            Car driveCar = road[i];
            driveCar.elapsed(1);

            // randomly adjust speed of vehicle at current location
            if (Math.random() <= probabilitySlowdown && driveCar.speed > 0) {
                driveCar.slowdown(1);
            }
            else if (driveCar.speed < MAXSPEED) {
                driveCar.accelerate(1);
            }

            // reduce speed of vehicle at current location
            // depending on speed of vehicle in front
            int inext = i + 1;
            while (inext < LENGTH && road[inext] == null)
                inext++;
            // in case there is another vehicle ..
            if (inext < LENGTH) {
                // reduce speed to avoid a crash
                if (driveCar.speed >= inext - i) {
                    driveCar.slowdown( driveCar.speed - (inext - i - 1) );
                }
            }

            // move vehicle to new location
            if (driveCar.speed > 0) {
                if (i + driveCar.speed < LENGTH) {
                    int ni = i + driveCar.speed;
                    road[ni] = driveCar;
                }
                else {
                    receivedCount++;
                    if (driveCar.latency > maxLatency) {
                        maxLatency = driveCar.latency;
                    }
                    if (driveCar.latency < minLatency) {
                        minLatency = driveCar.latency;
                    }
                    sumLatency += driveCar.latency;
                    avgLatency = sumLatency / receivedCount;
                    if ( driveCar.called == "pacer" ) {
                        throughput = BATCH * 100 / (ticks - timers.poll() + 1);
                    }
                    driveCar = null;
                }
                road[i] = null;
            }

            // continue with next vehicle
            i = inext;
        }

        // randomly decide whether a new vehicle arrives
        // new vehicle has random speed
        if (Math.random() <= probabilityArrival && road[0] == null) {
            if (timerFlag) {
                timers.add(ticks);
                timerFlag = false;
            }

            Car newCar;
            if (++count % BATCH == 0) {
                timerFlag = true;
                newCar = Car.create("pacer");
            }
            else {
                newCar = Car.create("regular");
            }
            newCar.accelerate( (int) (5.99 * Math.random()) );
            newCar.elapsed(1);
            road[0] = newCar;
        }

        ++ticks;
    }

    public void clear(Graphics g, int row, int dotdist, int dotsize) {
        for (int i = 0; i < LENGTH; i++) {
            if (road[i] != null) {
                g.setColor(Color.black);
                g.fillRect(i * dotdist, row, dotsize, dotsize);
            }
        }
    }

    public void paint(Graphics g, int row, int dotdist, int dotsize) {
        for (int i = 0; i < LENGTH; i++) {
            if (road[i] != null) {
                g.setColor( road[i].showColor() );
                g.fillRect( i * dotdist, row, dotsize, dotsize );
            }
        }
    }

    private final int LENGTH = 160;
    private final int MAXSPEED = 5;
    private final int BATCH = 10;
    private Car[] road;

    public int count;              // cars left from departure
    public int receivedCount;      // cars arrived at destination
    public int ticks;              // 1 tick := all cars on the road have been moved
    public int distance;
    public int minLatency;
    public int maxLatency;
    public int avgLatency;
    private int sumLatency;
    public int throughput;         // cars traveled the whole distance per 100 ticks
    private boolean timerFlag;     // true := start a timer
    private Queue<Integer> timers; // push timer into a queue
}

