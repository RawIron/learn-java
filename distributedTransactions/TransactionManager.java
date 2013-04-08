
package distributedTransactions;

public class TransactionManager {

    public static final int Idle = -1;
    public static final int Open = 0;
    public static final int Started = 1;
    public static final int Prepared = 2;
    public static final int Committed = 3;

    private int currentState;

    public void open() {
        currentState = TransactionManager.Open;
    }
    public void start() {
        currentState = TransactionManager.Started;
    }
    public void prepare() {
        currentState = TransactionManager.Prepared;
    }
    public void commit() {
        currentState = TransactionManager.Committed;
    }

    public void resourceManagerIs(ResourceManager rm) {};
    public int state() { return currentState; }
}
