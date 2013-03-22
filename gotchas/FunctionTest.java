
package gotchas;

import junit.framework.TestCase;


class Function {
    public void giveInteger(Integer value) {
        value += 4;
    }
    public void giveArray(int[] value) {
        value[1] = 4;
    }
}

public class FunctionTest extends TestCase {

    public FunctionTest() {
        super();
    }

    protected Function topic() {
        return new Function();
    }

    public final void test_passInteger() {
        Function f = topic();
        Integer value = new Integer(2);
        f.giveInteger(value);
        assertEquals((int)value,2);
    }

    public final void test_passIntArray() {
        Function f = topic();
        int[] value = {1, 2};
        f.giveArray(value);
        assertEquals(value[1], 4);
    }
}
