package eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.shapes;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;

/**
 * Provides a zero-dimensional shape that tries to behave like a point mass.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class ShapelessShape extends Shape {

    private final double mass;

    public ShapelessShape(double mass) {
        super(ShapeType.CIRCLE);
        this.mass = mass;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public boolean testPoint(Transform xf, Vec2 p) {
        return false;
    }

    @Override
    public boolean raycast(RayCastOutput output, RayCastInput input, Transform transform, int childIndex) {
        return false;
    }

    @Override
    public void computeAABB(AABB aabb, Transform xf, int childIndex) {
        aabb.lowerBound.setZero();
        aabb.upperBound.setZero();
    }

    @Override
    public void computeMass(MassData massData, float density) {
        massData.I = 0f;
        massData.center.setZero();
        massData.mass = (float) mass;
    }

    @Override
    public Shape clone() {
        return new ShapelessShape((mass));
    }
}
