package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.shapes;

import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import org.jbox2d.collision.shapes.Shape;

/**
 * Creating JBox2D shapes for JavaFX nodes.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class NodeShapeBuilder {

    /**
     * Attempt to determine the form of a node and create the appropriate JBox2D
     * collision shape.
     *
     * @param node a JavaFX node
     * @return a JBox2D shape
     */
    public static Shape createShape(Node node) {
        if (node instanceof Circle) {
            return new NodeCircleShape((Circle) node);
        }
        if (node instanceof Polygon) {
            return new NodePolygonShape((Polygon) node);
        }
        return new NodeBoxShape(node);
    }
}
