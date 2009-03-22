package org.apps.butler.util.encypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class provides APIs to encrypt and decrypt data with cryptographic
 * algorithm DES/ECB/PKCS5Padding. And optionally use base64 to serialize the
 * encoded data to String. <br>
 * <br>
 * Example: <br>
 *
 * <pre>
 * HashMap&lt;String, Object&gt; map = new HashMap&lt;String, Object&gt;();
 * map.put("testkey1", "my value1");
 * map.put("testkey2", "my value2");
 * String encryptedStr = DesEncryptUtil.encryptBase64(key, map);
 * .....
 * HashMap&lt;String, Object&gt; map = DesEncryptUtil.decryptBase64(key, encryptedStr);
 * </pre>
 */
public class DesEncryptUtil {

	private static final Logger logger = LoggerFactory
	.getLogger(DesEncryptUtil.class);

    private static final String ENCODING = "UTF-8";

    /**
     * Encrypt an object to byte array with DES encryption and
     * use base64 to encode this byte array to String.
     * @param key encryption key
     * @param object the object to be encrypted
     * @return encrypted base 64 string
     * @throws Exception
     */
    public static String encryptBase64(String key, Object object) throws Exception {
        if (logger.isTraceEnabled()) {
            logger.trace("key: " + key + " object: " + object);
        }
        byte[] buf = encryptToByteArray(key, object);
        return Base64.encodeBytes(buf);
    }

    /**
     * Encrypt an object to byte array with DES encryption
     * @param key encryption key
     * @param object the object to be encrypted
     * @return encrypted byte array
     * @throws Exception
     */
    public static byte[] encryptToByteArray(String key, Object object) throws Exception {
        Cipher desCipher = getCipher(key, Cipher.ENCRYPT_MODE);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CipherOutputStream cos = new CipherOutputStream(bos, desCipher);
        ObjectOutputStream oos = new ObjectOutputStream(cos);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        byte[] buf = bos.toByteArray();
        return buf;
    }

    private static Object decrypt(String key, String encryptedStr, boolean isBase64) throws Exception {
        if (logger.isTraceEnabled()) {
            logger.trace("key: " + key + " encryptedStr: " + encryptedStr);
        }

        byte[] buf = null;
        if (isBase64) {
            buf = Base64.decode(encryptedStr);
        } else {
            buf = encryptedStr.getBytes(ENCODING);
        }

        Object obj = decryptFromByteArray(key, buf);
        return obj;
    }

    /**
     * Decrypt the byte array to original object.
     * @param key DES encryption key.
     * @param buf byte array.
     * @return The original object.
     * @throws Exception
     */
    private static Object decryptFromByteArray(String key, byte[] buf)
            throws Exception {
        Cipher desCipher = getCipher(key, Cipher.DECRYPT_MODE);
        ByteArrayInputStream bis = new ByteArrayInputStream(buf);
        CipherInputStream cis = new CipherInputStream(bis, desCipher);
        ObjectInputStream ois = new ObjectInputStream(cis);
        Object obj = (Object) ois.readObject();
        ois.close();
        return obj;
    }

    /**
     * Decrypt the base64 string to original object.
     * @param key DES encryption key.
     * @param encryptedStr
     * @return The original object.
     * @throws Exception
     */
    public static Object decryptBase64(String key, String encryptedStr) throws Exception {
        return decrypt(key, encryptedStr, true);
    }

    /**
     * Get the DES cipher with specified key and mode.
     * @param key The encryption key.
     * @param mode Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
     * @return The cipher object.
     * @throws Exception
     */
    private static Cipher getCipher(String key, int mode) throws Exception {
        byte keyArr[] = key.getBytes(ENCODING);
        DESKeySpec desKeySpec = new DESKeySpec(keyArr);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        desCipher.init(mode, secretKey);
        return desCipher;
    }
}
