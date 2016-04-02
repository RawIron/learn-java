package distributedTransactions;

import junit.framework.TestCase;


public class ResourceTest extends TestCase {
    private Resource topic_empty() {
        return new Resource();
    }

    private Resource topic_written2Bytes() {
        Resource res = new Resource();
        byte[] record = new byte[] {122, 123};
        try {
            res.write(record);
        } catch (OutOfSpaceException e) {}
        return res;
    }

    public final void test_newResourceIsEmpty() {
        assertEquals(topic_empty().read().length, 0);
    }

    public final void test_writeToResource() {
        assertEquals(topic_written2Bytes().length(), 2);
    }

    public final void test_writeAndReadBack() {
        assertEquals(topic_written2Bytes().read()[0], 122);
        assertEquals(topic_written2Bytes().read()[1], 123);
    }

    public final void test_writeOverBuffer() {
        Resource res = new Resource();
        byte[] record = new byte[16382];
        try {
            res.write(record);
            assertTrue(false);
        } catch (OutOfSpaceException e) {
            assertTrue(true);
        }
    }
}
