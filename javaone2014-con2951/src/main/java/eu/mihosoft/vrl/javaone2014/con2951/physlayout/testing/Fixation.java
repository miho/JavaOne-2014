package eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Spring;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import jfxtras.labs.util.event.MouseControlUtil;


/**
 * This example anchors one node to another with many springs attached to it at
 * different points.
 * As a result, the two nodes will not only maintain a particular distance, but
 * also a particular relative angle.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class Fixation extends Example {

    Circle anchor, pendulum;
    List<Line> lines;
    public static final int CONN = 50;

    public Fixation() {
        super();
        anchor = new Circle(50, Color.RED);
        pendulum = new Circle(50, Color.GREEN);
        canvas.getChildren().addAll(anchor, pendulum);
        lines = new ArrayList<>();
        layout.setMass(anchor, Double.POSITIVE_INFINITY);
        MouseControlUtil.makeDraggable(anchor);
        MouseControlUtil.makeDraggable(pendulum);
        Button reconnect = new Button("Reconnect");
        menu.getItems().add(reconnect);
        reconnect.setOnAction((ActionEvent event) -> {
            reconnect();
        });
        getSimulation().setFriction(5);
    }

    private void reconnect() {
        layout.clearConnections(anchor, pendulum);
        lines.stream().forEach((Line l) -> {
            canvas.getChildren().remove(l);
        });
        lines = new ArrayList<>();
        Spring[] s = new Spring[CONN];
        for (int i = 0; i < CONN; i++) {
            Point2D a = new Point2D(Math.random() * 100 - 50, Math.random() * 100 - 50);
            Point2D b = new Point2D(Math.random() * 100 - 50, Math.random() * 100 - 50);
            double length = new Point2D(anchor.getLayoutX(), anchor.getLayoutY()).add(a)
                    .distance(new Point2D(pendulum.getLayoutX(), pendulum.getLayoutY()).add(b));
            s[i] = new Spring(length, 100, a, b);
            Line line = new Line();
            line.startXProperty().bind(anchor.layoutXProperty().add(anchor.translateXProperty()).add(a.getX()));
            line.startYProperty().bind(anchor.layoutYProperty().add(anchor.translateYProperty()).add(a.getY()));
            line.endXProperty().bind(pendulum.layoutXProperty().add(pendulum.translateXProperty()).add(b.getX()));
            line.endYProperty().bind(pendulum.layoutYProperty().add(pendulum.translateYProperty()).add(b.getY()));
            lines.add(line);
            canvas.getChildren().add(line);
            line.toBack();
        }
        layout.addConnection(anchor, pendulum, s);
    }

    @Override
    public void reset() {
        anchor.setLayoutX(WIDTH / 3);
        anchor.setLayoutY(HEIGHT / 2);
        pendulum.setLayoutX(2 * WIDTH / 3);
        pendulum.setLayoutY(HEIGHT / 2);
        reconnect();
    }

    @Override
    public String getTitle() {
        return "Relative Fixation";
    }
}
