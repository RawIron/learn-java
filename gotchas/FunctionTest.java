
package gotchas;

import junit.framework.TestCase;


class Function {
    public void giveInteger(Integer value) {
        value += 4;
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
}
