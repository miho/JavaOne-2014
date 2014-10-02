package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.shapes;

import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Circle;
import org.jbox2d.collision.shapes.CircleShape;

/**
 * A JBox2D circle shape that tracks the radius of a JavaFX circle node.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class NodeCircleShape extends CircleShape {

    public NodeCircleShape(Circle node) {
        this.m_radius = (float) node.getRadius();
        node.radiusProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            this.m_radius = newValue.floatValue();
        });
    }
}
