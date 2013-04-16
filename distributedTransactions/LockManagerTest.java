
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

    public final void test_createLockM() {
        assertNotNull(topic());
    }

    public final void test_acquireLock() {
        assertTrue(topic().lock(callback));
    }

    public final void test_releaseExistingLock() {
    }

    public final void test_releaseAlreadyReleasedLock() {
    }

    public final void test_acquireAndReleaseLock() {
    }
}

