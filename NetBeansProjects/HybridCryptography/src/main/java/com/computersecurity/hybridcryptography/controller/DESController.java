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
import com.computersecurity.hybridcryptography.model.moduleVEA.Polynomial;
import com.computersecurity.hybridcryptography.model.moduleVEA.Term;
import static com.computersecurity.hybridcryptography.util.CryptoUtils.getImage;
import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.NumberStringConverter;

/**
 * This class controls the model(s) and view of its associated fxml file
 */
public class DESController implements Initializable {

    //Service Classes
    private final DESEncryptionService encService = new DESEncryptionService();
    private final DESDecryptionService decService = new DESDecryptionService();
    private final DHGeneratorService dhGenService = new DHGeneratorService();

    private final ToggleGroup bitLengthGroupDH = new ToggleGroup();
    private final ToggleGroup bitLengthGroupRSA = new ToggleGroup();
    private final ToggleGroup modeGroup = new ToggleGroup();
    private final FileChooser fileChooser = new FileChooser();
        private int count;
    private int submittedUsers;
    private final ArrayList<String> usersInfo = new ArrayList(4);
    private Polynomial groupPolynomial = new Polynomial(0, 0);
    private final ArrayList<Term> terms = new ArrayList(4);
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
    

    private File imageFile;
    private File imageVSSFile;

    

    @FXML
    private BorderPane imagePane, encDecPane;

    @FXML
    private ImageView origImageView;
    
    @FXML
    private ImageView vssImageView;

    @FXML
    private ImageView decImageView;

    @FXML
    private RadioButton rbECB, rbCBC;

    @FXML
    private RadioButton rb512, rb1024;
    
    @FXML
    private RadioButton rbRSA512, rbRSA1024;

    @FXML
    private TextField baseTF, modTF;

    @FXML
    private TextField pubKeyATF, pubKeyBTF;

    @FXML
    private PasswordField privKeyAPF, privKeyBPF;

    @FXML
    private PasswordField secKeyAPF, secKeyBPF;

    @FXML
    private Button openImageBtn, openVSSImageBtn, dhGenBtn;

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
    
    @FXML
    private RadioButton rb2Users, rb3Users, rb4Users;

    @FXML
    private TextField xValueTF;

    @FXML
    private Button setUsersBtn, resetBtn;

    @FXML
    private Button addUserCoordBtn, clearTextBtn, genPolyBtn;

    @FXML
    private Label widthLabel, heightLabel;

    @FXML
    private TextArea vssTextArea;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rb2Users.setUserData(2);
        rb2Users.setSelected(true);
        rb2Users.setToggleGroup(userGroup);

        rb3Users.setUserData(3);
        rb3Users.setToggleGroup(userGroup);

        rb4Users.setUserData(4);
        rb4Users.setToggleGroup(userGroup);

        xValueTF.setText(Integer.toString(new SecureRandom().nextInt()));

        //Field Settings
        countProperty.set(count = 0);
        maxDegreeProperty.set(0);
        submittedUsersProperty.set(submittedUsers = 0);

        //Field Bindings
        degreeProperty.bind(maxDegreeProperty.subtract(countProperty));
        areUsersSetProperty.bind(submittedUsersProperty.greaterThanOrEqualTo(0).and(
                submittedUsersProperty.lessThan(maxDegreeProperty)));
        isAllSubmittedProperty.bind(submittedUsersProperty.greaterThanOrEqualTo(0).and(
                submittedUsersProperty.lessThan(maxDegreeProperty)).and(vssImageView.visibleProperty()));

        //UI Bindings
        resetBtn.disableProperty().bind(setUsersBtn.disableProperty().not());
        openVSSImageBtn.disableProperty().bind(areUsersSetProperty.not());
        addUserCoordBtn.disableProperty().bind(isAllSubmittedProperty.not());
        genPolyBtn.disableProperty().bind(addUserCoordBtn.disableProperty().not());
        widthLabel.textProperty().bindBidirectional(widthProperty, new NumberStringConverter());
        heightLabel.textProperty().bindBidirectional(heightProperty, new NumberStringConverter());

    
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
        rb512.setToggleGroup(bitLengthGroupDH);

        rb1024.setUserData(1024);
        rb1024.setToggleGroup(bitLengthGroupDH);

        rbRSA512.setUserData(512);
        rbRSA512.setSelected(true);
        rbRSA512.setToggleGroup(bitLengthGroupRSA);

        rbRSA1024.setUserData(1024);
        rbRSA1024.setToggleGroup(bitLengthGroupRSA);
        
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
    
    @FXML
    private void openVSSImage(ActionEvent event) {
        imageVSSFile = fileChooser.showOpenDialog(null);
        if (imageVSSFile != null) {
            vssImageView.setImage(getImage(imageVSSFile));
            invalidLabel.setVisible(false);
        } else {

            //User closes file chooser
        }

        event.consume();
    }


    /*
     The max degree of the polynomial is determined from the number of users
     Example: For 3 users, ax^3 + bx^2 + cx^1 + d, the button disables itself
     after first call 
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
        countProperty.set(count = 0);
        submittedUsersProperty.set(submittedUsers = 0);
        widthProperty.set(0);
        heightProperty.set(0);
        origImageView.setImage(null);
        origImageView.setVisible(false);
        monomials.clear();
        setUsersBtn.setDisable(false);
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
        if (!vssTextArea.getText().isEmpty()) {
            vssTextArea.clear();
        }

        countProperty.set(count++);
        submittedUsersProperty.set(++submittedUsers);
        addUserMonomialAndTerm();
        event.consume();
    }

    @FXML
    private void clearText(ActionEvent event) {
        if (!vssTextArea.getText().isEmpty()) {
            vssTextArea.clear();
        }
    }

    /*
     Computes the y value based from the generated x value and uses the x value 
     of the polynomial function in which the y value is the private key of the user
     */
    @FXML
    private void generatePolynomial(ActionEvent event) {
        vssTextArea.setText("Group Base Polynomial:\t" + getFinalPolynomial().toString());
        terms.clear();
        usersInfo.clear();
        resetBtn.fire();
    }

    /*
     Computes the coefficent for the nonomial term, sets the degree
     of the monomial term and generates a random x value
     */
    private void addUserMonomialAndTerm() {
        int width = widthProperty.intValue();
        int height = heightProperty.intValue();
        int coef = width * height;
        int deg = degreeProperty.get();

        //Add single monomial from user
        Polynomial monomial = new Polynomial(coef, deg);
        monomials.add(monomial);

        //Compute Term
        Term term = new Term(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE);
        terms.add(term);

        //Set the text area with the user information
        String userInfo = "\nUser " + submittedUsers + ":\t" + monomial.toString();
        usersInfo.add(userInfo + "\t| x = " + xValueTF.getText() + "\t->\t" + term.getTermValue() + "\n");
        vssTextArea.setText(Arrays.toString(usersInfo.toArray()));
    }

    /*
     Generates the final polynomial by simple adding all the 
     individual monomials together
     */
    private Polynomial getFinalPolynomial() {
        groupPolynomial = new Polynomial(new SecureRandom().nextInt(), 0);//Resets polynomial
        monomials.stream().forEach((monomial) -> groupPolynomial = groupPolynomial.plus(monomial));
        return groupPolynomial;
    }



    /*
     Generates all the Diffie-Hellman parameters: key pair, Diffie-Hellman
     parameter specifications (modulus, base, and choosen bit length value), and 
     shared secret key for both parties
     */
    @FXML
    private void generateParameters(ActionEvent event) {
        if (imageFile != null) {
            int bitLength = (Integer) bitLengthGroupDH.getSelectedToggle().getUserData();
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
