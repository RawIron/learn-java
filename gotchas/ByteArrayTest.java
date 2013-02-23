package gotchas;

import gotchas.VowsTestCase;


public class ByteArrayTest extends VowsTestCase { 
    public ByteArrayTest() {
        super();
    }

    public boolean topic(byte needle) {
        byte[] haystack = new byte[]{(byte)'c', (byte)'\r', (byte)'a'};

        for (int i=0; i<haystack.length; ++i) {
            if (haystack[i] == needle)
                return true;
        }
        return false;
    }

    public final void test_shouldFindTheByteNeedle() {
        try {
        byte needle = (byte)'\r';
        expect(topic(needle)).to_be_true();
        } catch (VowBrokenException e) {}
    }
}

