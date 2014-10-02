package eu.mihosoft.vrl.javaone2014.con2951.physlayout.testing;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Spring;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import jfxtras.labs.util.event.MouseControlUtil;

/**
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class Oscillator extends Example {

    Circle a, b;

    public Oscillator() {
        a = new Circle(30, Color.RED);
        a.setLayoutX(WIDTH / 4);
        a.setLayoutY(HEIGHT / 2);

        b = new Circle(30, Color.BLUE);
        b.setLayoutX(3 * WIDTH / 4);
        b.setLayoutY(HEIGHT / 2);

        canvas.getChildren().add(a);
        canvas.getChildren().add(b);
        MouseControlUtil.makeDraggable(a);
        MouseControlUtil.makeDraggable(b);

        Line line = new Line();
        line.setStroke(Color.BLACK);
        line.startXProperty().bind(a.layoutXProperty().add(a.translateXProperty()));
        line.startYProperty().bind(a.layoutYProperty().add(a.translateYProperty()));
        line.endXProperty().bind(b.layoutXProperty().add(b.translateXProperty()));
        line.endYProperty().bind(b.layoutYProperty().add(b.translateYProperty()));
        canvas.getChildren().add(line);
        line.toBack();

        layout.addConnection(a, b, new Spring(WIDTH / 5, 100));
        getSimulation().setFriction(0.8);
    }

    @Override
    public void reset() {
        
        System.out.println("reset oscillator");
        
        a.setLayoutX(WIDTH / 4);
        a.setLayoutY(HEIGHT / 2);
        b.setLayoutX(3 * WIDTH / 4);
        b.setLayoutY(HEIGHT / 2);
        
        a.setTranslateX(0);
        a.setTranslateY(0);
        b.setTranslateX(0);
        b.setTranslateY(0);
    }

    @Override
    public String getTitle() {
        return "Oscillator";
    }
}
