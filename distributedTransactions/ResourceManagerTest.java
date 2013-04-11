
package distributedTransactions;

import junit.framework.TestCase;


public class ResourceManagerTest extends TestCase {

    private ResourceManager topic() {
        Resource r = new Resource();
        LockManager lockm = new LockManager(r);
        LogManager logm = new LogManager();
        TransactionManager tm = new TransactionManager();
        ResourceManager rm = new ResourceManager(r, tm, lockm, logm);
        return rm;
    }

    public final void test_isReady() {
        ResourceManager rm = topic();
        rm.isReady();
        assertEquals(rm.state(), ResourceState.Open);
    }

    public final void test_prepare() {
        ResourceManager rm = topic();
        rm.isReady();
        rm.prepare();
        assertEquals(rm.state(), ResourceState.Prepared);
    }
 
    public final void test_commit() {
        ResourceManager rm = topic();
        rm.isReady();
        rm.prepare();
        rm.commit();
        assertEquals(rm.state(), ResourceState.Committed);
    }
}
