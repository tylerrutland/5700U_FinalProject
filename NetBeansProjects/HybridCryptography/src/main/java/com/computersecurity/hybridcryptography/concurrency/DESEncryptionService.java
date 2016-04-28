/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.concurrency;

import com.computersecurity.hybridcryptography.model.moduleDES.DESBase;
import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseCBC;
import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseECB;
import com.computersecurity.hybridcryptography.service.DESBaseCBCService;
import com.computersecurity.hybridcryptography.service.DESBaseECBService;
import java.io.File;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * This class is a "worker class" that uses a thread to perform complex tasks in
 * the background of the application. This class specifically performs a DES
 * encryption of the imageFile and outputs the encrypted file to a chosen
 * location
 */
public class DESEncryptionService extends Service<Boolean> {

    private DESBase desBase;
    private File imageFile, outputFile;

    public DESEncryptionService() {
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

    /*
     Creates a single task for the thread to execute in the background
     and calls a Boolean value "on succeeded" when the task is completed
     */
    @Override
    protected Task createTask() {
        return new Task<Boolean>() {

            @Override
            public Boolean call() throws InterruptedException {
                updateMessage("Encrypting Image . . . . .");
                Thread.sleep(2000);
                if (desBase instanceof DESBaseECB) {
                    updateMessage("Encryption Successful");
                    Thread.sleep(1500);
                    return new DESBaseECBService((DESBaseECB) desBase)
                            .encryptImage(imageFile, outputFile);
                } else {
                    updateMessage("Encryption Successful");
                    Thread.sleep(1500);
                    return new DESBaseCBCService((DESBaseCBC) desBase)
                            .encryptImage(imageFile, outputFile);
                }
            }
        };
    }

}
