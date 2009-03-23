package org.apps.butler.util.encypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apps.butler.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * This class provides APIs to encrypt and decrypt data with cryptographic
 * algorithm DES/ECB/PKCS5Padding. And use base64 to serialize the encoded data
 * to String. <br>
 * <br>
 * Example: <br>
 *
 * <pre>
 * HashMap&lt;String, Object&gt; map = new HashMap&lt;String, Object&gt;();
 * map.put(&quot;testkey1&quot;, &quot;my value1&quot;);
 * map.put(&quot;testkey2&quot;, &quot;my value2&quot;);
 * String encryptedStr = DesEncryptUtil.encryptBase64(key, map);
 * .....
 * HashMap&lt;String, Object&gt; map = DesEncryptUtil.decryptBase64(key, encryptedStr);
 * </pre>
 */
public class DesEncryptUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(DesEncryptUtil.class);

	private static final String SECURE_KEY_FILE_NAME = "secKey";

	private DesEncryptUtil() {
		// noop
	}

	/**
	 * Encrypt an object to byte array with DES encryption and use base64 to
	 * encode this byte array to String.
	 *
	 * @param object
	 *            the object to be encrypted
	 * @return encrypted string
	 * @throws Exception
	 */
	public static String encrypt(Object object) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.trace("object: " + object);
		}
		byte[] buf = encryptToByteArray(object);
		return Base64.encode(buf);
	}

	public static Object decrypt(String encryptedStr) throws Exception {
		if (logger.isTraceEnabled()) {
			logger.trace("encryptedStr: " + encryptedStr);
		}

		byte[] buf = null;
		buf = Base64.decode(encryptedStr);
		Object obj = decryptFromByteArray(buf);
		return obj;
	}

	/**
	 * Encrypt an object to byte array with DES encryption
	 *
	 * @param obj
	 *            the object to be encrypted
	 * @return encrypted byte array
	 * @throws Exception
	 */
	private static byte[] encryptToByteArray(Object obj) throws Exception {
		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		CipherOutputStream cos = new CipherOutputStream(bos, cipher);
		ObjectOutputStream oos = new ObjectOutputStream(cos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		byte[] buf = bos.toByteArray();
		return buf;
	}

	/**
	 * Decrypt the byte array to original object.
	 *
	 * @param buf
	 *            byte array.
	 * @return The original object.
	 * @throws Exception
	 */
	private static Object decryptFromByteArray(byte[] buf) throws Exception {
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
		ByteArrayInputStream bis = new ByteArrayInputStream(buf);
		CipherInputStream cis = new CipherInputStream(bis, cipher);
		ObjectInputStream ois = new ObjectInputStream(cis);
		Object obj = (Object) ois.readObject();
		ois.close();
		return obj;
	}

	/**
	 * Get the cipher with specified key and mode.
	 *
	 * @param mode
	 *            Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
	 * @return The cipher object.
	 * @throws Exception
	 */
	private static Cipher getCipher(int mode) throws Exception {
		byte keyArr[] = getSecureKey();
		DESKeySpec desKeySpec = new DESKeySpec(keyArr);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(mode, secretKey);
		return cipher;
	}

	public static byte[] getSecureKey() throws Exception {
		File secKeyFile = new File(SECURE_KEY_FILE_NAME);
		if (!secKeyFile.exists()) {
			SecureRandom secRandom = new SecureRandom();
			KeyGenerator keyGen = KeyGenerator.getInstance("DES");
			keyGen.init(secRandom);
			SecretKey secKey = keyGen.generateKey();
			byte[] secKeyArray = secKey.getEncoded();
			FileUtil.writeFile(secKeyFile, secKeyArray);
		}
		return FileUtil.readFile(secKeyFile);
	}

	public static void main(String[] args) {

		try {
			Map<String, String> map = new HashMap<String, String>();

			map.put("key1", "value1");
			map.put("key2", "value2");

			System.out.println(map);
			FileUtil.writeFile("testFile", encrypt(map));
			System.out
					.println(decrypt(new String(FileUtil.readFile("testFile"))));
			String testvalue = "sfadsfasdfas";
			System.out.println(testvalue);
			FileUtil.writeFile("testFile2", encrypt(testvalue));
			System.out.println(decrypt(new String(FileUtil
					.readFile("testFile2"))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
