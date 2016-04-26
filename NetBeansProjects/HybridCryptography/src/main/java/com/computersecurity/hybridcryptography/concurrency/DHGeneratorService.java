/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.concurrency;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import com.computersecurity.hybridcryptography.service.DHKeyAgreement2Service;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author sm6668
 */
public class DHGeneratorService extends Service<Object[]> {

    private DHKeyAgreement2 dhk;
    private final DHKeyAgreement2Service dhkService;

    public DHGeneratorService() {
        dhk = new DHKeyAgreement2();
        dhkService = new DHKeyAgreement2Service(dhk);
    }

    public DHKeyAgreement2 getDHKeyAgreement2() {
        return dhk;
    }

    public void setDHKeyAgreement2(DHKeyAgreement2 dhk) {
        dhkService.setDHKeyAgreement2(this.dhk = dhk);

    }

    @Override
    protected Task createTask() {
        return new Task<Object[]>() {

            @Override
            public Object[] call() throws InterruptedException {
                updateMessage("Generating Diffie-Hellman Parameters . . . . .");
                Thread.sleep(2000);

                Object[] items = {
                    dhkService.getSecretKeyA(),
                    dhkService.getSecretKeyB(),
                    dhkService.getBase(),
                    dhkService.getModulus(),
                    dhkService.getPublicKeyA(),
                    dhkService.getPrivateKeyA(),
                    dhkService.getPublicKeyB(),
                    dhkService.getPrivateKeyB()
                };
                return items;
            }

        };
    }

}
