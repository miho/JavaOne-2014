/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.javaone2014.Slide;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class SimpleInteractiveVizSlideController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private Slider sliderA;
    @FXML
    private PieChart pieChart;
    @FXML
    private Slider sliderB;
    
    private Slide slide;
    @FXML
    private AnchorPane root;
    @FXML
    private StackPane codeContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        label.setText("Basic Example");
        
        Data data1 = new PieChart.Data("A", 0);
        Data data2 = new PieChart.Data("B", 0);
        pieChart.getData().addAll(data1,data2);
        
        data1.pieValueProperty().bind(sliderA.valueProperty());
        data2.pieValueProperty().bind(sliderB.valueProperty());
        
        sliderA.setValue(sliderA.getMax());
        sliderB.setValue(sliderB.getMax());
        
    }   

    public Slide getSlide() {
        return slide;
    }

    public void setSlide(Slide slide) {
        this.slide = slide;
    }
    
    
    
}
