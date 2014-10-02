package eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.PointForceField;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jfxtras.labs.util.event.MouseControlUtil;

/**
 * There are four circular force-fields in this simulation that pull on a single
 * ball. Whenever the ball approaches one of them, it will be drawn into a fixed
 * position.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class Fields extends Example {

    Circle ball;

    public Fields() {
        ball = new Circle(50, Color.RED);
        layout.addField(
                new PointForceField(new Point2D(WIDTH / 4, HEIGHT / 4), -5e7),
                new PointForceField(new Point2D(3 * WIDTH / 4, HEIGHT / 4), -5e7),
                new PointForceField(new Point2D(3 * WIDTH / 4, 3 * HEIGHT / 4), -5e7),
                new PointForceField(new Point2D(WIDTH / 4, 3 * HEIGHT / 4), -5e7)
        );
        canvas.getChildren().add(ball);
        canvas.getChildren().addAll(
                new Circle(WIDTH / 4, HEIGHT / 4, 10, Color.BLUE),
                new Circle(3 * WIDTH / 4, HEIGHT / 4, 10, Color.BLUE),
                new Circle(3 * WIDTH / 4, 3 * HEIGHT / 4, 10, Color.BLUE),
                new Circle(WIDTH / 4, 3 * HEIGHT / 4, 10, Color.BLUE)
        );
        layout.addNode(ball);
        ball.setLayoutX(WIDTH/2);
        ball.setLayoutY(HEIGHT/2);
        MouseControlUtil.makeDraggable(ball);
    }

    @Override
    public void reset() {
        ball.setTranslateX(0);
        ball.setTranslateY(0);
    }

    @Override
    public String getTitle() {
        return "Force fields";
    }
}
