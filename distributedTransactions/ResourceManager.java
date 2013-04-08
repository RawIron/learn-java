
package distributedTransactions;


public class ResourceManager {

    public static final int Open = 0;
    public static final int Started = 0;
    public static final int Prepared = 0;
    public static final int Committed = 0;

    public void open() {}
    public void start() {}
    public void prepare() {}
    public void commit() {}
    public int state() { return 0; }
}
