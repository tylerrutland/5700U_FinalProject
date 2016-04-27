/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 *
 * @author sm6668
 */
public class CryptoUtils {

    public static void write(Cipher cipher, File imageFile, File outputFile)
            throws IOException, IllegalBlockSizeException, BadPaddingException {

        FileOutputStream fos;
        FileInputStream fis = new FileInputStream(imageFile);
        fos = new FileOutputStream(outputFile);
        byte[] buffer = new byte[4096];
        int len;
        while ((len = fis.read(buffer)) > 0) {
            fos.write(cipher.update(buffer, 0, len));
            fos.flush();
        }
        fos.write(cipher.doFinal());
        fos.close();

    }

}
