
package distributedTransactions;

public class TransactionManager {

    public static final int Open = 0;
    public static final int Started = 0;
    public static final int Prepared = 0;
    public static final int Committed = 0;

    private int currentState;

    public void open() {}
    public void start() {}
    public void prepare() {}
    public void commit() {}

    public void resourceManagerIs(ResourceManager rm) {};
    public int state() { return currentState; }
}
