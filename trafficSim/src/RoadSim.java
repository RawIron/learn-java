import java.util.LinkedList;
import java.util.Queue;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;


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

    private class MetricPanel extends Panel implements Metrics {

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

        public void show(Road freeway) {
            countLabel.setText("Count  " + freeway.count);
            circulatingLabel.setText("Circulating  " + freeway.circulating);
            distanceLabel.setText("Distance  " + freeway.distance);
            minLatencyLabel.setText("MinLatency  " + freeway.minLatency);
            maxLatencyLabel.setText("MaxLatency  " + freeway.maxLatency);
            avgLatencyLabel.setText("AvgLatency  " + freeway.avgLatency);
            throughputLabel.setText("Throughput  " + freeway.throughput);
            timeLabel.setText("Time  " + freeway.ticks);
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
        metrics = new MetricPanel();

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);

        frame.setLayout(new BorderLayout());
        frame.add("North", parameters);
        frame.add("Center", canvas);
        frame.add("South", metrics);

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
    }

    public void paint(Skip move) {}

    public void paint(Move move) {
        Graphics g = buffer.getDrawGraphics();

        g.setColor( Color.black );
        g.fillRect( move.fromA * XDOTDIST, ROW, DOTSIZE, DOTSIZE );
        g.setColor( move.item.showColor() );
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

        g.setColor( move.item.showColor() );
        g.fillRect( move.toB * XDOTDIST, ROW, DOTSIZE, DOTSIZE );

        g.dispose();
        buffer.show();
    }

    private static final int DOTSIZE = 4;
    private static final int XDOTDIST = 5;
    private static final int ROW = 44;

    public ParameterPanel parameters;
    public MetricPanel metrics;
    private BufferStrategy buffer;
}


interface Metrics {
    public void show(Road r);
}

interface Parameters {
    public int slowdown();
    public int arrival();
    public int playback();
}

interface Animator {
    public void paint(Skip m);
    public void paint(Move m);
    public void paint(Erase m);
    public void paint(BringIn m);
}


public class RoadSim {

    public RoadSim(Road _freeway, Parameters _parameters, Metrics _metrics, Animator _ui) {
        freeway = _freeway;
        parameters = _parameters;
        metrics = _metrics;
        ui = _ui;
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

            metrics.show(freeway);

            try {
                Thread.sleep( parameters.playback() );
            } catch (InterruptedException e) {}
        }
     }

    public static void main(String[] args) {
        RoadSimGui gui = new RoadSimGui();
        gui.init();
        RoadSim sim = new RoadSim(new Road(), gui.parameters, gui.metrics, gui);
        sim.run();
   }

   private Parameters parameters;
   private Metrics metrics;
   private Animator ui;
   private Road freeway;
}


/*
 * skip move := ()
 * move a piece := (n, m, piece)
 * erase a piece := (n, piece)
 * bring in a piece := (m=0, piece)
 */
class Skip {
    public Skip() {}
}

class Move {
    public Move(int _from, int _to, Car _car) {
        fromA = _from;
        toB = _to;
        item = _car;
    }

    public int fromA;   // item was there
    public int toB;     // and is here now
    public Car item;
}

class Erase {
    public Erase(int _from, Car _car) {
        fromA = _from;
        item = _car;
    }

    public int fromA;   // item was there
    public Car item;
}

class BringIn {
    public BringIn(Car _car) {
        toB = 0;
        item = _car;
    }

    public int toB;     // and is here now
    public Car item;
}


/*
 * no need for an interface at this point
 * just added to practice the syntax
 */
interface Vehicle {
    int slowdown(int decrement);
    int accelerate(int increment);
    int elapsed(int increment);
    Color showColor();
}

enum Category {
    REGULAR,
    PACER
}

/*
 */
class Car implements Vehicle {
    /*
     * provide a factory method
     * restrict what can be constructed
     * hide the details of construction
     *
     * all cars behave the same
     * no need for sub-classing yet
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

    private Car(Category _called, Color _color, Color _slowerColor, Color _fasterColor) {
        called = _called;
        color = _color;
        slowerColor = _slowerColor;
        fasterColor = _fasterColor;
        speed = 0;
        speedChange = 0;
        latency = 0;
    }

    public Category called;
    private Color color;        // color signals car moves at constant speed
    private Color slowerColor;  // color signals car is slowing down
    private Color fasterColor;  // color signals car is accelerating
    public int speed;           // actual speed
    private int speedChange;    // change in speed from speed at (ticks - 1)
    public int latency;         // traveltime in ticks

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


/*
 */
class Road {

    public Road() {
        road = new Car[LENGTH];
        for (int i = 0; i < LENGTH; i++) { road[i] = null; }

        loc = 0;

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


    public Object step(double probabilitySlowdown, double probabilityArrival) {
        Object move = new Skip();

        // skip location with no vehicle
        while (loc < LENGTH && road[loc] == null)
            loc++;

        if (loc < LENGTH) {
            Car driveCar = road[loc];
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
            int inext = loc + 1;
            while (inext < LENGTH && road[inext] == null)
                inext++;
            // in case there is another vehicle ..
            if (inext < LENGTH) {
                // reduce speed to avoid a crash
                if (driveCar.speed >= inext - loc) {
                    driveCar.slowdown( driveCar.speed - (inext - loc - 1) );
                }
            }

            // move vehicle to new location
            if (driveCar.speed > 0) {
                if (loc + driveCar.speed < LENGTH) {
                    int nloc = loc + driveCar.speed;
                    road[nloc] = driveCar;

                    move = new Move( loc, nloc, road[nloc] );
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
                    if ( driveCar.called == Category.PACER ) {
                        throughput = CARBATCH * 100 / (ticks - timers.poll() + 1);
                    }
                    driveCar = null;

                    move = new Erase( loc, null );
                }
                road[loc] = null;
            }

            // continue with next vehicle
            loc = inext;
        }
        else {
            ++ticks;
            loc = 0;
            // randomly decide whether a new vehicle arrives
            // new vehicle has random speed
            if (Math.random() <= probabilityArrival && road[0] == null) {
                ++count;

                if (timerFlag) {
                    timers.add(ticks);
                    timerFlag = false;
                }

                Car newCar;
                if (count % CARBATCH == 0) {
                    timerFlag = true;
                    newCar = Car.create(Category.PACER);
                }
                else {
                    newCar = Car.create(Category.REGULAR);
                }
                newCar.accelerate( (int) (5.99 * Math.random()) );
                road[loc] = newCar;

                move = new BringIn( road[loc] );
            }
        }

        circulating = count - receivedCount;

        return move;
    }

    private final int LENGTH = 160;   // the road is divided into a number of sectors
    private final int MAXSPEED = 5;   // car can at most travel this amount of sectors within 1 tick
    private final int CARBATCH = 10;
    private Car[] road;               // road is a list of sectors, there can only be 1 car in a sector
    private int loc;                  //

    public int count;              // cars left from departure
    public int receivedCount;      // cars arrived at destination
    public int circulating;
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

