/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.concurrency;

import com.computersecurity.hybridcryptography.model.DESBase;
import com.computersecurity.hybridcryptography.model.DESBaseCBC;
import com.computersecurity.hybridcryptography.model.DESBaseECB;
import com.computersecurity.hybridcryptography.service.DESBaseCBCService;
import com.computersecurity.hybridcryptography.service.DESBaseECBService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Steve
 */
public class EncryptionService extends Service<Boolean> {

    private DESBase desBase;
    private DESBaseECBService ecbService;
    private DESBaseCBCService cbcService;

    public EncryptionService() {
    }

    public void setDesBase(DESBase desBase) {
        this.desBase = desBase;
    }

//    public void setImageFile(File imageFile) {
//
//    }
//
//    public void setOutputFile(File outputFile) {
//
//    }

    @Override
    protected Task createTask() {
        return new Task<Boolean>() {

            @Override
            public Boolean call() throws InterruptedException {
                updateMessage("Encrypting Image . . . . .");
                Thread.sleep(2000);
                if (desBase instanceof DESBaseECB) {
                    ecbService = new DESBaseECBService((DESBaseECB) desBase);
                    return ecbService.encryptImage(null, null);
                } else {
                    cbcService = new DESBaseCBCService((DESBaseCBC) desBase);
                    return cbcService.encryptImage(null, null);
                }
            }
        };
    }

}
