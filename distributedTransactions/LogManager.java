
package distributedTransactions;

import java.util.Vector;


class TransactionId {}


public class LogManager<T> {
    Vector<T> pagelog = new Vector<T>(512, 128);
    public void write(T entry) {
        pagelog.add(entry);
    }
    public void invalidate(T entry) {
        pagelog.remove(entry);
    }
    public T recover() {
        return pagelog.lastElement();
    }
}

