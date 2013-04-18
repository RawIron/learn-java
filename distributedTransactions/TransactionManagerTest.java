
package distributedTransactions;

import junit.framework.TestCase;


public class TransactionManagerTest extends TestCase {

    private TransactionManager topic() {
        TransactionManager tm = new TransactionManager();
        Resource r = new Resource();
        LockManager lockM = new LockManager(r);
        LogManager logM = new LogManager();
        ResourceManager rm = new ResourceManager(r, tm, lockM, logM);
        tm.resourceManagerIs(rm);
        return tm;
    }

    public final void test_open() {
        TransactionManager tm = topic();
        tm.open();
        assertEquals(tm.state(), TransactionState.Open);
    }

    public final void test_start() {
        TransactionManager tm = topic();
        tm.open();
        tm.start();
        assertEquals(tm.state(), TransactionState.Started);
    }

    public final void test_prepare() {
        TransactionManager tm = topic();
        tm.open();
        tm.prepare();
        assertEquals(tm.state(), TransactionState.Prepared);
    }
 
    public final void test_commit() {
        TransactionManager tm = topic();
        tm.open();
        tm.prepare();
        tm.commit();
        assertEquals(tm.state(), TransactionState.Committed);
    }
}
