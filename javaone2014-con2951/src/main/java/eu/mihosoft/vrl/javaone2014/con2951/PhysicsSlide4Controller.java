/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.javaone2014.Action;
import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.ViewMode;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing.Example;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing.Fields;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing.Oscillator;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing.Throwing;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing.WheelPaneExample;
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
public class PhysicsSlide4Controller implements Initializable {

    @FXML
    private StackPane rootView;

    private Slide slide;

    private Example example;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        example = new Fields();
    }

    public void setSlide(Slide s) {

        this.slide = s;

        slide.viewModeProperty().addListener((ov, oldValue, newValue) -> {

            if (newValue == ViewMode.FULL) {
                addPhysics();
            } else {
                removePhysics();
            }
        });

    }

    private void addPhysics() {
        rootView.getChildren().add(example.getRootPane());
        example.startSimulation();
    }

    private void removePhysics() {
        
        example.stopSimulation();
        example.reset();
        
        rootView.getChildren().clear();
    }

}
