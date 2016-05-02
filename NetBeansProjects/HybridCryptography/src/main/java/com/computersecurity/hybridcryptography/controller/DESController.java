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
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class controls the model(s) and view of its associated fxml file
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
    private ImageView decImageView;

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
    private Button openImageBtn, dhGenBtn;

    @FXML
    private Tooltip baseTooltip, modTooltip;

    @FXML
    private Tooltip dhGenBtnTooltip;

    @FXML
    private Label messageLabel;

    @FXML
    private Label encMessageLabel, decMessageLabel;

    @FXML
    private Label encModeLabel, decModeLabel;

    @FXML
    private Label invalidLabel;

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

        encModeLabel.setText("ECB");
        decModeLabel.setText("ECB");

        dhGenBtnTooltip.setText(getText());

        modeGroup.selectedToggleProperty().addListener(modeToggleListener());

        //UI Bindings
        imagePane.disableProperty().bind(dhGenService.runningProperty());
        encDecPane.disableProperty().bind(dhGenService.runningProperty());
        openImageBtn.disableProperty().bind(dhGenService.runningProperty());
        dhGenBtn.disableProperty().bind(dhGenService.runningProperty());
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
            decImageView.setImage(null);
            invalidLabel.setVisible(false);
        } else {

            //User closes file chooser
        }

        event.consume();
    }

    /*
     Generates all the Diffie-Hellman parameters: key pair, Diffie-Hellman
     parameter specifications (modulus, base, and choosen bit length value), and 
     shared secret key for both parties
     */
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

    /*
     Encrypts an image using the DES Algorithm
     */
    @FXML
    private void encryptImageFile(ActionEvent event) {
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

    /*
     Decrypts an image using the DES Algorithm from the encrypted 
     image file
     */
    @FXML
    private void decryptImageFile(ActionEvent event) {
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

    /*
     Sets the worker objects to utilize a single thread
     */
    private void setExecutor(Executor exe) {
        encService.setExecutor(exe);
        decService.setExecutor(exe);
        dhGenService.setExecutor(exe);
    }

    /*
     A method that is called after the task(s) is successfully completed
     */
    private void dhSucceeded() {
        Object[] items = dhGenService.getValue();

        if (items.length != 0) {
            String secKeyA = (String) items[0];
            String secKeyB = (String) items[1];

            secKeyAPF.setText(secKeyA);
            secKeyBPF.setText(secKeyB);

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

    /*
     A method that is called after the task(s) is successfully completed
     */
    private void encSucceeded() {
        /*
         Currently no way to visualize an encrypted image file
         */
        invalidLabel.setVisible(true);
        encService.reset();
    }

    /*
     A method that is called after the task(s) is successfully completed
     */
    private void decSucceeded() {
        decImageView.setImage(getImage(decService.getOutputFile()));
        decService.reset();
    }

    /*
     A listener for showing the encryptiom modes to the user
     */
    private ChangeListener<Toggle> modeToggleListener() {
        return (observ, oldVal, newVal) -> {
            if (newVal != null) {
                DESBase desBase = (DESBase) newVal.getUserData();
                if (desBase instanceof DESBaseECB) {
                    encModeLabel.setText("ECB");
                    decModeLabel.setText("ECB");
                } else {
                    encModeLabel.setText("CBC");
                    decModeLabel.setText("CBC");
                }
            }
        };
    }

    /*
     Tooltip information 
     */
    private String getText() {
        return "The secret key generated from the Diffie-Hellman Algorithm will be used to encrypt\n"
                + "and decrypt the image file. If no parameters are generated, then there will be a\n"
                + "secret key generated in the background to encrypt and decrypt the image";
    }
}
