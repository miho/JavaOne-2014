/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;
 
import java.nio.ByteBuffer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
public class ImageOpsTest extends Application {
 
    // Image Data
    private static final int IMAGE_WIDTH = 2;
    private static final int IMAGE_HEIGHT = 2;
    private final byte imageData[] = 
        new byte[IMAGE_WIDTH * IMAGE_HEIGHT * 3];
    
    // Drawing Surface (Canvas)
    private WritableImage gc;
    private ImageView canvas;
    private Group root;
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PixelWriter Test");
        root = new Group();
        gc = new WritableImage(IMAGE_WIDTH, IMAGE_HEIGHT);

        canvas = new ImageView(gc);
      
        createImageData();
        drawImageData();
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
        
        canvas.setSmooth(false);
        
        canvas.fitWidthProperty().bind(primaryStage.widthProperty());
        canvas.fitHeightProperty().bind(primaryStage.heightProperty());
 
    }
 
    private void createImageData() {
        int i = 0;
        for (int y = 0; y < IMAGE_HEIGHT; y++) {
            int r = y * 255 / IMAGE_HEIGHT;
            for (int x = 0; x < IMAGE_WIDTH; x++) {
                int g = x * 255 / IMAGE_WIDTH;
                imageData[i] = (byte) r;
                imageData[i + 1] = (byte) r;
                imageData[i + 2] = (byte) r;
                i += 3;
            }
        }
    }
 
    private void drawImageData() {
        PixelWriter pixelWriter = gc.getPixelWriter();
        PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance();
        pixelWriter.setPixels(0, 0, IMAGE_WIDTH,
                            IMAGE_HEIGHT, pixelFormat, imageData, 
                            0, IMAGE_WIDTH * 3);
 
        // Add drop shadow effect
//        canvas.setEffect(new DropShadow(20, 20, 20, Color.GRAY));
        root.getChildren().add(canvas);
    }
}