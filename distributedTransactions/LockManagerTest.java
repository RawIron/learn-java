
package distributedTransactions;

import junit.framework.TestCase;


public class LockManagerTest extends TestCase {

    public final void test_createLockM() {
        Resource resource = new Resource();
        LockManager lockM = new LockManager(resource);
        assertNotNull(lockM);
    }

    public final void test_acquireLock() {
    }

    public final void test_releaseExistingLock() {
    }

    public final void test_releaseAlreadyReleasedLock() {
    }

    public final void test_acquireAndReleaseLock() {
    }
}

