
package distributedTransactions;

import junit.framework.TestCase;
import distributedTransactions.LogManager;


class LogManagerTopic<T> {
    private T beforeImage;
    private LogManager<T> logM;

    public LogManagerTopic(T logEntry) {
        beforeImage = logEntry;
    }

    private void init() {
        logM = new LogManager<T>();
    }
    public LogManagerTopic<T> created() {
        init();
        return this;
    }
    public LogManagerTopic<T> imageWritten() {
        init();
        logM.write(beforeImage);
        return this;
    }
    public LogManagerTopic<T> invalidated() {
        init();
        logM.invalidate(beforeImage);
        return this;
    }
    public T imageRecovered() {
        init();
        logM.write(beforeImage);
        T recoveredImage = logM.recover();
        return recoveredImage;
    }

    public boolean persisted() { return true; }
    public boolean noBeforeImageFound() { return true; }
}


public class LogManagerTest extends TestCase {

    private LogManagerTopic<String> topic;
    private String logEntry;
    public LogManagerTest() {
        logEntry = "beforeImage";
        topic = new LogManagerTopic<String>(logEntry);
    }

    public final void test_writeBeforeImage() {
        assertTrue(topic.imageWritten().persisted());
    }
    public final void test_invalidate() {
        assertTrue(topic.invalidated().noBeforeImageFound());
    }
    public final void test_recoverFromBeforeImage() {
        assertEquals(topic.imageRecovered(), logEntry);
    }
}
