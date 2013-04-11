
package distributedTransactions;


class TransactionId {
}

final class TransactionState {
    public static final TransactionState Idle = new TransactionState();
    public static final TransactionState Open = new TransactionState();
    public static final TransactionState Started = new TransactionState();
    public static final TransactionState Prepared = new TransactionState();
    public static final TransactionState Committed = new TransactionState();
    
    private TransactionState() {}
}


public class TransactionManager {

    private TransactionState currentState;
    private ResourceManager localResourceManager;

    public void open() {
        currentState = TransactionState.Open;
    }
    public void start() {
        currentState = TransactionState.Started;
    }
    public void prepare() {
        currentState = TransactionState.Prepared;
    }
    public void commit() {
        currentState = TransactionState.Committed;
    }

    public void ready(ResourceManager rm) {
    }
    public void readyToCommit(ResourceManager rm) {
    }
    public void commitSuccess(ResourceManager rm) {
    }

    public void resourceManagerIs(ResourceManager rm) {};
    public TransactionState state() { return currentState; }
}
