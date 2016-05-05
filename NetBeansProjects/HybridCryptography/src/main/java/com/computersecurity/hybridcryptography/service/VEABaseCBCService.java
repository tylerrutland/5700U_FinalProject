/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.moduleVEA.VEABaseCBC;
import java.io.File;

/**
 *
 * @author sm6668
 */
public class VEABaseCBCService {

    private final VEABaseCBC cbc;

    public VEABaseCBCService(VEABaseCBC cbc) {
        this.cbc = cbc;
    }

    public boolean encryptImageFile(File imageFile, File outputFile) {
        return cbc.encryptImageFile(imageFile, outputFile, cbc.getVEAKey());
    }

    public boolean decryptImageFile(File imageFile, File outputFile) {
        return cbc.decryptImageFile(imageFile, outputFile, cbc.getVEAKey());
    }

}
