
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
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
    Label receivedMessage = new Label();
    Label senderPrivateKey = new Label(); // JUST FOR SHOW, OBVIOUSLY WOULDN'T BE IN PRODUCTION VERSION
    Label receiverPrivateKey = new Label(); // JUST FOR SHOW, OBVIOUSLY WOULDN'T BE IN PRODUCTION VERSION
    byte[] encryptedSample;
    byte[] decryptedSample;
    TextField senderName = new TextField("Type Sender's Name");
    TextField receiverName = new TextField("Receiver 1");
    int DHSender, DHReceiver; // DHVALUES
    int A, B; // DHVALUES
    SendReceive sendMyMessage = new SendReceive();
    Sender sender = new Sender();
    Receiver receiver = new Receiver();
    boolean p; // CHECK IF A POINT ON SENDER IMAGE HAS BEEN CHOSEN
    boolean q; // CHECK IF A POINT ON RECEIVER IMAGE HAS BEEN CHOSEN

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Button btn = new Button();
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        final Button openButton = new Button("Open a .bmp Picture..."); // OPEN SENDER IMAGE
        final Button openButton2 = new Button("Open a Picture..."); // OPEN RECEIVER IMAGE
        final Button Encrypt = new Button("Encrypt Image");
        final Button Decrypt = new Button("Decrypt Image");
        final Button Send = new Button("Send Encrypted Image");
        final Button Receive = new Button("Receive Encrypted Image");
        final DES encrypter = new DES();
        final ImageView iv1 = new ImageView();
        final ImageView iv2 = new ImageView();
        final ImageView iv3 = new ImageView();
        final ImageView iv4 = new ImageView();

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
        hbox3.setMaxHeight(225);
        hbox4.setMinHeight(225);
        hbox4.setPrefWidth(225);
        hbox3.setStyle("-fx-border-color: white");
        hbox4.setStyle("-fx-border-color: white");

        iv1.setFitWidth(180);
        iv1.setPreserveRatio(true);
        iv2.setFitWidth(180);
        iv2.setPreserveRatio(true);
        iv4.setFitWidth(180);
        iv4.setPreserveRatio(true);
        vbox4.getChildren().addAll(iv1);
        vbox5.getChildren().addAll(openButton2, Receive, receivedMessage, receiverPrivateKey, Decrypt, decryptedBytesText, decryptedBytes);
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
        iv4.setEffect(new DropShadow(20, Color.WHITE));
        vbox1.setPrefWidth(500);
        vbox2.setPrefWidth(500);
        vbox1.setPrefHeight(750);
        vbox2.setPrefHeight(750);
        vbox4.setPadding(new Insets(10, 10, 10, 10));
        vbox6.setPadding(new Insets(10, 10, 10, 10));
        vbox3.getChildren().addAll(openButton, senderPrivateKey, DESRadioButtonOption, VEARadioButtonOption,
                Encrypt, originalBytesText, originalBytes, encryptedBytesText,
                encryptedBytes, sendChooser, SendToUserRadioButtonOption, SendToGroupRadioButtonOption);
        Scene scene = new Scene(root, 1000, 750);
        primaryStage.setTitle("Final Project");
        primaryStage.setScene(scene);
        primaryStage.show();

        pickEncryptionType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button

            }
        });
        openButton.setOnAction( /// OPEN PICTURE
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {

                try {
                    File file = fileChooser.showOpenDialog(null);

                    if (file != null) {
                        originalBytes.setText("");
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
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Pick a Point");
                        alert.setHeaderText(null);
                        alert.setContentText("Double-click a point on the image");
                        alert.showAndWait();
                        iv1.setOnMouseClicked(new EventHandler<MouseEvent>() { // GET X,Y COORDS FROM SENDER IMAGE

                            @Override
                            public void handle(MouseEvent arg0) {
                                sender.setSenderPrivateKey((int) arg0.getX(), (int) arg0.getY());
                                p = true;
                                System.out.println(sender.getSenderPrivateKey());
                            }

                        });
                    }
                } catch (Exception d) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        });
        openButton2.setOnAction( /// OPEN RECEIVER PICTURE TO GET PIXEL COORDINATES
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {

                try {
                    File file = fileChooser.showOpenDialog(null);

                    if (file != null) {
                        Image imageToEncrypt = new Image("File:" + file.getAbsolutePath());
                        iv2.setImage(imageToEncrypt);
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Pick a Point");
                        alert.setHeaderText(null);
                        alert.setContentText("Double-click a point on the image");
                        alert.showAndWait();
                        iv2.setOnMouseClicked(new EventHandler<MouseEvent>() { // GET X,Y COORDS FROM SENDER IMAGE

                            @Override
                            public void handle(MouseEvent arg1) {
                                receiver.setReceiverPrivateKey((int) arg1.getX(), (int) arg1.getY());
                                q = true;
                                System.out.println(receiver.getReceiverPrivateKey());
                            }

                        });
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

        pickSendType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                if (SendToUserRadioButtonOption.isSelected()) {
                    vbox3.getChildren().addAll(receiverName, senderName, Send);
                };
            }
        });

        Send.setOnAction( // SEND MESSAGE
                new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent e) {
                if (p && q) {
                    try {
                        sender.setUserAddress(senderName.getText());
                        sender.setReceiverAddress(receiverName.getText());
                        sendMyMessage.setGandP();

                        sender.setA(sendMyMessage.g, sendMyMessage.p);
                        if (sender.checkUser() == 1) {
                            sendMyMessage.setMessage(fileContent);
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Alert");
                            alert.setHeaderText(null);
                            alert.setContentText("Message sent successfully to "+ receiverName.getText());
                            alert.showAndWait();
                        } else {
                            JOptionPane.showMessageDialog(null, "User not found");
                        }
                    } catch (Exception d) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                } else if (!q) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Pick a Point");
                    alert.setHeaderText(null);
                    alert.setContentText("Receiver needs to open an image and pick a point");
                    alert.showAndWait();
                }
            }
        });

        Receive.setOnAction( // RECEIVE MESSAGE
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {

                try {
                    //receiver.setReceiverPrivateKey();
                    receiver.setB(sendMyMessage.g, sendMyMessage.p);
                    receivedMessage.setStyle("-fx-text-fill: RED");
                    System.out.println("BEORE p, A, B: " + sendMyMessage.p + " , " + sender.getA() + " , " + receiver.getB());
                    sender.setDHSender(sendMyMessage.p, receiver.getB());
                    System.out.println(DHSender + " and " + DHReceiver);
                    if (DHSender == DHReceiver) {
                        receivedMessage.setText("Message is: " + Arrays.toString(sendMyMessage.getMessage()));
                    } else {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Sender and Receiver do not share a correct key");
                        alert.showAndWait();
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

                    System.out.println();
                    System.out.println("before decryption");
                    for (int i = 0; i < 100; i++) {
                        System.out.print(fileContent[i] + " ");
                    }
                    fileContent = encrypter.decrypt(fileContent);
                    System.out.println("Fuck " + Arrays.toString(fileContent));
                    System.out.println("100 of the decrypted image bytes");
                    decryptedSample = new byte[10];
                    for (int i = 0; i < 100; i++) {
                        if (i < 10) {
                            decryptedSample[i] = fileContent[i];
                        }
                        System.out.print(fileContent[i] + " ");
                    }
                    System.out.println("fuck" + Arrays.toString(fileContent));
                    decryptedBytes.setText("Decrypted byte sample: " + Arrays.toString(decryptedSample));
                    InputStream in2 = new ByteArrayInputStream(fileContent);
                    BufferedImage bImageFromConvert2 = ImageIO.read(in2);
                    File imageFile2 = new File("src/images/Decrypt.bmp");
                    ImageIO.write(bImageFromConvert2, "bmp", imageFile2);
                    Image image3 = new Image("file:" + imageFile2);
                    iv2.setImage(image3);

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

    class Sender extends SendReceive {

        int senderAddress, receiverAddress, a;

        public void setSenderPrivateKey(int x, int y) {
            a = x + y;
        }
//        public void setSenderPrivateKey() {
//            //a = getUserExchangeKey(message);
//        }

        public int getSenderPrivateKey() {
            return a;
        }

        public void setA(int g, int p) {
            A = ((int) Math.pow(g, a) % p);
        }

        public int getA() {
            return A;
        }

        public void setUserAddress(String userName) {
            senderAddress = userName.hashCode();
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

        public int checkUser() {
            if (getReceiverAddress() == Receiver1.getText().hashCode()) {
                return 1;
            } else {
                return 0;
            }
        }

        public void setDHSender(int p, int B) {
            DHSender = (int) Math.pow(B, getSenderPrivateKey()) % p;
        }

        public int getDHSender() {
            return DHSender;
        }
    }

    class Receiver extends SendReceive {

        int b;

        public void setB(int g, int p) {
            B = (int) Math.pow(g, b) % p;
            setDHReceiver(p);
        }

        public int getB() {
            return B;
        }

        public void setReceiverPrivateKey(int x, int y) {
            b = x + y;
        }
//        public void setReceiverPrivateKey() {
//            b = getUserExchangeKey(message);
//        }

        public int getReceiverPrivateKey() {
            return b;
        }

        public void setDHReceiver(int p) {
            DHReceiver = (int) Math.pow(A, getReceiverPrivateKey()) % p;
        }

        public int getDHReceiver() {

            return DHReceiver;
        }
    }

    class SendReceive {

        int g, p;
        byte message[];
        int primes[] = {107, 109, 113, 127, 131,
            137, 139, 149, 151, 157, 163, 167, 173, 179, 181,
            191, 193, 197, 199, 211, 223, 227, 229, 233, 239,
            241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
            307, 311, 313, 317, 331, 337, 347, 349};

        public void setGandP() {
            g = (int) primes[(int) (Math.random() * 43)];
            p = (int) primes[(int) (Math.random() * 43)];
        }

        public void setMessage(byte[] messageToSend) {
            message = messageToSend;
        }

        public byte[] getMessage() {
            return message;
        }
    }

    public static void main(String[] args) throws java.net.UnknownHostException, IOException {
        launch(args);

    }
}
