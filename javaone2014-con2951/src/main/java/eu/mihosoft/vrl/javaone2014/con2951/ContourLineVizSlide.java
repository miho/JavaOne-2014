/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.javaone2014.Slide;
import eu.mihosoft.vrl.javaone2014.ViewMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class ContourLineVizSlide implements Initializable {

    @FXML
    private StackPane rootView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setSlide(Slide s) {
        s.viewModeProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue == ViewMode.FULL) {
                Pane p = new eu.mihosoft.vrl.v3d.contours.Main().getRootPane();
//        p.setStyle("-fx-background-color: red;");
                StackPane.setAlignment(p, Pos.CENTER);
                rootView.getChildren().add(p);
            } else {
                rootView.getChildren().clear();
            }
        });
    }

}
