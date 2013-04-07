
package distributedTransactions;

import junit.framework.TestCase;


public class TransactionManagerTest {

    private TransactionManager topic() {
        ResourceManager rm1 = new ResourceManager();
        ResourceManager rm2 = new ResourceManager();
        TransactionManager tm = new TransactionManager();
        tm.addResourceManager(rm1);
        tm.addResourceManager(rm2);
        return tm;
    }

    public final void test_open() {
        TransactionManager tm = topic();
        tm.open();
        assertEquals(tm.resourceCountIs(), 2);
        assertEquals(tm.stateIs(), tm.Open);
    }

    public final void test_start() {
        TransactionManager tm = topic();
        tm.open();
        tm.start();
        assertEquals(tm.stateIs(), tm.Started);
    }

    public final void test_commit() {
        TransactionManager tm = topic();
        tm.open();
        tm.start();
        tm.commit();
        assertEquals(tm.stateIs(), tm.Committed);
    }
}
