
package distributedTransactions;

import junit.framework.TestCase;


public class TransactionManagerTest extends TestCase {

    private TransactionManager topic() {
        ResourceManager rm = new ResourceManager();
        TransactionManager tm = new TransactionManager();
        tm.resourceManagerIs(rm);
        return tm;
    }

    public final void test_open() {
        TransactionManager tm = topic();
        tm.open();
        assertEquals(tm.state(), tm.Open);
    }

    public final void test_start() {
        TransactionManager tm = topic();
        tm.open();
        tm.start();
        assertEquals(tm.state(), tm.Started);
    }

    public final void test_prepare() {
        TransactionManager tm = topic();
        tm.open();
        tm.prepare();
        assertEquals(tm.state(), tm.Prepared);
    }
 
    public final void test_commit() {
        TransactionManager tm = topic();
        tm.open();
        tm.prepare();
        tm.commit();
        assertEquals(tm.state(), tm.Committed);
    }
}
