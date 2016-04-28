/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.controller;

import com.computersecurity.hybridcryptography.concurrency.AppExecutorService;
import com.computersecurity.hybridcryptography.concurrency.DHGeneratorService;
import com.computersecurity.hybridcryptography.concurrency.DESDecryptionService;
import com.computersecurity.hybridcryptography.concurrency.DESEncryptionService;
import com.computersecurity.hybridcryptography.model.moduleDES.DESBase;
import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseCBC;
import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseECB;
import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import static com.computersecurity.hybridcryptography.util.CryptoUtils.getImage;
import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Steve
 */
public class DESController implements Initializable {

    //Service Classes
    private final DESEncryptionService encService = new DESEncryptionService();
    private final DESDecryptionService decService = new DESDecryptionService();
    private final DHGeneratorService dhGenService = new DHGeneratorService();

    private final ToggleGroup bitLengthGroup = new ToggleGroup();
    private final ToggleGroup modeGroup = new ToggleGroup();
    private final FileChooser fileChooser = new FileChooser();

    private File imageFile;

    @FXML
    private BorderPane imagePane, encDecPane;

    @FXML
    private ImageView origImageView;

    @FXML
    private ImageView encImageView, decImageView;

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
    private Tooltip baseTooltip, modTooltip;

    @FXML
    private Tooltip secretKeyATooltip;

    @FXML
    private Label messageLabel;

    @FXML
    private Label encMessageLabel, decMessageLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private ProgressBar encProgressBar, decProgressBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setExecutor(AppExecutorService.pool);
        encService.setOnSucceeded(e -> encSucceeded());
        decService.setOnSucceeded(e -> decSucceeded());
        dhGenService.setOnSucceeded(e -> dhSucceeded());

        rbECB.setUserData(new DESBaseECB());
        rbECB.setSelected(true);
        rbECB.setToggleGroup(modeGroup);

        rbCBC.setUserData(new DESBaseCBC());
        rbCBC.setToggleGroup(modeGroup);

        rb512.setUserData(512);
        rb512.setSelected(true);
        rb512.setToggleGroup(bitLengthGroup);

        rb1024.setUserData(1024);
        rb1024.setToggleGroup(bitLengthGroup);

        imagePane.disableProperty().bind(dhGenService.runningProperty());
        encDecPane.disableProperty().bind(dhGenService.runningProperty());
        openBtn.disableProperty().bind(dhGenService.runningProperty());
        genBtn.disableProperty().bind(dhGenService.runningProperty());
        messageLabel.textProperty().bind(dhGenService.messageProperty());
        messageLabel.visibleProperty().bind(dhGenService.runningProperty());
        progressIndicator.progressProperty().bind(dhGenService.progressProperty());

        encMessageLabel.textProperty().bind(encService.messageProperty());
        encProgressBar.visibleProperty().bind(encService.runningProperty());
        encProgressBar.progressProperty().bind(encService.progressProperty());

        decMessageLabel.textProperty().bind(decService.messageProperty());
        decProgressBar.visibleProperty().bind(decService.runningProperty());
        decProgressBar.progressProperty().bind(decService.progressProperty());

        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("BMP files (*.bmp)", "*.bmp"),
                new ExtensionFilter("JPEG files (*.jpg)", "*.jpg"),
                new ExtensionFilter("PNG files (*.png)", "*.png"));
    }

    @FXML
    private void openImage(ActionEvent event) {
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null) {
            origImageView.setImage(getImage(imageFile));
        } else {

            //User closes file chooser
        }

        event.consume();
    }

    @FXML
    private void generateParameters(ActionEvent event) {
        if (imageFile != null) {
            int bitLength = (Integer) bitLengthGroup.getSelectedToggle().getUserData();
            dhGenService.setDHKeyAgreement2(new DHKeyAgreement2(bitLength));
            dhGenService.start();
            event.consume();
        } else {

            //User needs to choose an image first
        }
    }

    @FXML
    private void encryptImage(ActionEvent event) {
        if (imageFile != null) {
            DESBase desBase = (DESBase) modeGroup.getSelectedToggle().getUserData();
            File outputFile = fileChooser.showSaveDialog(null);
            if (outputFile != null) {
                encService.setDESBase(desBase);
                encService.setImageFile(imageFile);
                encService.setOutputFile(outputFile);
                encService.start();
            }
        } else {

            //User hasn't opened image
        }
        event.consume();
    }

    @FXML
    private void decryptImage(ActionEvent event) {
        if (imageFile != null && encService.getOutputFile().exists()) {
            DESBase desBase = (DESBase) modeGroup.getSelectedToggle().getUserData();
            File outputFile = fileChooser.showSaveDialog(null);
            if (outputFile != null) {
                decService.setDESBase(desBase);
                decService.setImageFile(encService.getOutputFile());
                decService.setOutputFile(outputFile);
                decService.start();
            }
        } else {

            //User hasn't opened image or encrypted the opened image
        }
        event.consume();
    }

    private void setExecutor(Executor exe) {
        encService.setExecutor(exe);
        decService.setExecutor(exe);
        dhGenService.setExecutor(exe);
    }

    private void dhSucceeded() {
        Object[] items = dhGenService.getValue();

        if (items.length != 0) {
            String secKeyA = (String) items[0];
            String secKeyB = (String) items[1];

            secKeyAPF.setText(secKeyA);
            secKeyBPF.setText(secKeyB);
            secretKeyATooltip.setText("Secret Keys Are Equal: " + secKeyA.contains(secKeyB));

            String base = ((BigInteger) items[2]).toString();
            String mod = ((BigInteger) items[3]).toString();
            String pubKeyA = Arrays.toString(((PublicKey) items[4]).getEncoded());
            String pubKeyB = Arrays.toString(((PublicKey) items[6]).getEncoded());

            if (rb1024.isSelected()) {
                baseTF.setText(base);
                baseTooltip.setText(
                        base.substring(0, 150) + "\n"
                        + base.substring(150) + "\n"
                        + "Base Length: " + base.length());
                modTF.setText(mod);
                modTooltip.setText(
                        mod.substring(0, 150) + "\n"
                        + mod.substring(150) + "\n"
                        + "Modulus Length: " + mod.length());
            } else {
                baseTF.setText(base);
                baseTooltip.setText(base + "\nBase Length: " + base.length());
                modTF.setText(mod);
                modTooltip.setText(mod + "\nModulus Length: " + mod.length());
            }

            pubKeyATF.setText(pubKeyA);
            privKeyAPF.setText(Arrays.toString(((PrivateKey) items[5]).getEncoded()));
            pubKeyBTF.setText(pubKeyB);
            privKeyBPF.setText(Arrays.toString(((PrivateKey) items[7]).getEncoded()));

            dhGenService.reset();

        } else {

            dhGenService.reset();

        }
    }

    private void encSucceeded() {
        /*
         Currently no way to visualize an encrypted image file
         Image View is blank
         */
        encImageView.setImage(getImage(encService.getOutputFile()));
        encService.reset();
    }

    private void decSucceeded() {
        decImageView.setImage(getImage(decService.getOutputFile()));
        decService.reset();
    }

}
