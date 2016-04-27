/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import static com.computersecurity.hybridcryptography.util.CryptoUtils.write;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author sm6668
 */
public class DESBaseECB extends DESBase implements Cryptable {

    private static final String ALGORITHM = "DES/ECB/PKCS5Padding";
    private Cipher cipher;

    public DESBaseECB() {
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }
    }

    @Override
    public boolean encryptImage(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            return false;
        }
    }

    @Override
    public boolean decryptImage(File imageFile, File outputFile, SecretKey key) {

        try {

            cipher.init(Cipher.DECRYPT_MODE, key);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            return false;
        }

    }

    @Override
    public byte[] getCipherText(byte[] plaintext, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plaintext);

        } catch (InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;
        }
    }

    @Override
    public byte[] getPlainText(byte[] ciphertext, SecretKey key) {
        try {

            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(ciphertext);

        } catch (InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;

        }
    }

//    public boolean encryptImage(File imageFile, File outputFile, SecretKey key) {
//        try {
//            //The imageFile's path is going to be used as the plaintext for random seed
//            byte[] imageFileBytePath = Files.readAllBytes(imageFile.toPath());
//
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            byte[] cipherInputFileBytePath = cipher.doFinal(imageFileBytePath);
//
//            //Use the cipherInputFileBytePath as a seed for reproducible results for decryption
//            sr.setSeed(cipherInputFileBytePath);
//
//            BufferedImage bufImg = ImageIO.read(new FileImageInputStream(imageFile));
//
//            //Randomize the pixels of the image file
//            for (int w = 0; w < bufImg.getWidth(); w++) {
//                for (int h = 0; h < bufImg.getHeight(); h++) {
//                    Color color = new Color(bufImg.getRGB(w, h));
//
//                    int randRed = color.getRed() ^ sr.nextInt(255);
//                    int randGreen = color.getGreen() ^ sr.nextInt(255);
//                    int randBlue = color.getBlue() ^ sr.nextInt(255);
//
//                    //Set random pixel color at location (w, h)
//                    bufImg.setRGB(w, h, (new Color(randRed, randBlue, randGreen)).getRGB());
//                }
//
//            }
//
//            //Save to a bmp file
//            return ImageIO.write(bufImg, "bmp", new FileImageOutputStream(outputFile));
//
//        } catch (IOException |
//                InvalidKeyException |
//                IllegalBlockSizeException |
//                BadPaddingException ex) {
//            return false;
//        }
//    }
}
