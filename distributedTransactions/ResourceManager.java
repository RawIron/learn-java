
package distributedTransactions;


class Resource {
    public void write();
}

class LogManager {
    public void write();
}

class LockManager {
    public void lock();
    public void release();
}


public class ResourceManager {
    private LockManager lockm;
    private LogManager logm;
    private TransactionManager tm;
    private Resource r;
    
    public ResourceManager(TransactionManager tm, LockManager lockm, LogManager logm) {
        this.tm = tm;
        this.lockm = lockm;
        this.logm = logm;
    }

    public boolean isReady() {
        tm.ready();
    }
    public void prepare() {
        lockm.lock();
        logm.write();
        tm.readyToCommit();
    }
    public void commit() {
        r.write();
        logm.write();
        lockm.release();
        tm.commitSuccess();
    }
}
