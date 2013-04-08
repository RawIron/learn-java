
package distributedTransactions;


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

    public void resourceManagerIs(ResourceManager rm) {};
    public TransactionState state() { return currentState; }
}
