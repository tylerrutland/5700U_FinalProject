/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;

/**
 * FXML Controller class
 *
 * @author sm6668
 */
public class MainViewController implements Initializable {

    private final ArrayList<Node> pages = new ArrayList(3);

    @FXML
    private Pagination pagination;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addPage("fxml/HybridCrypto.fxml");
        pagination.setCurrentPageIndex(0);
        pagination.setPageCount(1);
        pagination.setPageFactory((Integer pageIndex) -> pages.get(pageIndex));
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
    }

    private void addPage(String resource) {
        try {
            pages.add((new FXMLLoader(
                    MainViewController.class.getClassLoader()
                    .getResource(resource)).load()));

        } catch (Exception ex) {

            System.out.println(ex);
            pages.add(null);

        }
    }
}
