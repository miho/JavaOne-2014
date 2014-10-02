package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.shapes;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

/**
 * A JBox2D circle shape that tracks the vertices of a JavaFX polygon node.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class NodeBoxShape extends PolygonShape {

    private final Node node;

    public NodeBoxShape(Node node) {
        this.node = node;
        this.set(convertPoints(), 4);
        node.boundsInLocalProperty().addListener((change) -> {
            this.set(convertPoints(), 4);
        });
    }

    private Vec2[] convertPoints() {
        Bounds bounds = node.getBoundsInLocal();
        return new Vec2[]{
            new Vec2((float) bounds.getMinX(), (float) bounds.getMinY()),
            new Vec2((float) bounds.getMaxX(), (float) bounds.getMinY()),
            new Vec2((float) bounds.getMaxX(), (float) bounds.getMaxY()),
            new Vec2((float) bounds.getMinX(), (float) bounds.getMaxY())
        };
    }
}
