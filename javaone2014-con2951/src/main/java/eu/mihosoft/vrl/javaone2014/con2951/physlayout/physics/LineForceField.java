package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics;

import javafx.geometry.Point2D;

/**
 * A cylindrical field projected by an infinite line.
 *
 * The field is perpendicular to the line, and drops off quadratically with
 * distance.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class LineForceField extends ForceField {

    private final Point2D location;
    private final Point2D direction;
    private final double strength;

    /**
     * Create a new field.
     *
     * @param location a single point along the line.
     * @param direction the direction of the line.
     * @param strength the strength of the field.
     */
    public LineForceField(Point2D location, Point2D direction, double strength) {
        // Normalize, so direction is a unit vector, and location is perpendicular.
        this.direction = direction.normalize();
        this.location = projection(location, this.direction);
        this.strength = strength;
    }

    /**
     * Create a new field.
     *
     * @param location a single point along the line.
     * @param angle the angle of the line, clockwise from (1,0).
     * @param strength the strength of the field.
     */
    public LineForceField(Point2D location, double angle, double strength) {
        this(location, new Point2D(Math.cos(angle), Math.sin(angle)), strength);
    }

    @Override
    public Point2D force(Point2D point) {
        Point2D relative = projection(point.subtract(location), direction);
        double distance = relative.magnitude();
        return relative.multiply((strength / (distance * distance * distance)));
    }
}
