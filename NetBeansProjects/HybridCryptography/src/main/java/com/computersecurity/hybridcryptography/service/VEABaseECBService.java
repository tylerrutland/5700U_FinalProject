/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.moduleVEA.VEABaseECB;
import java.io.File;

/**
 *
 * @author sm6668
 */
public class VEABaseECBService {

    private final VEABaseECB ecb;

    public VEABaseECBService(VEABaseECB ecb) {
        this.ecb = ecb;
    }

    public boolean renderImage(File imageFile, File outputFile) {
        return ecb.encryptImageFile(imageFile, outputFile, ecb.getVEAKey());
    }

    public boolean derenderImage(File imageFile, File outputFile) {
        return ecb.decryptImageFile(imageFile, outputFile, ecb.getVEAKey());
    }

    public boolean encryptImageFile(File imageFile, File outputFile) {
        return ecb.encryptImageFile(imageFile, outputFile, ecb.getVEAKey());
    }

    public boolean decryptImageFile(File imageFile, File outputFile) {
        return ecb.decryptImageFile(imageFile, outputFile, ecb.getVEAKey());
    }

}
