package gotchas;

import junit.framework.TestCase;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Sha1Test extends TestCase {

  private String hexOfBytes(byte[] bytes) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < bytes.length; i++)
      hexString.append(Integer.toHexString(0xFF & bytes[i]));
    return hexString.toString();
  }

  private String sha1(String salt, String message) {
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }

    digest.update(message.getBytes());
    byte[] messageDigest = digest.digest();

    return hexOfBytes(messageDigest);
  }

  private byte[] bytesOfString(String s) {
    byte[] bytes = null;

    try {
      bytes = s.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) { }

    return bytes;
  }


  public final void test_string_bytes() {
    String message = "a1";
    byte[] bytes = null;

    bytes = bytesOfString(message);
    assertEquals(bytes.length, 2);
    assertEquals((int) (bytes[0] & 0xff), 97);
    assertEquals((int) (bytes[1] & 0xff), 49);
  }

  public final void test_sha1() {
    String salt = "b2";
    String message = "a1";
    byte[] bytes = null;
  }

}
