package ar.com.tandilweb.byo.backend.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class CryptDES {
	private Cipher ecipher;
	private Cipher dcipher;

	CryptDES(SecretKey key) throws Exception {
		ecipher = Cipher.getInstance("DES");
		dcipher = Cipher.getInstance("DES");
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		dcipher.init(Cipher.DECRYPT_MODE, key);
	}

	@SuppressWarnings("restriction")
	public String encrypt(String str) throws Exception {
		byte[] utf8 = str.getBytes("UTF8");
		byte[] enc = ecipher.doFinal(utf8);
		return new sun.misc.BASE64Encoder().encode(enc);
	}

	@SuppressWarnings("restriction")
	public String decrypt(String str) throws Exception {
		byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
		byte[] utf8 = dcipher.doFinal(dec);
		return new String(utf8, "UTF8");
	}
}
