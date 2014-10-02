/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.ModifiedMouseControl;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.panes.WheelPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class WheelPane2 extends Example {

    public WheelPane2() {
        WheelPane pane = new WheelPane();
        canvas = pane;
        setSimulation(pane.getSimulation());
        root.setCenter(pane);

        canvas.setLayoutX((WIDTH / 2));
        canvas.setLayoutY((HEIGHT / 2));

        pane.setSpacing(20);

        pane.setCenter(new Circle(10, Color.BLACK));

        canvas.toBack();

        ModifiedMouseControl.makeDraggable(pane.getCenter());

        getSimulation().setFriction(6);

        Button add = new Button("Add node");
        Button remove = new Button("Remove node");
        menu.getItems().addAll(add, remove);
        add.setOnAction(e -> {
            Button btn = new Button("i: " + (canvas.getChildren().size() + 1));
            canvas.getChildren().add(btn);
        });
        remove.setOnAction(e -> {
            if (!canvas.getChildren().isEmpty()) {
                canvas.getChildren().remove(canvas.getChildren().
                        get(canvas.getChildren().size() - 1));
            }
        });

        for (int i = 0; i < 5; i++) {
            Button btn = new Button("i: " + i);
            canvas.getChildren().add(btn);
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public String getTitle() {
        return "WheelPane";
    }

}
