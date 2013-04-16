
package distributedTransactions;

import junit.framework.TestCase;
import distributedTransactions.LockManager;



class Callback implements LockCallback {
    public void resourceIsAvailable() {
    }
}


public class LockManagerTest extends TestCase {
    private Callback callback;
    private LockManager topic() {
        Callback callback = new Callback();
        Resource resource = new Resource();
        LockManager lockM = new LockManager(resource);
        return lockM;
    }
    private LockManager topic_hasLock() {
        LockManager lockM = topic();
        lockM.lock(callback);
        return lockM;
    }
    private LockManager topic_releasedLock() {
        LockManager lockM = topic();
        lockM.lock(callback);
        lockM.release();
        return lockM;
    }

    public final void test_createLockM() {
        assertNotNull(topic());
    }

    public final void test_acquireLock() {
        assertTrue(topic().lock(callback));
    }

    public final void test_releaseExistingLock() {
        assertTrue(topic_hasLock().release());
    }

    public final void test_releaseAlreadyReleasedLock() {
        assertFalse(topic_releasedLock().release());
    }

    public final void test_acquireAndReleaseLock() {
    }
}

