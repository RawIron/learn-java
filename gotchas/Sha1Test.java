package gotchas;

import junit.framework.TestCase;
//import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



public class Sha1Test extends TestCase {

  private String hexOfBytes(final byte[] bytes) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < bytes.length; i++)
      hexString.append(Integer.toHexString(0xFF & bytes[i]));
    return hexString.toString();
  }

  private String hexOfBytes2(final byte[] bytes) {
    StringBuffer hexString = new StringBuffer();
    for (byte b : bytes) {
        hexString.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
    }
    return hexString.toString();
  }

  private String hexOfBytes3(final byte[] bytes) {
    // Convert raw bytes to Hex
    //byte[] hexBytes = new Hex().encode(rawHmac);
    //  Covert array of Hex bytes to a String
    //return new String(hexBytes, "UTF-8");
    return null;
  }

 // private String base64OfBytes(final byte[] bytes) {
 //   return new String(Base64.encodeBase64(bytes));
 // }

  private byte[] bytesOfString(final String s) {
    byte[] bytes = null;
    try {
      bytes = s.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return bytes;
  }

  private String sha1(final String salt, final String message) {
    MessageDigest sha1digest = null;
    try {
      sha1digest = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }

    byte[] messageBytes = bytesOfString(message);
    byte[] saltBytes = bytesOfString(salt);
    sha1digest.update(messageBytes);
    byte[] signature = sha1digest.digest();

    return hexOfBytes2(signature);
  }

  private String hmacSha1(final String key, final String value) {
    try {
      // Get an hmac_sha1 key from the raw key bytes
      byte[] keyBytes = bytesOfString(key);
      SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

      // Get an hmac_sha1 Mac instance and initialize with the signing key
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(signingKey);

      // Compute the hmac on input data bytes
      byte[] rawHmac = mac.doFinal(bytesOfString(value));

      return hexOfBytes2(rawHmac);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  public final void test_string_bytes() {
    String message = "a1";
    byte[] bytes = null;
    bytes = bytesOfString(message);

    assertEquals(2, bytes.length);
    assertEquals(97, (int) (bytes[0] & 0xff));
    assertEquals(49, (int) (bytes[1] & 0xff));
  }

  public final void test_string_hex() {
    String message = "a1";
    byte[] bytes = null;
    bytes = bytesOfString(message);
    String hex = hexOfBytes(bytes);
    assertEquals(4, hex.length());
    assertEquals("6131", hex);
  }

  public final void test_hex_hex2() {
    String message = "a1";
    byte[] bytes = null;
    bytes = bytesOfString(message);
    String hex = hexOfBytes(bytes);
    String hex2 = hexOfBytes2(bytes);
    assertEquals(hex2, hex);
  }

  public final void test_sha1() {
    String salt = "b2";
    String message = "a1";
    String signature = sha1(salt, message);
    assertEquals("f29bc91bbdab169fc0c0a326965953d11c7dff83", signature);
  }

  public final void test_sha1_hmac() {
    String salt = "b2";
    String message = "a1";
    String signature = hmacSha1(salt, message);
    assertEquals("240d6b81caf2ee8cb4161065095ac681fc0ce44a", signature);
  }

}
