/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

/**
 *
 * @author sm6668
 */
public class CryptoUtils {

    public static Image getImage(File imageFile) {
        try {
            return new Image(new FileInputStream(imageFile));
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

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
        
        for (int i = 0; i < 5; i++) {//Rounds later
            fos.write(cipher.doFinal());
        }
        
        fos.close();

    }

    public static boolean encryptImage(File imageFile, File outputFile) {
        try {
//            //The imageFile's path is going to be used as the plaintext for random seed
//            byte[] imageFileBytePath = Files.readAllBytes(imageFile.toPath());
//
//            //Use the cipherInputFileBytePath as a seed for reproducible results for decryption
//            SecureRandom sr = new SecureRandom("SHA1PRNG");
//            sr.setSeed(300);

            BufferedImage bufImg = ImageIO.read(new FileImageInputStream(imageFile));

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
            //Save to a bmp file
            return ImageIO.write(bufImg, "bmp", new FileImageOutputStream(outputFile));

        } catch (Exception ex) {
            return false;
        }
    }
}
