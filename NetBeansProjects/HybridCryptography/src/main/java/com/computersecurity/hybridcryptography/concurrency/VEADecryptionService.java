/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.concurrency;

import com.computersecurity.hybridcryptography.model.moduleVEA.VEABase;
import com.computersecurity.hybridcryptography.model.moduleVEA.VEABaseCBC;
import com.computersecurity.hybridcryptography.model.moduleVEA.VEABaseECB;
import com.computersecurity.hybridcryptography.service.VEABaseCBCService;
import com.computersecurity.hybridcryptography.service.VEABaseECBService;
import java.io.File;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author sm6668
 */
public class VEADecryptionService extends Service<Boolean> {

    private int rounds;
    private VEABase veaBase;
    private File imageFile, outputFile;

    public VEADecryptionService() {
    }

    public void setVEABase(VEABase veaBase) {
        this.veaBase = veaBase;
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

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
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
                updateMessage("Encrypting Image VEA. . . . .");
                Thread.sleep(2000);
                if (veaBase instanceof VEABaseECB) {
                    updateMessage("Encryption Successful");
                    Thread.sleep(1500);

                    VEABaseECB ecb = (VEABaseECB) veaBase;
                    ecb.setRounds(rounds);

                    return new VEABaseECBService(ecb)
                            .derenderImage(imageFile, outputFile);
                } else {
                    updateMessage("Encryption Successful");
                    Thread.sleep(1500);

                    VEABaseCBC cbc = (VEABaseCBC) veaBase;
                    cbc.setRounds(rounds);

                    return new VEABaseCBCService(cbc)
                            .derenderImage(imageFile, outputFile);
                }
            }
        };
    }
}
