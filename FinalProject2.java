import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    byte[] fileContent;
    static byte userExchangeKey;
    boolean complete; // ALLOW ENCRYPTION/DECRYPTION BUTTON VISIBILITY
    public final static int FILE_SIZE = Integer.MAX_VALUE;
    Label originalBytesText = new Label("10 original bytes");
    Label originalBytes = new Label();
    Label encryptedBytesText = new Label("10 encrypted bytes");
    Label encryptedBytes = new Label();
    Label decryptedBytesText = new Label("10 decrypted bytes");
    Label decryptedBytes = new Label();
    Label Receiver1 = new Label("Receiver 1");
    byte[] encryptedSample;
    byte[] decryptedSample;
    SendReceive sendMyMessage = new SendReceive();
    TextField senderName = new TextField("Type Sender's Name");
    TextField receiverName = new TextField("Receiver 1");

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
        final ImageView iv3 = new ImageView();

        final RadioButton DESRadioButtonOption = new RadioButton("DES");
        final ToggleGroup pickEncryptionType = new ToggleGroup();
        DESRadioButtonOption.setToggleGroup(pickEncryptionType);
        DESRadioButtonOption.setSelected(false);
        final RadioButton VEARadioButtonOption = new RadioButton("VEA");
        VEARadioButtonOption.setToggleGroup(pickEncryptionType);
        VEARadioButtonOption.setSelected(false);

        Label sendChooser = new Label("Select where to image to");
        final RadioButton SendToUserRadioButtonOption = new RadioButton("Specific User");
        final ToggleGroup pickSendType = new ToggleGroup();
        SendToUserRadioButtonOption.setToggleGroup(pickSendType);
        SendToUserRadioButtonOption.setSelected(false);
        final RadioButton SendToGroupRadioButtonOption = new RadioButton("Group");
        SendToGroupRadioButtonOption.setToggleGroup(pickSendType);
        SendToGroupRadioButtonOption.setSelected(false);

        BorderPane root = new BorderPane();
        HBox hbox1 = new HBox();
        final VBox vbox1 = new VBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        final VBox vbox2 = new VBox();
        final VBox vbox3 = new VBox();
        final VBox vbox4 = new VBox();
        final VBox vbox5 = new VBox();
        final VBox vbox6 = new VBox();
        final VBox vbox7 = new VBox();
        final VBox vbox8 = new VBox();
        root.setTop(hbox1);
        hbox1.getChildren().addAll(vbox1, vbox2);
        vbox1.getChildren().addAll((hbox2));
        vbox2.getChildren().addAll(hbox3, hbox4);
        hbox2.getChildren().addAll(vbox3, vbox4);
        hbox4.getChildren().addAll(vbox7, vbox8);
        hbox1.setStyle("-fx-background-color: #373b43");
        originalBytesText.setStyle("-fx-text-fill: #FFFFFF");
        originalBytes.setStyle("-fx-text-fill: RED");
        encryptedBytesText.setStyle("-fx-text-fill: #FFFFFF");
        encryptedBytes.setStyle("-fx-text-fill: RED");
        decryptedBytesText.setStyle("-fx-text-fill: #FFFFFF");
        decryptedBytes.setStyle("-fx-text-fill: RED");
        DESRadioButtonOption.setStyle("-fx-text-fill: #FFFFFF");
        VEARadioButtonOption.setStyle("-fx-text-fill: #FFFFFF");
        sendChooser.setStyle("-fx-text-fill: #FFFFFF");
        SendToUserRadioButtonOption.setStyle("-fx-text-fill: #FFFFFF");
        SendToGroupRadioButtonOption.setStyle("-fx-text-fill: #FFFFFF");
        vbox1.setPadding(new Insets(15, 20, 20, 15));
        vbox3.setSpacing(10);
        hbox3.setPadding(new Insets(15, 20, 20, 15));
        hbox4.setPadding(new Insets(15, 20, 20, 15));
        vbox5.setSpacing(10);
        vbox6.setSpacing(10);
        vbox7.setSpacing(10);
        vbox8.setSpacing(10);
        vbox3.setPrefWidth(225);
        vbox4.setPrefWidth(225);
        vbox4.setAlignment(Pos.CENTER);
        vbox5.setPrefWidth(225);
        vbox6.setPrefWidth(225);
        vbox6.setAlignment(Pos.CENTER);
        hbox3.getChildren().addAll(vbox5, vbox6);
        hbox3.setPrefHeight(225);
        hbox4.setPrefHeight(225);
        hbox3.setPrefWidth(225);
        hbox4.setPrefWidth(225);
        hbox3.setStyle("-fx-border-color: white");
        hbox4.setStyle("-fx-border-color: white");

        iv1.setFitWidth(180);
        iv1.setPreserveRatio(true);
        iv2.setFitWidth(180);
        iv2.setPreserveRatio(true);
        vbox4.getChildren().addAll(iv1);
        vbox5.getChildren().addAll(Receive, Decrypt, decryptedBytesText);
        vbox6.getChildren().addAll(iv2);
        vbox8.getChildren().addAll(iv3);
        //vbox7.getChildren().addAll(Receive, Decrypt);
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
                Encrypt, originalBytesText, originalBytes, encryptedBytesText,
                encryptedBytes, sendChooser, SendToUserRadioButtonOption, SendToGroupRadioButtonOption, senderName, receiverName, Send);
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

        Send.setOnAction( // SEND MESSAGE
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {

                        try {

                            sendMyMessage.setUserAddress(senderName.getText());
                            sendMyMessage.setReceiverAddress(receiverName.getText());
                            sendMyMessage.setGandP();
                            sendMyMessage.getSenderPrivateKey();
                            System.out.println(sendMyMessage.getB());
                            if (sendMyMessage.checkUser() == 1) {
                                sendMyMessage.setMessage(fileContent);
                            } else {
                                JOptionPane.showMessageDialog(null, "User not found");
                            }

                        } catch (Exception d) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                });
        Receive.setOnAction( // RECEIVE MESSAGE
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {

                        try {
                            sendMyMessage.getReceiverPrivateKey();
                            Label receivedMessage = new Label();
                            receivedMessage.setStyle("-fx-text-fill: RED");
                            int p = (int) (Math.pow(sendMyMessage.getB(), sendMyMessage.a) % sendMyMessage.p);
                            int q = (int) (Math.pow(sendMyMessage.getA(), sendMyMessage.b) % sendMyMessage.p);
                            System.out.println(p + " and " + q);
                            if (p == q) {
                                receivedMessage.setText(Arrays.toString(sendMyMessage.getMessage()));
                                vbox5.getChildren().addAll(receivedMessage);
                            }
                        } catch (Exception d) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                });
        Decrypt.setOnAction( // DECRYPT
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {

                        try {
                            System.arraycopy(sendMyMessage.getMessage(), 0, fileContent, 0, sendMyMessage.getMessage().length);
                            fileContent = encrypter.decrypt(fileContent);
                            System.out.println();
                            System.out.println("before decryption");
                            for (int i = 0; i < 100; i++) {
                                System.out.print(fileContent[i] + " ");
                            }
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
    public int getUserExchangeKey(byte[] bytes) {
        int max = 0;
        for (int i = 1; i < fileContent.length; i++) {
            if (fileContent[i] > max) {
                max = fileContent[i];
            }
        }
        userExchangeKey = fileContent[(int) (Math.random() * max)];
        userExchangeKey = (byte) Math.abs(userExchangeKey);
        if (userExchangeKey == 0 || userExchangeKey > 20) {
            getUserExchangeKey(fileContent);
        }
        return userExchangeKey;

    }

    class SendReceive {

        int senderAddress;
        int receiverAddress;
        byte message[];
        int primes[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
            31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
            73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
            127, 131, 137, 139, 149, 151, 157, 163, 167, 173};
        int g, p, a, b;
        int A, B;

        public void setUserAddress(String userName) {
            senderAddress = userName.hashCode();
        }

        public int getA() {
            return (A = ((int) Math.pow(g, a) % p));
        }

        public int getB() {
            return (B = ((int) Math.pow(g, b) % p));
        }

        public int getSenderPrivateKey() {
            return (a = getUserExchangeKey(message));
        }

        public int getReceiverPrivateKey() {
            return (b = getUserExchangeKey(message));
        }

        public void setGandP() {
            g = (int) primes[(int) (Math.random() * 40)];
            p = (int) primes[(int) (Math.random() * 40)];
        }

        public int getUserAddress() {
            return senderAddress;
        }

        public void setReceiverAddress(String receiverName) {
            receiverAddress = receiverName.hashCode();
        }

        public int getReceiverAddress() {
            return receiverAddress;
        }

        public void setMessage(byte[] messageToSend) {
            message = messageToSend;
            System.out.println(Arrays.toString(message));
        }

        public byte[] getMessage() {
            return message;
        }

        public int checkUser() {
            if (getReceiverAddress() == Receiver1.getText().hashCode()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) throws java.net.UnknownHostException, IOException {
        launch(args);

    }
}
