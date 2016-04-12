import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DES {

    SecretKey key = KeyGenerator.getInstance("DES").generateKey();
    Cipher ecipher, dcipher;

    DES() throws Exception {
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    public byte[] encrypt(byte[] str) throws Exception {
        // Encode the string into bytes using utf-8
        //byte[] utf8 = str.getBytes("UTF8");

        // Encrypt
        byte[] encrypt = ecipher.doFinal(str);
        // Encode bytes to base64 to get a string
        return encrypt;
    }

    public byte[] decrypt(byte[] str) throws Exception {
        // Decode base64 to get bytes
        byte[] decrypt = dcipher.doFinal(str);

        // Decode using utf-8
        return decrypt;
    }
}
