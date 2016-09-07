package at.fhhgb.caas.crypto;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class DataCrypto {

	private static final char[] PASSWORD = "dskdskglldklgmkrkskdl".toCharArray();
    private static final byte[] SALT = {
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };
    private static final CharSequence SECRETE = "PBEWithMD5AndDES";
    private static final String ENCODING = "UTF-8";
    private static final int iterationCount = 20;
    
	public static String encrypt(String plainText) {
        String encrypt = plainText;
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRETE.toString());
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance(SECRETE.toString());
	        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, iterationCount));
	        encrypt = base64Encode(pbeCipher.doFinal(plainText.getBytes(ENCODING)));
        } catch (Exception e) {
	    	System.err.println(e.getMessage());
        }
        return encrypt;
    }

    public static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    public static String decrypt(String encryptedText) {
        String decrypt = encryptedText;
    	try {
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SECRETE.toString());
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance(SECRETE.toString());
	        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, iterationCount));
	        decrypt = new String(pbeCipher.doFinal(base64Decode(encryptedText)), ENCODING);
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }
        return decrypt;
    }

    public static byte[] base64Decode(String property) throws IOException {
        return new BASE64Decoder().decodeBuffer(property);
    }
}
