/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author sm6668
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    MainApplication.class.getClassLoader()
                    .getResource("fxml/MainView.fxml"));

            Parent parent = (Parent) loader.load();

            primaryStage.setScene(new Scene(parent));
            primaryStage.show();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
