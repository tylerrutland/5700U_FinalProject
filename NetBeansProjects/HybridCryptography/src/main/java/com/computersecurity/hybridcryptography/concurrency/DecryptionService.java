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
import java.io.File;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author sm6668
 */
public class DecryptionService extends Service<Boolean> {

    private DESBase desBase;
    private File imageFile, outputFile;

    public DecryptionService() {
    }

    public void setDESBase(DESBase desBase) {
        this.desBase = desBase;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    protected Task createTask() {
        return new Task<Boolean>() {

            @Override
            public Boolean call() throws InterruptedException {
                updateMessage("Decrypting Image . . . . .");
                Thread.sleep(2000);
                if (desBase instanceof DESBaseECB) {
                    Thread.sleep(1500);
                    updateMessage("Encryption Successful");
                    return new DESBaseECBService((DESBaseECB) desBase)
                            .decryptImage(imageFile, outputFile);

                } else {
                    Thread.sleep(1500);
                    updateMessage("Encryption Successful");
                    return new DESBaseCBCService((DESBaseCBC) desBase)
                            .decryptImage(imageFile, outputFile);
                }
            }
        };
    }

}
