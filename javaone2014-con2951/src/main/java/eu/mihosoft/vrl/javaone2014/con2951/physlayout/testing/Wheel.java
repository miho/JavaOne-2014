package eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Spring;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import jfxtras.labs.util.event.MouseControlUtil;

/**
 * These are several nodes connected in a great circle. They will rapidly
 * converge into a circle formation from random initial positions.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class Wheel extends Example {

    private static final int NODE_COUNT = 25;
    private static final int NODE_SIZE = 10;
    private final Circle[] circles;
    private final Circle anchor;

    public Wheel() {
        circles = new Circle[NODE_COUNT];
        for (int i = 0; i < NODE_COUNT; i++) {
            circles[i] = new Circle();
            circles[i].setFill(Color.hsb(360.0 * i / NODE_COUNT, 1.0, 0.5));
            circles[i].setRadius(NODE_SIZE);
            canvas.getChildren().add(circles[i]);
            MouseControlUtil.makeDraggable(circles[i]);
            layout.setMass(circles[i], 0.5);
        }

        anchor = new Circle();
        anchor.setFill(Color.BLACK);
        anchor.setRadius(NODE_SIZE);
        canvas.getChildren().add(anchor);
        MouseControlUtil.makeDraggable(anchor);

        double radius = Math.min(WIDTH, HEIGHT) / 3;
        Spring radial = new Spring(radius, 10);
        Spring segment = new Spring(2 * radius * Math.sin(Math.PI / NODE_COUNT), 10);

        for (int i = 0; i < NODE_COUNT; i++) {
            Line line = new Line();
            line.setFill(Color.BLACK);
            line.setStroke(Color.BLACK);
            line.startXProperty().bind(circles[i].layoutXProperty().add(circles[i].translateXProperty()));
            line.startYProperty().bind(circles[i].layoutYProperty().add(circles[i].translateYProperty()));
            line.endXProperty().bind(circles[(i + 1) % NODE_COUNT].layoutXProperty().add(circles[(i + 1) % NODE_COUNT].translateXProperty()));
            line.endYProperty().bind(circles[(i + 1) % NODE_COUNT].layoutYProperty().add(circles[(i + 1) % NODE_COUNT].translateYProperty()));
            canvas.getChildren().add(line);
            line.toBack();

            line = new Line();
            line.setFill(Color.BLACK);
            line.setStroke(Color.BLACK);
            line.startXProperty().bind(circles[i].layoutXProperty().add(circles[i].translateXProperty()));
            line.startYProperty().bind(circles[i].layoutYProperty().add(circles[i].translateYProperty()));
            line.endXProperty().bind(anchor.layoutXProperty().add(anchor.translateXProperty()));
            line.endYProperty().bind(anchor.layoutYProperty().add(anchor.translateYProperty()));
            canvas.getChildren().add(line);
            line.toBack();

            layout.addConnection(circles[i], circles[(i + 1) % NODE_COUNT], segment);
            layout.addConnection(circles[i], anchor, radial);
            layout.setMass(circles[i], 0.5);
        }
        for (int i = 0; i < NODE_COUNT; i++) {
            for (int j = 2; j < NODE_COUNT; j++) {
                layout.addConnection(circles[i], circles[(i + j) % NODE_COUNT], new Spring(2 * radius * Math.sin(j * Math.PI / NODE_COUNT), 10));
            }
        }

        // This part stays put.
        layout.setMass(anchor, Double.POSITIVE_INFINITY);

        ((Button) menu.getItems().get(2)).setText("Randomize");
    }

    @Override
    public void reset() {
        anchor.setLayoutX(WIDTH * 0.5);
        anchor.setLayoutY(HEIGHT * 0.5);
        for (Circle circle : circles) {
            circle.setLayoutX(Math.random() * WIDTH);
            circle.setLayoutY(Math.random() * HEIGHT);
        }
    }

    @Override
    public String getTitle() {
        return "Springs";
    }

}
