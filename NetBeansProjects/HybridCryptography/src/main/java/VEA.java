
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.bouncycastle.jcajce.provider.symmetric.DES;

public class VEA {

    int rounds; // # OF ROUNDS TO CYCLE
    SecretKey key = KeyGenerator.getInstance("Blowfish").generateKey();
    Cipher ecipher, dcipher;

    VEA() throws Exception {
        ecipher = Cipher.getInstance("Blowfish");
        dcipher = Cipher.getInstance("Blowfish");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    public byte[] encrypt(byte[] str) throws Exception {
        byte[] encrypt = new byte[str.length];
        // Encrypt
        for (int i = 0; i < rounds; i++) {
            encrypt = ecipher.doFinal(str);
        }
        return encrypt;
    }

    public byte[] decrypt(byte[] str) throws Exception {
        // Decode base64 to get bytes
        byte[] decrypt = new byte[str.length];
        for (int i = 0; i < rounds; i++) {
            decrypt = dcipher.doFinal(str);
        }

        // Decode using utf-8
        return decrypt;
    }

    ArrayList<Byte> encrypt(ArrayList<Byte> fileContent2) {
        byte[] encrypt = new byte[fileContent2.size()];
        for (int i = 0; i < fileContent2.size(); i++) {
            encrypt[i] = fileContent2.get(i);
        }
        try {
            ecipher.doFinal(encrypt);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
        fileContent2.clear();
        for (int i = 0; i < encrypt.length; i++) {
            fileContent2.add(150, encrypt[i]);
        }
        return fileContent2;
    }
}
