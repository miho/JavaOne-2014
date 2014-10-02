package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics;

import javafx.geometry.Point2D;

/**
 * A tether is a spring that connects a node to a fixed point instead of a
 * second node.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class Tether {

    private Spring spring;

    /**
     * Creates a tether of a particular length and strength, fixing the origin
     * of a node to a particular point.
     *
     * @param length
     * @param strength
     * @param anchor
     */
    public Tether(double length, double strength, Point2D anchor) {
        this(length, strength, anchor, new Point2D(0, 0));
    }

    /**
     * Creates a tether of a particular length and strength, fixing a point
     * relative to the origin of a node to another point.
     *
     * @param length
     * @param strength
     * @param anchor
     * @param fixture
     */
    public Tether(double length, double strength, Point2D anchor, Point2D fixture) {
        spring = new Spring(length, strength, anchor, fixture);
    }

    /**
     * Calculates the force acting on the node.
     *
     * This requires only the position of the tethered node, and considers the
     * anchor to be a node at the global origin.
     *
     * @param a
     * @return
     */
    public Point2D getForce(Point2D a) {
        return spring.getForce(a, new Point2D(0, 0));
    }
}
