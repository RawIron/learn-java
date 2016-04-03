import java.lang.System;
import java.util.Arrays;

class OutOfSpaceException extends Exception {}

public class Resource {
    private final int BufferSize = 8192;
    private byte[] data = new byte[BufferSize];
    private int position;

    public Resource() {
        this.position = 0;
    }

    public void write(byte[] change) throws OutOfSpaceException {
        append(change);
    }
    public byte[] read() {
        return Arrays.copyOfRange(data, 0, position);
    }
    public int length() {
        return position;
    }

    private void append(byte[] change) throws OutOfSpaceException {
        int current = this.length();
        int added = change.length;
        if (!hasSpaceFor(current+added)) {
            throw new OutOfSpaceException();
        }
        byte[] modified = new byte[current+added];
        System.arraycopy(data, 0, modified, 0, current);
        System.arraycopy(change, 0, modified, current, added);
        data = modified;
        position += added;
    }

    private boolean hasSpaceFor(int numberOfBytes) {
        if (position + numberOfBytes < BufferSize) {
            return true;
        }
        return false;
    }
}
