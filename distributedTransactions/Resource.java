
package distributedTransactions;

import java.lang.System;
import java.util.Arrays;


class Resource {
    private byte[] data = new byte[8192];
    private int position;

    public Resource() {
        this.position = 0;
    }

    public void write(byte[] change) {
        append(change);
    }
    public byte[] read() {
        return Arrays.copyOfRange(data, 0, position);
    }
    public int length() {
        return position;
    }

    private void append(byte[] change) {
        int has = this.length();
        int added = change.length;
        byte[] modified = new byte[has+added];
        System.arraycopy(data, 0, modified, 0, has);
        System.arraycopy(change, 0, modified, has, added);
        data = modified;
        position += added;
    }
}
