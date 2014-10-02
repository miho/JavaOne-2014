package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics;

import javafx.geometry.Point2D;

/**
 * A uniform force field projected by an infinite plane.
 *
 * Assuming the plane is perpendicular to the X-Y plane, the force is
 * perpendicular to the intersection, and remains constant on each side of the
 * intersection.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class UniformForceField extends ForceField {

    private final Point2D location;
    private final Point2D intersection;
    private final double strength;

    /**
     * Create a new field.
     *
     * @param location a point on the intersection with X-Y.
     * @param normal the normal vector to the plane.
     * @param strength the strength of the field.

     */
    public UniformForceField(Point2D location, Point2D normal, double strength) {
        this.location = location;
        this.strength = strength;
        normal.normalize();

        this.intersection = new Point2D(normal.getY(), normal.getX());
        projection(location, intersection);
    }

    /**
     * Create a new field.
     *
     * @param location a point on the intersection with X-Y.
     * @param angle the angle of the normal vector, clockwise from (1,0)
     * @param strength the strength of the field.

     */
    public UniformForceField(Point2D location, double angle, double strength) {
        this(location, location.add(new Point2D(Math.cos(angle), Math.sin(angle))), strength);
    }

    @Override
    public Point2D force(Point2D point) {
        return projection(
                point.subtract(location).normalize(), intersection
        ).multiply(strength);
    }
}
