package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.shapes;

import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.scene.shape.Polygon;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

/**
 * A JBox2D circle shape that tracks the vertices of a JavaFX polygon node.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class NodePolygonShape extends PolygonShape {

    private final Polygon node;

    public NodePolygonShape(Polygon node) {
        this.node = node;
        this.set(convertPoints(node.getPoints()), node.getPoints().size());
        node.getPoints().addListener((ListChangeListener.Change<? extends Double> c) -> {
            this.set(convertPoints(node.getPoints()), node.getPoints().size());
        });
    }

    private static Vec2[] convertPoints(List<Double> points) {
        Vec2[] vertices = new Vec2[points.size() / 2];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vec2(points.get(2 * i).floatValue(), points.get(2 * i + 1).floatValue());
        }
        return vertices;
    }
}
