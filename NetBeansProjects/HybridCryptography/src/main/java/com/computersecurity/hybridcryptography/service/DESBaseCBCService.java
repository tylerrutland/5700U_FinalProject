/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseCBC;
import java.io.File;

/**
 * This class services the specified DESBaseCBC object with the desired methods
 * for the presentation layer of the application
 */
public class DESBaseCBCService {

    public DESBaseCBC cbc;

    public DESBaseCBCService(DESBaseCBC cbc) {
        this.cbc = cbc;
    }

    public boolean encryptImageFile(File imageFile, File outputFile) {
        return cbc.encryptImageFile(imageFile, outputFile, cbc.getDESKeyA());
    }

    public boolean decryptImageFile(File imageFile, File outputFile) {
        return cbc.decryptImageFile(imageFile, outputFile, cbc.getDESKeyB());
    }

}
