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
public class DiscretizedWorldSlideController implements Initializable {

    @FXML
    private StackPane rootView;

    private Slide slide;

    private Timeline tl;
    private Timeline tl2;
    private Timeline tl3;

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
        circle.setLayoutY(420);
        rootView.getChildren().add(circle);

        Circle circle2 = new Circle(50, Color.CRIMSON);
        circle2.setManaged(false);
        circle2.setLayoutY(310);
        rootView.getChildren().add(circle2);

        Circle circle3 = new Circle(50, Color.GOLD);
        circle3.setManaged(false);
        circle3.setLayoutY(200);
        rootView.getChildren().add(circle3);

        slide.viewModeProperty().addListener((ov, oldValue, newValue) -> {

            if (newValue == ViewMode.FULL) {
                if (tl != null) {
                    tl.stop();
                }
                tl = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(circle.layoutXProperty(),
                                        100
                                        + circle.getRadius() * 2 + 5)
                        ),
                        new KeyFrame(Duration.seconds(5),
                                new KeyValue(circle.layoutXProperty(),
                                        100
                                        + rootView.getWidth()
                                        - circle.getRadius() * 2 - 5)
                        ));

                tl.setAutoReverse(true);
                tl.setCycleCount(Timeline.INDEFINITE);
                tl.play();

                if (tl2 != null) {
                    tl2.stop();
                }
                tl2 = new Timeline(3,
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(circle2.layoutXProperty(),
                                        100
                                        + circle2.getRadius() * 2 + 5)
                        ),
                        new KeyFrame(Duration.seconds(5),
                                new KeyValue(circle2.layoutXProperty(),
                                        100
                                        + rootView.getWidth()
                                        - circle2.getRadius() * 2 - 5)
                        ));

                tl2.setAutoReverse(true);
                tl2.setCycleCount(Timeline.INDEFINITE);
                tl2.play();

                if (tl3 != null) {
                    tl3.stop();
                }
                tl3 = new Timeline(1,
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(circle3.layoutXProperty(),
                                        100
                                        + circle3.getRadius() * 2 + 5)
                        ),
                        new KeyFrame(Duration.seconds(5),
                                new KeyValue(circle3.layoutXProperty(),
                                        100
                                        + rootView.getWidth()
                                        - circle3.getRadius() * 2 - 5)
                        ));

                tl3.setAutoReverse(true);
                tl3.setCycleCount(Timeline.INDEFINITE);
                tl3.play();
            } else {

                if (tl3 != null) {
                    tl3.stop();
                }

                if (tl2 != null) {
                    tl2.stop();
                }
                if (tl != null) {
                    tl.stop();
                }

            }
        });

    }

}
