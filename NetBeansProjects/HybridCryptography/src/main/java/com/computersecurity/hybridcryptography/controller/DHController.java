/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.controller;

import com.computersecurity.hybridcryptography.model.DESBaseCBC;
import com.computersecurity.hybridcryptography.model.DESBaseECB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

/**
 *
 * @author Steve
 */
public class DHController implements Initializable {

    private final ToggleGroup keyGroup = new ToggleGroup();
    private final ToggleGroup cipherGroup = new ToggleGroup();

    private final DESBaseECB desBaseECB = new DESBaseECB();
    private final DESBaseCBC desBaseCBC = new DESBaseCBC();

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
    private ImageView imageView;

    @FXML
    private Button openBtn, genBtn;

    @FXML
    private ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbECB.setToggleGroup(cipherGroup);
        rbCBC.setToggleGroup(cipherGroup);

        rb512.setToggleGroup(keyGroup);
        rb1024.setToggleGroup(keyGroup);

    }

    @FXML
    private void openImage(ActionEvent event) {

        event.consume();
    }

    @FXML
    private void generateParameters(ActionEvent event) {

        event.consume();
    }
}
