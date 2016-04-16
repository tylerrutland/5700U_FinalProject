import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class FinalProject2 extends Application {

    static byte[] fileContent;
    static byte userExchangeKey;
    boolean complete; // ALLOW ENCRYPTION/DECRYPTION BUTTON VISIBILITY
    public final static int FILE_SIZE = Integer.MAX_VALUE;
    Label originalBytesText = new Label("10 original bytes");
    Label originalBytes = new Label();
    Label encryptedBytesText = new Label("10 encrypted bytes");
    Label encryptedBytes = new Label();
    Label decryptedBytesText = new Label("10 decrypted bytes");
    Label decryptedBytes = new Label();
    byte[] encryptedSample;
    byte[] decryptedSample;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Button btn = new Button();
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        final Button openButton = new Button("Open a .bmp Picture...");
        final Button Encrypt = new Button("Encrypt Image");
        final Button Decrypt = new Button("Decrypt Image");
        final Button Send = new Button("Send Encrypted Image");
        final Button Receive = new Button("Receive Encrypted Image");
        final DES encrypter = new DES();
        final ImageView iv1 = new ImageView();
        final ImageView iv2 = new ImageView();
        final RadioButton DESRadioButtonOption = new RadioButton("DES");
        final ToggleGroup pickEncryptionType = new ToggleGroup();
        DESRadioButtonOption.setToggleGroup(pickEncryptionType);
        DESRadioButtonOption.setSelected(false);
        final RadioButton VEARadioButtonOption = new RadioButton("VEA");
        VEARadioButtonOption.setToggleGroup(pickEncryptionType);
        VEARadioButtonOption.setSelected(false);
        BorderPane root = new BorderPane();
        HBox hbox1 = new HBox();
        final VBox vbox1 = new VBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        final VBox vbox2 = new VBox();
        final VBox vbox3 = new VBox();
        final VBox vbox4 = new VBox();
        final VBox vbox5 = new VBox();
        final VBox vbox6 = new VBox();
        root.setTop(hbox1);
        hbox1.getChildren().addAll(vbox1, vbox2);
        vbox1.getChildren().addAll((hbox2));
        vbox2.getChildren().addAll(hbox3);
        hbox2.getChildren().addAll(vbox3, vbox4);
        hbox1.setStyle("-fx-background-color: #373b43");
        originalBytesText.setStyle("-fx-text-fill: #FFFFFF");
        originalBytes.setStyle("-fx-text-fill: #FFFFFF");
        encryptedBytesText.setStyle("-fx-text-fill: #FFFFFF");
        encryptedBytes.setStyle("-fx-text-fill: #FFFFFF");
        decryptedBytesText.setStyle("-fx-text-fill: #FFFFFF");
        decryptedBytes.setStyle("-fx-text-fill: #FFFFFF");
        DESRadioButtonOption.setStyle("-fx-text-fill: #FFFFFF");
        VEARadioButtonOption.setStyle("-fx-text-fill: #FFFFFF");
        vbox1.setPadding(new Insets(15, 20, 20, 15));
        vbox3.setSpacing(10);
        vbox2.setPadding(new Insets(15, 20, 20, 15));
        vbox5.setSpacing(10);
        vbox6.setSpacing(10);
        vbox3.setPrefWidth(225);
        vbox4.setPrefWidth(225);
        vbox4.setAlignment(Pos.CENTER);
        vbox5.setPrefWidth(225);
        vbox6.setPrefWidth(225);
        vbox6.setAlignment(Pos.CENTER);
        hbox3.getChildren().addAll(vbox5, vbox6);
        iv1.setFitWidth(180);
        iv1.setPreserveRatio(true);
        iv2.setFitWidth(180);
        iv2.setPreserveRatio(true);
        vbox4.getChildren().addAll(iv1);
        vbox5.getChildren().addAll(Receive, Decrypt, decryptedBytesText, decryptedBytes);
        vbox6.getChildren().addAll(iv2);
        short buttonWidth = 160;
        openButton.setMinWidth(buttonWidth);
        Encrypt.setMinWidth(buttonWidth);
        Decrypt.setMinWidth(buttonWidth);
        Send.setMinWidth(buttonWidth);
        Receive.setMinWidth(buttonWidth);
        vbox1.setStyle("-fx-border-color: white");
        vbox2.setStyle("-fx-border-color: white");
        iv1.setEffect(new DropShadow(20, Color.WHITE));
        iv2.setEffect(new DropShadow(20, Color.WHITE));
        vbox1.setPrefWidth(450);
        vbox2.setPrefWidth(450);
        vbox1.setPrefHeight(450);
        vbox2.setPrefHeight(450);
        vbox4.setPadding(new Insets(10, 10, 10, 10));
        vbox6.setPadding(new Insets(10, 10, 10, 10));

        vbox3.getChildren().addAll(openButton, DESRadioButtonOption, VEARadioButtonOption,
                Encrypt, originalBytesText, originalBytes, encryptedBytesText, encryptedBytes, Send);
        Scene scene = new Scene(root, 900, 450);
        primaryStage.setTitle("Final Project");
        primaryStage.setScene(scene);
        primaryStage.show();

        pickEncryptionType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                System.out.println("Selected Radio Button - " + chk.getText());

            }
        });
        openButton.setOnAction( /// OPEN PICTURE
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {

                        try {
                            File file = fileChooser.showOpenDialog(null);

                            if (file != null) {
                                Image imageToEncrypt = new Image("File:" + file.getAbsolutePath());
                                iv1.setImage(imageToEncrypt);
                                fileContent = Files.readAllBytes(file.toPath());
                                System.out.println("key is " + getUserExchangeKey(fileContent)); // PRINT RANDOM PIXES BYTE
                                System.out.println("100 of the original image bytes");
                                byte[] originalSample = new byte[10];
                                for (int i = 0; i < 100; i++) {
                                    if (i < 10) {
                                        originalSample[i] = fileContent[i];
                                    }
                                    System.out.print(fileContent[i] + " ");
                                }
                                originalBytes.setText(Arrays.toString(originalSample));
                            }
                        } catch (Exception d) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                });
        Encrypt.setOnAction( // ENCRYPT
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        if (DESRadioButtonOption.isSelected()) {

                            try {
                                fileContent = encrypter.encrypt(fileContent);
                                System.out.println("\n100 of the encrypted image bytes");
                                encryptedSample = new byte[10];
                                for (int i = 0; i < 100; i++) {
                                    if (i < 10) {
                                        encryptedSample[i] = fileContent[i];
                                    }
                                    System.out.print(fileContent[i] + " ");
                                }
                                OutputStream out = null;
                                try {
                                    out = new BufferedOutputStream(new FileOutputStream("src/images/Encrypt.bmp"));
                                    out.write(fileContent);

                                    Image image2 = new Image("file:" + out);
                                    iv1.setImage(image2);
                                    encryptedBytes.setText(Arrays.toString(encryptedSample));
                                } finally {
                                    if (out != null) {
                                        out.close();
                                    }
                                }
                            } catch (Exception d) {
                                JOptionPane.showMessageDialog(null, e);
                            }
                        }
                    }
                });
        Decrypt.setOnAction( // DECRYPT
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {

                        try {
                            System.out.println();
                            System.out.println("before decryption");
                            for (int i = 0; i < 100; i++) {
                                System.out.print(fileContent[i] + " ");
                            }
                            fileContent = encrypter.decrypt(fileContent);
                            System.out.println("100 of the decrypted image bytes");
                            decryptedSample = new byte[10];
                            for (int i = 0; i < 100; i++) {
                                if (i < 10) {
                                    decryptedSample[i] = fileContent[i];
                                }
                                System.out.print(fileContent[i] + " ");
                            }
                            InputStream in2 = new ByteArrayInputStream(fileContent);
                            BufferedImage bImageFromConvert2 = ImageIO.read(in2);
                            File imageFile2 = new File("src/images/Decrypt.bmp");
                            ImageIO.write(bImageFromConvert2, "bmp", imageFile2);
                            Image image3 = new Image("file:" + imageFile2);
                            iv2.setImage(image3);
                            decryptedBytes.setText(Arrays.toString(decryptedSample));
                        } catch (Exception d) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                });
    }

// GET RANDOM PIXEL BYTE FOR EXCHANGE AMONG GROUP MEMBERS
    public static int getUserExchangeKey(byte[] bytes) {
        int max = 0;
        for (int i = 1; i < fileContent.length; i++) {
            if (fileContent[i] > max) {
                max = fileContent[i];
            }
        }
        userExchangeKey = fileContent[(int) (Math.random() * max)];
        return userExchangeKey;

    }

    public static void main(String[] args) throws java.net.UnknownHostException, IOException {
        launch(args);
    }
}
