import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class FinalProject2 extends Application {

    static byte[] fileContent = new byte[1000000];
    static byte userExchangeKey;
    boolean complete; // ALLOW ENCRYPTION/DECRYPTION BUTTON VISIBILITY

    @Override
    public void start(final Stage primaryStage) throws Exception {
        Button btn = new Button();
        final FileChooser fileChooser = new FileChooser();

        final Button openButton = new Button("Open a Picture...");
        final Button Encrypt = new Button("Encrypt Image");
        final Button Decrypt = new Button("Decrypt Image");
        final DES encrypter = new DES();
        final ImageView iv1 = new ImageView();
        BorderPane root = new BorderPane();
        HBox hbox = new HBox();
        final VBox vbox = new VBox();
        root.setTop(hbox);
        hbox.getChildren().addAll(vbox, iv1);
        vbox.getChildren().addAll(openButton);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setSpacing(20);
        short buttonWidth = 160;
        openButton.setMinWidth(buttonWidth);
        Encrypt.setMinWidth(buttonWidth);
        Decrypt.setMinWidth(buttonWidth);
        Scene scene = new Scene(root, 600, 450);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        openButton.setOnAction( /// OPEN PICTURE
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {

                        try {

                            String file = "C:\\Users\\Tyler\\Pictures\\palmTree.bmp";
                            File imageToEncrypt = new File(file);
                            Image image = new Image("file:///C:\\Users\\Tyler\\Pictures\\palmTree.bmp");
                            iv1.setImage(image);
                            fileContent = Files.readAllBytes(imageToEncrypt.toPath());
                            vbox.getChildren().addAll(Encrypt);
                            System.out.println("key is " + getUserExchangeKey(fileContent)); // PRINT RANDOM PIXES BYTE
                            System.out.println("100 of the original image bytes");
                            for (int i = 0; i < 100; i++) {
                                System.out.print(fileContent[i] + " ");
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

                        try {
                            fileContent = encrypter.encrypt(fileContent);
                            System.out.println("\n100 of the encrypted image bytes");
                            for (int i = 0; i < 100; i++) {
                                System.out.print(fileContent[i] + " ");
                            }
                            //JOptionPane.showMessageDialog(null, "The file encrypted Successfully, image in folder");
                            OutputStream out = null;
                            vbox.getChildren().addAll(Decrypt);
                            try {
                                out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Tyler\\Pictures\\Encrypt.bmp"));
                                out.write(fileContent);

                                Image image2 = new Image("file:///C:\\Users\\Tyler\\Pictures\\Encrypt.bmp");
                                iv1.setImage(image2);
                            } finally {
                                if (out != null) {
                                    out.close();
                                }
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
                            System.out.println();
                            fileContent = encrypter.decrypt(fileContent);
                            System.out.println("100 of the decrypted image bytes");
                            for (int i = 0; i < 100; i++) {
                                System.out.print(fileContent[i] + " ");
                            }
                            InputStream in2 = new ByteArrayInputStream(fileContent);
                            BufferedImage bImageFromConvert2 = ImageIO.read(in2);
                            File imageFile2 = new File("C:\\Users\\Tyler\\Pictures\\Decrypt.bmp");
                            ImageIO.write(bImageFromConvert2, "bmp", imageFile2);
                            Image image3 = new Image("file:///C:\\Users\\Tyler\\Pictures\\Decrypt.bmp");
                            iv1.setImage(image3);
                        } catch (Exception d) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        Point location = new Point(4, 13);

        System.out.println("Starting location:");
        System.out.println("X equals " + location.x);
        System.out.println("Y equals " + location.y);

        System.out.println("\nMoving to (7, 6)");
        location.x = 7;
        location.y = 6;

        System.out.println("\nEnding location:");
        System.out.println("X equals " + location.x);
        System.out.println("Y equals " + location.y);

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
}
//class PointSetter {
//
//  public static void main(String[] arguments) {
//    Point location = new Point(4, 13);
//
//    System.out.println("Starting location:");
//    System.out.println("X equals " + location.x);
//    System.out.println("Y equals " + location.y);
//
//    System.out.println("\nMoving to (7, 6)");
//    location.x = 7;
//    location.y = 6;
//
//    System.out.println("\nEnding location:");
//    System.out.println("X equals " + location.x);
//    System.out.println("Y equals " + location.y);
//  }
//}
