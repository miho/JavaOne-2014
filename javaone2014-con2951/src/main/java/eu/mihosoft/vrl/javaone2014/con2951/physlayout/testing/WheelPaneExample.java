package eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.ModifiedMouseControl;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.panes.PhysicalPane;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.panes.WheelPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


/**
 * An abstract template for example applications. This class creates the basic
 * prerequisites (layout and simulation) along with a GUI to start, stop and
 * reset the simulation.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class WheelPaneExample extends Example {

    public static final int NODE_COUNT = 5;
    public static final int NODE_SIZE = 5;

    private final List<Circle> circles;
    private final List<Line> spokeLines;
    private final List<Line> rimLines;
    private final Circle anchor;

    /**
     * Super-constructor for all examples.
     */
    public WheelPaneExample() {
        Button add = new Button("Add node");
        Button remove = new Button("Remove node");
        menu.getItems().addAll(add, remove);
        add.setOnAction(e -> {
            addCircle();
        });
        remove.setOnAction(e -> {
            removeCircle();
        });

        canvas = new WheelPane();
        setSimulation(((PhysicalPane) canvas).getSimulation());
        ((WheelPane) canvas).setSpacing(10);

        anchor = new Circle(NODE_SIZE, Color.BLACK);
        circles = new ArrayList<>();
        spokeLines = new ArrayList<>();
        rimLines = new ArrayList<>();
        for (int i = 0; i < NODE_COUNT; i++) {
            addCircle();
        }
        ((WheelPane) canvas).setCenter(anchor);

        canvas.setLayoutX((WIDTH / 2));
        canvas.setLayoutY((HEIGHT / 2));
        root.setCenter(canvas);
        canvas.toBack();
        ModifiedMouseControl.makeDraggable(anchor);
    }

    /**
     * Reset the simulation. This is called once during the setup, and whenever
     * the reset button is pressed.
     */
    @Override
    public void reset() {
        for (Circle circle : circles) {
            circle.setTranslateX((Math.random() - 0.5) * WIDTH);
            circle.setTranslateY((Math.random() - 0.5) * HEIGHT);
        }
    }

    /**
     * Get the window title.
     *
     * @return the string that the title will be set to.
     */
    @Override
    public String getTitle() {
        return "Wheel Pane";
    }

    private void addCircle() {
        int i = circles.size();
        Circle circle = new Circle(NODE_SIZE * (1 + Math.sqrt(i)));
        circle.setFill(Color.hsb(360.0 * (i % NODE_COUNT) / NODE_COUNT, 1.0, 0.5));
        circles.add(circle);

        Line spokeLine = new Line();
        spokeLine.setFill(Color.BLACK);
        spokeLine.setStroke(Color.BLACK);
        spokeLine.startXProperty().bind(circle.layoutXProperty().add(circle.translateXProperty()));
        spokeLine.startYProperty().bind(circle.layoutYProperty().add(circle.translateYProperty()));
        spokeLine.endXProperty().bind(anchor.layoutXProperty().add(anchor.translateXProperty()));
        spokeLine.endYProperty().bind(anchor.layoutYProperty().add(anchor.translateYProperty()));
        canvas.getChildren().addAll(circle, spokeLine);
        spokeLine.setManaged(false);
        spokeLine.toBack();
        spokeLines.add(spokeLine);

        if (circles.size() > 1) {
            Line wheel1 = rimLines.get(i - 1);
            wheel1.endXProperty().bind(circle.layoutXProperty().add(circle.translateXProperty()));
            wheel1.endYProperty().bind(circle.layoutYProperty().add(circle.translateYProperty()));
        }

        Line wheel2 = new Line();
        wheel2.setFill(Color.BLACK);
        wheel2.setStroke(Color.BLACK);
        wheel2.startXProperty().bind(circle.layoutXProperty().add(circle.translateXProperty()));
        wheel2.startYProperty().bind(circle.layoutYProperty().add(circle.translateYProperty()));
        wheel2.endXProperty().bind(circles.get(0).layoutXProperty().add(circles.get(0).translateXProperty()));
        wheel2.endYProperty().bind(circles.get(0).layoutYProperty().add(circles.get(0).translateYProperty()));
        wheel2.setManaged(false);
        rimLines.add(wheel2);
        canvas.getChildren().add(wheel2);
        wheel2.toBack();
        ModifiedMouseControl.makeDraggable(circle);
    }

    private void removeCircle() {
        int i = circles.size() - 1;
        if (i < 0) return;

        // Remove circle and spoke:
        canvas.getChildren().remove(circles.get(i));
        circles.remove(i);
        canvas.getChildren().remove(spokeLines.get(i));
        spokeLines.remove(i);

        // Remove the last rim segment, and reattach the previous one:
        canvas.getChildren().remove(rimLines.get(i));
        rimLines.remove(i);
        if (i > 0) {
            Line wheel1 = rimLines.get(i - 1);
            wheel1.endXProperty().bind(circles.get(0).layoutXProperty().add(circles.get(0).translateXProperty()));
            wheel1.endYProperty().bind(circles.get(0).layoutYProperty().add(circles.get(0).translateYProperty()));
        }
    }
}
