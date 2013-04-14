
package distributedTransactions;

import junit.framework.TestCase;


public class ResourceTest extends TestCase {
    private Resource topic_empty() {
        return new Resource();
    }
    private Resource topic_written2Bytes() {
        Resource res = new Resource();
        byte[] record = new byte[] {122, 123};
        res.write(record);
        return res;
    }

    public final void test_newResourceIsEmpty() {
        assertEquals(topic_empty().read().length, 0);
    }

    public final void test_writeToResource() {
        assertEquals(topic_written2Bytes().length(), 2);
    }

}
