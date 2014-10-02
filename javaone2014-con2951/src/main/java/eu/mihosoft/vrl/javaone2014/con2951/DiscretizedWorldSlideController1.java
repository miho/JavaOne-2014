/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.javaone2014.Action;
import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.ViewMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class DiscretizedWorldSlideController1 implements Initializable {

    @FXML
    private StackPane rootView;

    private Slide slide;

    private Timeline tl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }

    public void setSlide(Slide s) {

        System.out.println("here");

        this.slide = s;

        Circle circle = new Circle(50, Color.CORNFLOWERBLUE);
        circle.setManaged(false);
        circle.setLayoutY(400);
        rootView.getChildren().add(circle);

        slide.viewModeProperty().addListener((ov, oldValue, newValue) -> {

            if (newValue == ViewMode.FULL) {
                if (tl != null) {
                    tl.stop();
                }
                tl = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(circle.layoutXProperty(),
                                        100+
                                        circle.getRadius() * 2 + 5)
                        ),
                        new KeyFrame(Duration.seconds(3),
                                new KeyValue(circle.layoutXProperty(),
                                        100+
                                        rootView.getWidth()
                                        - circle.getRadius() * 2 - 5)
                        ));

                tl.setAutoReverse(true);

                tl.setCycleCount(Timeline.INDEFINITE);
                tl.play();
            } else {
                if (tl != null) {
                    tl.stop();
                }
            }
        });

    }

}
