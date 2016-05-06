/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseECB;
import java.io.File;

/**
 * This class services the specified DESBaseECB object with the desired methods
 * for the presentation layer of the application
 */
public class DESBaseECBService {

    private final DESBaseECB ecb;

    public DESBaseECBService(DESBaseECB ecb) {
        this.ecb = ecb;
    }

    public boolean encryptImageFile(File imageFile, File outputFile) {
        return ecb.encryptImageFile(imageFile, outputFile, ecb.getDESKey());
    }

    public boolean decryptImageFile(File imageFile, File outputFile) {
        return ecb.decryptImageFile(imageFile, outputFile, ecb.getDESKey());
    }
}
