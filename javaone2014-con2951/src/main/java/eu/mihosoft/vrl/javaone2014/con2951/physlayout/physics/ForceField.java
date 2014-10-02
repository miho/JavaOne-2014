package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics;

import javafx.geometry.Point2D;

/**
 * Implements a field that applies a force on all particles.
 *
 * The field acts on all particles equally, either repulsing or attracting.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public abstract class ForceField {

    public abstract Point2D force(Point2D location);

    /**
     * Project a 2D vector onto another.
     *
     * Takes two vectors a and b and calculate a = b*l + c, where l is scalar
     * and c is perpendicular to b.
     *
     * @param source the projection source
     * @param target the projection target
     * @return the component of the source that is perpendicular to the target.
     */
    public static Point2D projection(Point2D source, Point2D target) {
        return source.subtract(target.multiply(source.dotProduct(target)));
    }
}
