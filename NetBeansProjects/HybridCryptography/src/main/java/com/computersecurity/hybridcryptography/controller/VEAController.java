/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.controller;

import com.computersecurity.hybridcryptography.model.moduleVEA.Polynomial;
import static com.computersecurity.hybridcryptography.util.CryptoUtils.getImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author sm6668
 */
public class VEAController implements Initializable {

    private int count;
    private int submittedUsers;

    private Polynomial groupPolynomial = new Polynomial(0, 0);
    private final ArrayList<Polynomial> monomials = new ArrayList(4);

    private final SimpleDoubleProperty widthProperty = new SimpleDoubleProperty();
    private final SimpleDoubleProperty heightProperty = new SimpleDoubleProperty();

    private final SimpleIntegerProperty countProperty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty degreeProperty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty maxDegreeProperty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty submittedUsersProperty = new SimpleIntegerProperty();
    private final SimpleBooleanProperty areUsersSetProperty = new SimpleBooleanProperty();
    private final SimpleBooleanProperty isAllSubmittedProperty = new SimpleBooleanProperty();

    private final ToggleGroup userGroup = new ToggleGroup();
    private final FileChooser fileChooser = new FileChooser();

    private File imageFile;

    @FXML
    private RadioButton rb2Users, rb3Users;

    @FXML
    private Button setUsersBtn, resetBtn;

    @FXML
    private ImageView origImageView;

    @FXML
    private Button openImageBtn, addUserCoordBtn, genPolyBtn;

    @FXML
    private Label widthLabel, heightLabel;

    @FXML
    private TextArea coordTextArea;

    @FXML
    private Label messageLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rb2Users.setUserData(2);
        rb2Users.setSelected(true);
        rb2Users.setToggleGroup(userGroup);

        rb3Users.setUserData(3);
        rb3Users.setToggleGroup(userGroup);

        countProperty.set(count = 0);
        maxDegreeProperty.set(0);
        submittedUsersProperty.set(submittedUsers = 0);

        //Field Bindings
        degreeProperty.bind(maxDegreeProperty.subtract(countProperty));
        areUsersSetProperty.bind(submittedUsersProperty.greaterThanOrEqualTo(0).and(
                submittedUsersProperty.lessThan(maxDegreeProperty)));
        isAllSubmittedProperty.bind(submittedUsersProperty.greaterThanOrEqualTo(0).and(
                submittedUsersProperty.lessThan(maxDegreeProperty)).and(origImageView.visibleProperty()));

        //UI Bindings
        resetBtn.disableProperty().bind(setUsersBtn.disableProperty().not());
        openImageBtn.disableProperty().bind(areUsersSetProperty.not());
        addUserCoordBtn.disableProperty().bind(isAllSubmittedProperty.not());
        genPolyBtn.disableProperty().bind(areUsersSetProperty.not().and(isAllSubmittedProperty.not()));
        widthLabel.textProperty().bindBidirectional(widthProperty, new NumberStringConverter());
        heightLabel.textProperty().bindBidirectional(heightProperty, new NumberStringConverter());

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp"),
                new FileChooser.ExtensionFilter("JPEG files (*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
    }

    /*
     The max degree of the polynomial is determined from the number of users
     Example: For 3 users, ax^3 + bx^2 + cx^1 + d
     */
    @FXML
    private void setUsers(ActionEvent event) {
        int users = (Integer) userGroup.getSelectedToggle().getUserData();
        maxDegreeProperty.set(users);
        setUsersBtn.setDisable(true);
    }

    /*
     Resets all property values back to their inital state
     */
    @FXML
    private void reset(ActionEvent event) {
        maxDegreeProperty.set(0);
        countProperty.set(0);
        submittedUsersProperty.set(0);
        widthProperty.set(0);
        heightProperty.set(0);
        origImageView.setImage(null);
        origImageView.setVisible(false);
        monomials.clear();
        coordTextArea.clear();
        setUsersBtn.setDisable(false);
    }

    @FXML
    private void openImage(ActionEvent event) {
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null) {
            origImageView.setImage(getImage(imageFile));
            origImageView.setVisible(true);
        } else {

            //User closes file chooser
            origImageView.setVisible(false);

        }

        event.consume();
    }

    /*
     Sets the (w,h) coordinate relative to the image
     */
    @FXML
    private void setCoordinates(MouseEvent event) {
        widthProperty.set(event.getX());
        heightProperty.set(event.getY());
        event.consume();
    }

    /*
     Increments the count, decrements the number of users, and 
     adds a term to the generating group polynomial
     */
    @FXML
    private void addUserCoordinate(ActionEvent event) {
        countProperty.set(count++);
        submittedUsersProperty.set(++submittedUsers);
        addTerm();
        event.consume();
    }

    @FXML
    private void generatePolynomial(ActionEvent event) {
        coordTextArea.setText(getPolynomial().toString());
    }

    /*
     Computes the coefficent for the term and sets the degree
     of the monomial term
     */
    private void addTerm() {
        int coef = widthProperty.intValue() * heightProperty.intValue();
        int deg = degreeProperty.get();
        monomials.add(new Polynomial(coef, deg));
    }

    private Polynomial getPolynomial() {
        monomials.stream().forEach((monomial) -> {
            groupPolynomial = groupPolynomial.plus(monomial);
        });

        return groupPolynomial;
    }
}