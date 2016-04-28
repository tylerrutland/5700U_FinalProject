/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseCBC;
import java.io.File;

/**
 *
 * @author sm6668
 */
public class DESBaseCBCService {

    public DESBaseCBC cbc;

    public DESBaseCBCService(DESBaseCBC cbc) {
        this.cbc = cbc;
    }

    public boolean encryptImage(File imageFile, File outputFile) {
        return cbc.encryptImage(imageFile, outputFile, cbc.getDESKeyA());
    }

    public boolean decryptImage(File imageFile, File outputFile) {
        return cbc.decryptImage(imageFile, outputFile, cbc.getDESKeyB());
    }

}
