/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.ViewMode;
import eu.mihosoft.vrl.javaone2014.math.MouseControlUtil;
import eu.mihosoft.vrl.v3d.contours.MetaCircle;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class ForceFieldVizSlide implements Initializable {

    @FXML
    private StackPane rootView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        setCircle();
    }

    private void setCircle() {
        Circle fxC1 = new Circle(20);
        
        fxC1.setLayoutX(300);
        fxC1.setLayoutY(300);

        RadialGradient gradient1 = new RadialGradient(0,
                .1,
                0,
                0,
                20,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.BLACK));

        fxC1.setFill(gradient1);
        fxC1.setManaged(false);
        rootView.getChildren().add(fxC1);
        MouseControlUtil.makeDraggable(fxC1);

        fxC1.boundsInParentProperty().addListener((ov, oldV, newV) -> {

            RadialGradient background = new RadialGradient(0,
                    .1,
                    fxC1.getLayoutX(),
                    fxC1.getLayoutY(),
                    fxC1.getRadius()*10,
                    false,
                    CycleMethod.NO_CYCLE,
                    new Stop(0, Color.BLACK),
                    new Stop(1, Color.TRANSPARENT));

            rootView.setBackground(new Background(new BackgroundFill(
                    background, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }

    public void setSlide(Slide s) {
        s.viewModeProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue == ViewMode.FULL) {
                //
            } else {
                //
            }
        });
    }

}
