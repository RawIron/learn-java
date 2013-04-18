
package distributedTransactions;


final class ResourceState {
    public static final ResourceState Idle = new ResourceState();
    public static final ResourceState Open = new ResourceState();
    public static final ResourceState Started = new ResourceState();
    public static final ResourceState Prepared = new ResourceState();
    public static final ResourceState Committed = new ResourceState();
    
    private ResourceState() {}
}


public class ResourceManager implements LockCallback {
    private LockManager lockm;
    private LogManager logm;
    private TransactionManager tm;
    private Resource resource;
    private byte[] beforeImage;
    private ResourceState currentState;
    
    public ResourceManager(
        Resource resource,
        TransactionManager tm,
        LockManager lockm,
        LogManager logm)
    {
        this.resource = resource;
        this.tm = tm;
        this.lockm = lockm;
        this.logm = logm;
    }

    public boolean isReady() {
        tm.ready(this);
        currentState = ResourceState.Open;
        return true;
    }
    public void prepare() {
        lockm.lock(this);
        beforeImage = resource.read();
        logm.write(beforeImage);
        tm.readyToCommit(this);
        currentState = ResourceState.Prepared;
    }
    public void commit() {
        byte[] page = new byte[] {34,};
        try { resource.write(page); } catch(OutOfSpaceException e) {}
        logm.invalidate(beforeImage);
        lockm.release();
        tm.commitSuccess(this);
        currentState = ResourceState.Committed;
    }

    public ResourceState state() {
        return currentState;
    }

    public void resourceIsAvailable() {
    }
}

