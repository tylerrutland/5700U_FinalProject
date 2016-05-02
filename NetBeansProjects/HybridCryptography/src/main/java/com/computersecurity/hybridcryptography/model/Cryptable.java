/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.io.File;
import javax.crypto.SecretKey;

/**
 *
 * @author sm6668
 */
public interface Cryptable {

    public boolean encryptImageFile(File imageFile, File outputFile, SecretKey key);

    public boolean decryptImageFile(File imageFile, File outputFile, SecretKey key);

    public byte[] getCipherText(byte[] plaintext, SecretKey key);

    public byte[] getPlainText(byte[] ciphertext, SecretKey key);
}
