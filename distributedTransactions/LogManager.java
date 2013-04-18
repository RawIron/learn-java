
package distributedTransactions;

import java.util.Vector;

class TransactionId {}

public class LogManager {
    Vector<byte[]> pagelog = new Vector<byte[]>(512, 128);
    public void write(byte[] entry) {
        pagelog.add(entry);
    }
    public void invalidate(byte[] entry) {
        pagelog.remove(entry);
    }
    public void recover(TransactionId tid) {
    }
}

