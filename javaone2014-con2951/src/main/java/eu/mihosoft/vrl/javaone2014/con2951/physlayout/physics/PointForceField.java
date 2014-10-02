package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics;

import javafx.geometry.Point2D;

/**
 * A field emanating from a single point location.
 *
 * The strength drops off quadratically with distance, and acts in the direction
 * of their difference.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class PointForceField extends ForceField {

    private final Point2D source;
    double strength;

    /**
     * Create a new point source at the specified location.
     *
     * A positive strength repulses all particles.
     *
     * @param source
     * @param strength
     */
    public PointForceField(Point2D source, double strength) {
        this.source = source;
        this.strength = strength;
    }

    @Override
    public Point2D force(Point2D location) {
        Point2D relative = location.subtract(source);
        double distance = relative.magnitude();
        if (distance > 0.01) {
            return relative.multiply((strength / (distance * Math.max(distance * distance, 0.05))));
        }
        else return Point2D.ZERO;
    }
}
