package trafficSim.src;

import java.lang.Math;
import java.io.*;
import java.util.logging.*;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;


/**
 * animate the movement on the road
 * show simulation metrics
 */
class RoadSimGui implements Animator {

    public RoadSimGui() {}

    private class ParameterPanel extends Panel implements Parameters {

        public ParameterPanel() {
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

            playback = new Scrollbar(Scrollbar.HORIZONTAL, 0, 0, 0, 600);
            playback.setUnitIncrement(25);
            playback.setValue(200);

            Label playbackLabel = new Label();
            playbackLabel.setText("Playback  " + playback.getValue());
            playback.addAdjustmentListener(new AdjustmentListener() {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e) {
                    playbackLabel.setText("Playback  " + playback.getValue());
                }
            });

            setLayout(new GridLayout(2, 4));
            add(slowdownLabel);
            add(slowdown);
            add(arrivalLabel);
            add(arrival);
            add(playbackLabel);
            add(playback);
            add(new Label(""));
            add(new Label(""));
        }

        public int slowdown() { return slowdown.getValue(); }
        public int arrival() { return arrival.getValue(); }
        public int playback() { return playback.getValue(); }

        private Scrollbar slowdown;
        private Scrollbar arrival;
        private Scrollbar playback;
    }

    private class MetricPanel extends Panel implements Monitor {

        public MetricPanel() {
            countLabel = new Label("Count  0");
            circulatingLabel = new Label("Circulating  0");
            distanceLabel = new Label("Distance  0");
            minLatencyLabel = new Label();
            minLatencyLabel.setText("MinLatency  0");
            maxLatencyLabel = new Label();
            maxLatencyLabel.setText("MaxLatency  0");
            avgLatencyLabel = new Label();
            avgLatencyLabel.setText("AvgLatency  0");
            throughputLabel = new Label();
            throughputLabel.setText("Throughput  0");
            timeLabel = new Label("Time  0");

            setLayout(new GridLayout(3, 3));
            add(timeLabel);
            add(countLabel);
            add(circulatingLabel);
            add(minLatencyLabel);
            add(maxLatencyLabel);
            add(avgLatencyLabel);
            add(throughputLabel);
            add(distanceLabel);
        }

        public void show(Metrics metrics) {
            countLabel.setText("Count  " + metrics.count);
            circulatingLabel.setText("Circulating  " + metrics.circulating);
            distanceLabel.setText("Distance  " + metrics.distance);
            minLatencyLabel.setText("MinLatency  " + metrics.minLatency);
            maxLatencyLabel.setText("MaxLatency  " + metrics.maxLatency);
            avgLatencyLabel.setText("AvgLatency  " + metrics.avgLatency);
            throughputLabel.setText("Throughput  " + metrics.throughput);
            timeLabel.setText("Time  " + metrics.ticks);
        }

        private Label countLabel;
        private Label circulatingLabel;
        private Label distanceLabel;
        private Label minLatencyLabel;
        private Label maxLatencyLabel;
        private Label avgLatencyLabel;
        private Label throughputLabel;
        private Label timeLabel;
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

        parameters = new ParameterPanel();
        monitor = new MetricPanel();

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);

        frame.setLayout(new BorderLayout());
        frame.add("North", parameters);
        frame.add("Center", canvas);
        frame.add("South", monitor);

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
    }

    public void paint(Skip move) {}

    public void paint(Move move) {
        Graphics g = buffer.getDrawGraphics();

        g.setColor( Color.black );
        g.fillRect( move.fromA * XDOTDIST, ROW, DOTSIZE, DOTSIZE );
        g.setColor( move.item.color() );
        g.fillRect( move.toB * XDOTDIST, ROW, DOTSIZE, DOTSIZE );

        g.dispose();
        buffer.show();
    }

    public void paint(Erase move) {
        Graphics g = buffer.getDrawGraphics();

        g.setColor( Color.black );
        g.fillRect( move.fromA * XDOTDIST, ROW, DOTSIZE, DOTSIZE );

        g.dispose();
        buffer.show();
    }

    public void paint(BringIn move) {
        Graphics g = buffer.getDrawGraphics();

        g.setColor( move.item.color() );
        g.fillRect( move.toB * XDOTDIST, ROW, DOTSIZE, DOTSIZE );

        g.dispose();
        buffer.show();
    }

    private static final int DOTSIZE = 4;
    private static final int XDOTDIST = 5;
    private static final int ROW = 44;

    public ParameterPanel parameters;
    public MetricPanel monitor;
    private BufferStrategy buffer;
}


/**
 * observability
 *
 * method is called in the main simulation loop
 */
interface Monitor {
    public void show(Metrics m);
}

/**
 * parameters which can be adjusted
 * while simulation is running
 *
 * methods are called in the main simulation loop
 */
interface Parameters {
    public int slowdown();
    public int arrival();
    public int playback();
}

/**
 * one method per simulation event
 * different designs
 *   paint(Object o)
 *   paint(<E>)
 */
interface Animator {
    public void paint(Skip m);
    public void paint(Move m);
    public void paint(Erase m);
    public void paint(BringIn m);
}


/**
 * construct Simulator object
 * run the main simulation loop
 */
public class RoadSim {

    public RoadSim(Road freeway, Metrics metrics, Parameters parameters, Monitor monitor, Animator ui) {
        this.freeway = freeway;
        this.metrics = metrics;
        this.parameters = parameters;
        this.monitor = monitor;
        this.ui = ui;
    }

    public void run() {
        for ( ;; ) {
            double probabilitySlowdown = 0.01 * parameters.slowdown();
            double probabilityArrival = 0.01 * parameters.arrival();

            Object move = freeway.step(probabilitySlowdown, probabilityArrival);
            if ( move instanceof Skip ) {
                ui.paint( (Skip) move );
            }
            else if ( move instanceof Move ) {
                ui.paint( (Move) move );
            }
            else if ( move instanceof Erase ) {
                ui.paint( (Erase) move );
            }
            else if ( move instanceof BringIn ) {
                ui.paint( (BringIn) move );
            }

            monitor.show(metrics);

            try {
                Thread.sleep( parameters.playback() );
            } catch (InterruptedException e) {}
        }
     }

    public static void main(String[] args) {
        RoadSimGui gui = new RoadSimGui();
        gui.init();

        Metrics metrics = new Metrics();
        Road road = new Road(metrics);
        RoadSim sim = new RoadSim(road, metrics, gui.parameters, gui.monitor, gui);
        sim.run();
   }

   private Parameters parameters;
   private Monitor monitor;
   private Animator ui;
   private Road freeway;
   private Metrics metrics;
}


/**
 * Simulation Events
 *
 * skip move := ()
 * move a piece := (n, m, piece)
 * erase a piece := (n, piece)
 * bring in a piece := (m=0, piece)
 */
class Skip {
    public Skip() {}
}

class Move {
    public Move(int from, int to, Car car) {
        fromA = from;
        toB = to;
        item = car;
    }

    public int fromA;   // item was there
    public int toB;     // and is here now
    public Car item;
}

class Erase {
    public Erase(int from, Car car) {
        fromA = from;
        item = car;
    }

    public int fromA;   // item was there
    public Car item;
}

class BringIn {
    public BringIn(Car car) {
        toB = 0;
        item = car;
    }

    public int toB;     // item is here now
    public Car item;
}


/**
 * capture telemetry data of the simulation
 */
class Metrics {
    public Metrics() {
        distance = 160;
        ticks = 0;
        count = 0;
        receivedCount = 0;
        crashes = 0;
        maxLatency = 0;
        minLatency = 999;
        avgLatency = 0;
        sumLatency = 0;
        throughput = 0;
    }

    public final int distance;     // length of the road in sectors
    public int ticks;              // 1 tick := all cars on the road have been moved
    public int count;              // cars left from departure
    public int receivedCount;      // cars arrived at destination
    public int circulating;        // cars on the road
    public int crashes;            // cars crashed
    public int minLatency;
    public int maxLatency;
    public int avgLatency;
    public int sumLatency;
    public int throughput;         // cars traveled the whole distance per 100 ticks
}


/**
 * simulate how the vehicles move on the road
 *    _drives_ the vehicles
 *    keeps track where the vehicles are located on the road
 */
class Road {

    public Road(Metrics metrics) {
        road = new Car[LENGTH];
        for (int i = 0; i < LENGTH; i++) { road[i] = null; }
        loc = 0;

        this.metrics = metrics;
        timerFlag = true;
        timers = new LinkedList<>();

        logger = Logger.getLogger(this.getClass().getName());
        logger.setLevel(Level.INFO);
    }

    /**
     * move one car to a new position
     */
    public Object step(double probabilitySlowdown, double probabilityArrival) {
        Object move = new Skip();

        // skip location with no vehicle
        while (loc < LENGTH && road[loc] == null)
            loc++;

        if (loc < LENGTH) {
            Car driveCar = road[loc];
            driveCar.elapsed(1);
            int gasPedal = 0;
            int brakePedal = 0;

            // randomly adjust speed of vehicle at current location
            if (Math.random() <= probabilitySlowdown && driveCar.speed() > 0) {
                gasPedal = 0;
            }
            else {
                gasPedal = 100;
            }

            // reduce speed of vehicle at current location
            // depending on speed of vehicle in front
            int inext = loc + 1;
            while (inext < LENGTH && road[inext] == null)
                ++inext;
            // in case there is another vehicle ..
            if (inext < LENGTH) {
                // reduce speed to avoid a crash
                if (driveCar.speed() >= inext - loc) {
                    gasPedal = 0;
                    brakePedal = 100;
                }
            }
            driveCar.accelerator(gasPedal);
            driveCar.brake(brakePedal);

            // move vehicle to new location
            if (driveCar.speed() > 0) {
                if (inext < LENGTH && driveCar.speed() >= inext - loc) {
                    // could not avoid a crash
                    ++metrics.crashes;
                    // fender bender, keep going
                    road[inext-1] = driveCar;

                    logger.fine(String.format("crash %d %d %d\n", driveCar.id(), driveCar.speed(), inext-1));
                    move = new Move( loc, inext-1, road[inext-1] );
                }
                else if (loc + driveCar.speed() < LENGTH) {
                    int nloc = loc + driveCar.speed();
                    road[nloc] = driveCar;

                    logger.fine(String.format("move %d %d %d\n", driveCar.id(), driveCar.speed(), nloc));
                    move = new Move( loc, nloc, road[nloc] );
                }
                else {
                    metrics.receivedCount++;
                    if (driveCar.traveltime() > metrics.maxLatency) {
                        metrics.maxLatency = driveCar.traveltime();
                    }
                    if (driveCar.traveltime() < metrics.minLatency) {
                        metrics.minLatency = driveCar.traveltime();
                    }
                    metrics.sumLatency += driveCar.traveltime();
                    metrics.avgLatency = metrics.sumLatency / metrics.receivedCount;
                    if ( driveCar.called() == Category.PACER ) {
                        metrics.throughput = CARBATCH * 100 / (metrics.ticks - timers.poll() + 1);
                    }

                    logger.fine(String.format("remove %d %d %d\n", driveCar.id(), driveCar.speed(), loc));
                    driveCar = null;
                    move = new Erase( loc, null );
                }
                road[loc] = null;
            }

            // continue with next vehicle
            loc = inext;
        }
        else {
            ++metrics.ticks;
            loc = 0;
            // randomly decide whether a new vehicle arrives
            // new vehicle has random speed
            if (Math.random() <= probabilityArrival && road[0] == null) {
                ++metrics.count;

                if (timerFlag) {
                    timers.add(metrics.ticks);
                    timerFlag = false;
                }

                Car newCar;
                if (metrics.count % CARBATCH == 0) {
                    timerFlag = true;
                    newCar = Car.create(Category.PACER);
                }
                else {
                    newCar = Car.create(Category.REGULAR);
                }
                newCar.accelerator( (int) (99.99 * Math.random()) );
                road[loc] = newCar;

                move = new BringIn( road[loc] );
            }
        }

        metrics.circulating = metrics.count - metrics.receivedCount;

        return move;
    }

    private Logger logger;

    private final int LENGTH = 160;   // the road is divided into a number of sectors
    private final int CARBATCH = 10;
    private Car[] road;               // road is a list of sectors, there can only be 1 car in a sector
    private int loc;                  //

    private Metrics metrics;
    private boolean timerFlag;     // true := start a timer
    private Queue<Integer> timers; // push the tick a timer was started into a queue
}


/**
 * no need for an interface at this point
 * just practice the usage of an interface in the design
 */
interface Vehicle {
    int brake(int position);          // new position of brake pedal
    int accelerator(int position);    // new position of accelerator pedal
    int elapsed(int ticks);       // time elapsed in ticks
    // methods to read state
    long id();
    Color color();
    Category called();
    int speed();
    int traveltime();
}

/**
 * using enum instead of strings
 *  client code can be checked by compiler
 *  clients need to recompile when categories change
 */
enum Category {
    REGULAR,
    PACER
}

/**
 * very simplistic car model
 *  press down or release accelerator pedal
 *  press down or release brake pedal
 * linear response of speed to pedal movements
 *
 * all cars behave the same
 *  no need for sub-classing yet
 */
class Car implements Vehicle {
    private static long sequence = 0;

    /**
     * provide a factory method
     *   restrict what can be constructed
     *   hide the details of construction
     */
    public static Car create(Category category) {
        switch(category) {
        case REGULAR:
            return new Car(category, Color.blue, Color.yellow, Color.green);
        case PACER:
            return new Car(category, Color.red, Color.orange, Color.pink);
        }
        return null;
    }

    private Car(Category called, Color color, Color slowerColor, Color fasterColor) {
        this.called = called;
        this.color = color;
        this.slowerColor = slowerColor;
        this.fasterColor = fasterColor;
        id = ++sequence;
        acceleratorAt = 0;
        brakeAt = 0;
        topSpeed = 10;
        speed = 0;
        speedChange = 0;
        traveltime = 0;
    }

    /**
     * @param pos position of the break pedal, between 0 and 100
     *            0 = released all the way
     *            100 = pressed down all the way
     * @return current speed
     */
    @Override
    public int brake(int pos) {
        final int delay = 5;   // ticks it takes to go from topspeed to 0
        final int range = 100;  // discrete points to approximate continuous movement
        int deltaSpeed = (int) Math.floor( ((double) pos / (delay * range)) * topSpeed );
        speed = Math.max(0, speed - deltaSpeed);
        speedChange -= deltaSpeed;
        brakeAt = pos;
        return speed;
    }

    /**
     * @param pos position of the accelerator pedal, between 0 and 100
     *            0 = released all the way
     *            100 = pressed down all the way
     * @return current speed
     */
    @Override
    public int accelerator(int pos) {
        final int delay = 10;   // ticks it takes to go from 0 to topspeed
        final int range = 100;  // discrete points to approximate continuous movement
        if ( pos - acceleratorAt >= 0 ) {
            int deltaSpeed = (int) Math.ceil( ((double) pos / (delay * range)) * topSpeed );
            speed = Math.min(speed + deltaSpeed, topSpeed);
            speedChange += deltaSpeed;
        }
        else {
            int deltaSpeed = (int) Math.floor( ((double) pos / (delay * range)) * topSpeed );
            speed = Math.max(0, speed - deltaSpeed);
            speedChange -= deltaSpeed;
        }
        acceleratorAt = pos;
        return speed;
    }

    /**
     *  simulator has the central time
     *
     *  @param incr ticks passed since last call from simulator
     *  @return total time traveled in ticks
     */
    @Override
    public int elapsed(int incr) {
        speedChange = 0;
        traveltime += incr;
        return traveltime;
    }

    /**
     * client depends on an interface
     *  expose state to the client via methods
     *  interface cannot expose class variables
     */
    @Override
    public Color color() {
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

    @Override
    public int speed() {
        return speed;
    }

    @Override
    public int traveltime() {
        return traveltime;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public Category called() {
        return called;
    }

    private final long id;
    private final Category called;
    private final Color color;        // color signals car moves at constant speed
    private final Color slowerColor;  // color signals car is slowing down
    private final Color fasterColor;  // color signals car is accelerating
    private final int topSpeed;       // in this simplistic model of a vehicle it is constant
                                      // car can at most travel this amount of road sectors within 1 tick
    private int acceleratorAt;  // current position of accelerator pedal
    private int brakeAt;        // current position of brake pedal
    private int speed;          // current speed
    private int speedChange;    // change in speed since speed at (ticks - 1)
    private int traveltime;     // traveltime in ticks
}

