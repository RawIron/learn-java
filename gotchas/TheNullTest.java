package gotchas;

import junit.framework.TestCase;


public class TheNullTest extends TestCase {

    public class ForIntegers extends TestCase {
        private int topic() {
            return 0;
        }
        public final void should_not_allow_assignment() {
            int outcome = topic();
            // outcome = (int) null;
            assertEquals(outcome, null);
        }
    }

    public class ForStrings extends TestCase {
        private String topic() {
            return null;
        }
        public final void should_not_allow_assignment() {
            String outcome = topic();
            assertEquals(outcome, null);
        }
    }

    public final void test_integers() {
        ForIntegers vow = new ForIntegers();
        vow.should_not_allow_assignment();
    }
    public final void test_strings() {
        ForStrings vow = new ForStrings();
        vow.should_not_allow_assignment();
    }
}
