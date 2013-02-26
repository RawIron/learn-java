package gotchas;

import gotchas.VowsTestCase;


public class TheNullTest extends VowsTestCase {

    public class ForFloatTest extends VowsTestCase {
        private float topic() {
            return 0;
        }
        public final void test_should_not_allow_assignment() {
            // outcome = (float) null;
            expect(true).to_be_true();
            expect(topic()).to_be_null();
        }
    }

    public class ForStringsTest extends VowsTestCase {
        private String topic() {
            return null;
        }
        public final void test_should_not_allow_assignment() {
            expect(true).to_be_true();
            expect(topic()).to_be_null();
        }
    }
}
