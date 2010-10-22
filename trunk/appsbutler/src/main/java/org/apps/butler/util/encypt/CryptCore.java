package org.apps.butler.util.encypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class CryptCore {

	private final static String ALGORITHM_KEY = "RSA";

	public KeyPair createPairKey(int keySize) {
		try {
			KeyPairGenerator keygen = KeyPairGenerator
					.getInstance(ALGORITHM_KEY);
			SecureRandom random = new SecureRandom();
			random.setSeed(10000);
			keygen.initialize(keySize, random);// keygen.initialize(512);
			KeyPair keyPair = keygen.generateKeyPair();
			PublicKey pubkey = keyPair.getPublic();
			PrivateKey prikey = keyPair.getPrivate();
			System.out.println(pubkey);
			System.out.println(prikey);
			return keyPair;
			// doObjToFile("mykeys.bat", new Object[] { prikey, pubkey });
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 加密
	 *
	 * @param key
	 *            加密的密钥
	 * @param data
	 *            待加密的明文数据
	 * @return 加密后的数据
	 * @throws Exception
	 */
	public byte[] encrypt(Key key, byte[] data) throws Exception {
		try {
			KeyFactory mykeyFactory = KeyFactory.getInstance(ALGORITHM_KEY);
			X509EncodedKeySpec pub_spec = new X509EncodedKeySpec(
					key.getEncoded());
			PublicKey pubKey = mykeyFactory.generatePublic(pub_spec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 解密
	 *
	 * @param key
	 *            解密的密钥
	 * @param raw
	 *            已经加密的数据
	 * @return 解密后的明文
	 * @throws Exception
	 */
	public byte[] decrypt(Key key, byte[] raw) throws Exception {
		try {
			PKCS8EncodedKeySpec priv_spec = new PKCS8EncodedKeySpec(
					key.getEncoded());
			KeyFactory mykeyFactory = KeyFactory.getInstance(ALGORITHM_KEY);
			PrivateKey privKey = mykeyFactory.generatePrivate(priv_spec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			return cipher.doFinal(raw);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			CryptCore core = new CryptCore();
			KeyPair keyPair = core.createPairKey(512);
			byte[] secString = core.encrypt(keyPair.getPublic(),
					"testabc".getBytes());
			System.out.println("Encoded string: " + new String(secString));
			secString = core.decrypt(keyPair.getPrivate(), secString);
			System.out.println("Decoded string: " + new String(secString));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
