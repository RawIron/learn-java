package gotchas;

import junit.framework.TestCase;


public class UseOfFinalTest extends TestCase {
    public UseOfFinalTest() { super(); }

    public final void test_finalInBlock() {
        final int counter;
        counter = 4;
        ++counter;
        assertEquals(counter,4);
    }
}
