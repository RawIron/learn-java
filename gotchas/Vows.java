package gotchas;

import junit.framework.TestCase;


class VowBrokenException extends Exception {
    private static final long uid = 0L;
    public VowBrokenException(String message) {
        super(message);
    }
}


class VowsTestCase extends TestCase {
    public VowsTestCase() {
        super();
    }
    public VowAssert expect(boolean outcome) {
        VowBooleanAssert v = new VowBooleanAssert(outcome);
        return v;
    }
}


abstract class VowAssert extends TestCase {
    public VowAssert() {
        super();
    }
    protected abstract boolean to_be_true() throws VowBrokenException;
    protected abstract boolean to_be_false() throws VowBrokenException;
}


class VowBooleanAssert extends VowAssert {
    boolean outcome = false; 
    public VowBooleanAssert(boolean outcome) {
        this.outcome = outcome;
    }
    public boolean to_be_false() throws VowBrokenException {
        assertEquals(outcome, false);
        outcome = !outcome;
        return to_be_true();
    }
    public boolean to_be_true() throws VowBrokenException {
        assertEquals(outcome, true);
        if (outcome != true) {
            throw new VowBrokenException("");
        }
        return true;
    }
}

