package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics;

import java.util.Objects;
import javafx.geometry.Point2D;

/**
 * An idealized mechanical spring.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class Spring {

    private final double length;
    private final double strength;
    private final Point2D a, b;

    /**
     * Create a new spring.
     *
     * The spring is anchored at a specific point relative to the origin.
     *
     * @param length The equilibrium length.
     * @param strength The stiffness constant k.
     * @param a anchor point on a
     * @param b anchor point on b
     */
    public Spring(double length, double strength, Point2D a, Point2D b) {
        assert length > 0;
        assert strength > 0;
        this.length = length;
        this.strength = strength;
        this.a = a;
        this.b = b;
    }

    public Spring(double length, double strength) {
        this(length, strength, new Point2D(0, 0), new Point2D(0, 0));
    }

    /**
     * Calculate the spring's currently exerted force.
     *
     * @param distance The current (positive) length l of the spring.
     * @return The force (positive force is directed inward to the other point).
     */
    private double getForce(double distance) {
        return (distance - length) * strength;
    }

    /**
     * Calculate the spring's currently exerted force.
     *
     * If the spring is infinitely compressed, the direction of the force is
     * undefined. The return value will be a zero vector.
     *
     * @param a the first point
     * @param b the second point
     * @return the force acting on the first point (flip sign for second)
     */
    public Point2D getForce(Point2D a, Point2D b) {
        Point2D relative = b.add(this.b).subtract(a.add(this.a));
        double distance = relative.magnitude();
        if (distance > 0) {
            return relative.multiply(getForce(distance) / distance);
        }
        else {
            // Apply force in a random direction:
            double force = getForce(0);
            double angle = Math.random() * 2 * Math.PI;
            return new Point2D(Math.sin(angle) * force, Math.cos(angle)*force);
        }
    }

    public Spring reverse() {
        return new Spring(length, strength, b, a);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Spring other = (Spring) obj;
        if (Double.doubleToLongBits(this.length) != Double.doubleToLongBits(other.length)) {
            return false;
        }
        if (Double.doubleToLongBits(this.strength) != Double.doubleToLongBits(other.strength)) {
            return false;
        }
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        return Objects.equals(this.b, other.b);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.length) ^ (Double.doubleToLongBits(this.length) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.strength) ^ (Double.doubleToLongBits(this.strength) >>> 32));
        hash = 53 * hash + Objects.hashCode(this.a);
        hash = 53 * hash + Objects.hashCode(this.b);
        return hash;
    }

}
