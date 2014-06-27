package gotchas;

import junit.framework.TestCase;


import java.io.UnsupportedEncodingException;


public class Sha1Test extends TestCase {

    public final void test_string_bytes() {
        String message = "a1";
        byte [] bytes = null;

        try {
          bytes = message.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) { }

        assertEquals(bytes.length, 2);
    }


    public final void test_sha1() {
        String salt = "b2";
        String message = "a1";
        byte [] bytes = null;
    }
}
