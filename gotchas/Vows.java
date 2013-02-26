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
    public VowBooleanAssert expect(boolean outcome) {
        VowBooleanAssert v = new VowBooleanAssert(outcome);
        return v;
    }
    public VowFloatAssert expect(float outcome) {
        VowFloatAssert v = new VowFloatAssert(outcome);
        return v;
    }
    public VowStringAssert expect(String outcome) {
        VowStringAssert v = new VowStringAssert(outcome);
        return v;
    }
}


class VowStringAssert extends TestCase {
    String outcome = ""; 
    public VowStringAssert(String outcome) {
        this.outcome = outcome;
    }
    public void to_equal(String expected) {
        assertEquals(outcome, expected);
    }
    public void not_to_equal(String expected) {
        assertEquals(outcome, expected);
    }
    public void to_be_string() {
        assertEquals(true, true);
    }
    public void to_be_null() {
        assertEquals(outcome, null);
    }
}

class VowFloatAssert extends TestCase {
    float outcome = 0; 
    public VowFloatAssert(float outcome) {
        this.outcome = outcome;
    }
    public void to_equal(float expected) {
        assertEquals(outcome, expected);
    }
    public void not_to_equal(float expected) {
        assertEquals(outcome, expected);
    }
    public void to_be_numeric() {
        assertEquals(true, true);
    }
    public void to_be_null() {
        assertEquals(false, true);
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

