package trafficSim.src;

import junit.framework.TestCase;
import trafficSim.src.RoadSim;


public class RoadSimTest extends TestCase {

    public RoadSimTest() {
        super();
    }

    public final void testMaxAcceleration() {
        Car car = Car.create(Category.REGULAR);
        car.accelerator(100);
        assertEquals(car.speed(), 1);
    }

    public final void testReachesTopspeed() {
        Car car = Car.create(Category.REGULAR);
        for (int i=0; i<10; ++i) {
            car.accelerator(100);
        }
        assertEquals(car.speed(), 10);
    }

    public final void testBrakeStop() {
        Car car = Car.create(Category.REGULAR);
        car.accelerator(100);
        car.accelerator(100);
        car.brake(100);
        assertEquals(car.speed(), 0);
    }

    public final void testBrakes() {
        Car car = Car.create(Category.REGULAR);
        car.accelerator(100);
        car.accelerator(100);
        car.accelerator(100);
        car.brake(100);
        assertEquals(car.speed(), 1);
    }
}
