/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseECB;
import java.io.File;

/**
 *
 * @author sm6668
 */
public class DESBaseECBService {

    public DESBaseECB ecb;

    public DESBaseECBService(DESBaseECB ecb) {
        this.ecb = ecb;
    }

    public boolean encryptImage(File imageFile, File outputFile) {
        return ecb.encryptImage(imageFile, outputFile, ecb.getDESKeyA());
    }

    public boolean decryptImage(File imageFile, File outputFile) {
        return ecb.decryptImage(imageFile, outputFile, ecb.getDESKeyB());
    }
}
