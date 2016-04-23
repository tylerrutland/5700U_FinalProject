/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.controller;

import com.computersecurity.hybridcryptography.concurrency.AppExecutorService;
import com.computersecurity.hybridcryptography.concurrency.DHGeneratorService;
import com.computersecurity.hybridcryptography.model.DESBaseCBC;
import com.computersecurity.hybridcryptography.model.DESBaseECB;
import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

/**
 *
 * @author Steve
 */
public class DHController implements Initializable {

    private final DESBaseECB ecb = new DESBaseECB();
    private final DESBaseCBC cbc = new DESBaseCBC();
    private final ToggleGroup bitLengthGroup = new ToggleGroup();
    private final ToggleGroup modeGroup = new ToggleGroup();
    private final FileChooser fileChooser = new FileChooser();
    private final DHGeneratorService dhGeneratorService = new DHGeneratorService();

    @FXML
    private SplitPane splitPane;

    @FXML
    private BorderPane cipherPane;

    @FXML
    private ImageView initImageView, origImageView, encImageView;

    @FXML
    private RadioButton rbECB, rbCBC;

    @FXML
    private RadioButton rb512, rb1024;

    @FXML
    private TextField baseTF, modTF;

    @FXML
    private TextField pubKeyATF, pubKeyBTF;

    @FXML
    private PasswordField privKeyAPF, privKeyBPF;

    @FXML
    private PasswordField secKeyAPF, secKeyBPF;

    @FXML
    private Button openBtn, genBtn;

    @FXML
    private Button encryptBtn, decryptBtn;

    @FXML
    private Label messageLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dhGeneratorService.setExecutor(AppExecutorService.pool);
        dhGeneratorService.setOnSucceeded(e -> succeeded());

        rbECB.setUserData(ecb);
        rbECB.setSelected(true);
        rbECB.setToggleGroup(modeGroup);

        rbCBC.setUserData(cbc);
        rbCBC.setToggleGroup(modeGroup);

        rb512.setUserData(512);
        rb512.setSelected(true);
        rb512.setToggleGroup(bitLengthGroup);

        rb1024.setUserData(1024);
        rb1024.setToggleGroup(bitLengthGroup);

        splitPane.disableProperty().bind(dhGeneratorService.runningProperty());
        cipherPane.disableProperty().bind(dhGeneratorService.runningProperty());
        openBtn.disableProperty().bind(dhGeneratorService.runningProperty());
        genBtn.disableProperty().bind(dhGeneratorService.runningProperty());
        messageLabel.textProperty().bind(dhGeneratorService.messageProperty());
        messageLabel.visibleProperty().bind(dhGeneratorService.runningProperty());
        progressIndicator.progressProperty().bind(dhGeneratorService.progressProperty());

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp"));
    }

    @FXML
    private void openImage(ActionEvent event) {
        File imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null) {
            Image img = new Image("File:" + imageFile.getAbsolutePath());
            initImageView.setImage(img);
            origImageView.setImage(img);
        }

        event.consume();
    }

    @FXML
    private void generateParameters(ActionEvent event) {
        int bitLength = (Integer) bitLengthGroup.getSelectedToggle().getUserData();
        dhGeneratorService.setDHKeyAgreement2(new DHKeyAgreement2(bitLength));
        dhGeneratorService.start();
        event.consume();
    }

    @FXML
    private void encryptImage(ActionEvent event) {

        event.consume();
    }

    @FXML
    private void decryptImage(ActionEvent event) {

        event.consume();
    }

    private void succeeded() {
        Object[] items = dhGeneratorService.getValue();

        if (items.length != 0) {

            secKeyAPF.setText((String) items[0]);
            secKeyBPF.setText((String) items[1]);
            baseTF.setText(((BigInteger) items[2]).toString());
            modTF.setText(((BigInteger) items[3]).toString());

            /*
             *Shows the DH Parameter Specifications
             */
//            System.out.println("PublicKey A: " + items[4]);
//            System.out.println("PrivateKey A: " + items[5]);
//            System.out.println("PublicKey B: " + items[6]);
//            System.out.println("PrivateKey B: " + items[7]);
            pubKeyATF.setText(Arrays.toString(((PublicKey) items[4]).getEncoded()));
            privKeyAPF.setText(Arrays.toString(((PrivateKey) items[5]).getEncoded()));
            pubKeyBTF.setText(Arrays.toString(((PublicKey) items[6]).getEncoded()));
            privKeyBPF.setText(Arrays.toString(((PrivateKey) items[7]).getEncoded()));

            dhGeneratorService.reset();

        } else {

            dhGeneratorService.reset();

        }
    }

}
