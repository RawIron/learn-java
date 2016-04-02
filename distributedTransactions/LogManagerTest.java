package distributedTransactions;

import junit.framework.TestCase;
import distributedTransactions.LogManager;


class LogManagerTopic<T> {
    private T beforeImage;
    private LogManagerMonitor<T> logM;

    public LogManagerTopic(T logEntry) {
        beforeImage = logEntry;
    }

    private void init() {
        logM = new LogManagerMonitor<T>(new LogManager<T>());
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

    public boolean isPersisted() { return logM.isPersisted(); }
    public boolean throwsNoBeforeImageFound() { return logM.throwsNoBeforeImageFound(); }
}


public class LogManagerTest extends TestCase {

    private LogManagerTopic<String> topic;
    private String logEntry;
    public LogManagerTest() {
        logEntry = "beforeImage";
        topic = new LogManagerTopic<String>(logEntry);
    }

    public final void test_writeBeforeImage() {
        assertTrue(topic.imageWritten().isPersisted());
    }
    public final void test_invalidateNonExistingBeforeImage() {
        assertTrue(topic.invalidated().throwsNoBeforeImageFound());
    }
    public final void test_recoverFromBeforeImage() {
        assertEquals(topic.imageRecovered(), logEntry);
    }
}
