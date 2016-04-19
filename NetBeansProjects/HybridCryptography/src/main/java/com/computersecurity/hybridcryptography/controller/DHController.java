/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.controller;

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

    private final ToggleGroup group = new ToggleGroup();

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
        rb512.setToggleGroup(group);
        rb1024.setToggleGroup(group);

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
