package gotchas;

import junit.framework.TestCase;


class VowsTestCase extends TestCase {
    public VowsTestCase() {
        super();
    }
    public VowExceptionAssert expect(Object outcome) {
        VowExceptionAssert v = new VowExceptionAssert(outcome);
        return v;
    }
    public boolean topic() {
        // set handler for exception
        return false;
    }
    public VowBooleanAssert expect(boolean outcome) {
        VowBooleanAssert v = new VowBooleanAssert(outcome);
        return v;
    }
}


class VowBooleanAssert extends TestCase {
    boolean outcome = false; 
    public VowBooleanAssert(boolean outcome) {
        this.outcome = outcome;
    }
    public void to_be_false() {
        assertEquals(outcome, false);
    }
    public void to_be_true() {
        assertEquals(outcome, true);
    }
}


class VowExceptionAssert extends TestCase {
    Object outcome = null; 
    public VowExceptionAssert(Object outcome) {
        this.outcome = outcome;
    }
    public void to_be_an_error_like(String exceptionClassName) {
    }
}

