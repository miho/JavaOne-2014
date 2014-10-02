package eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.panes;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.PhysLayout;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Box2DSpringSimulation;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Spring;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Tether;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.VBox;


/**
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class PhysicalVBox extends VBox implements PhysicalPane {

    private final PhysLayout layout;
    private double strength = 50;
    private final Box2DSpringSimulation simulation;

    public PhysicalVBox() {
        layout = new PhysLayout(this);
        simulation = new Box2DSpringSimulation(layout);
        initialize();
    }

    public PhysicalVBox(Node... children) {
        super(children);
        layout = new PhysLayout(this);
        simulation = new Box2DSpringSimulation(layout);
        initialize();
    }

    public PhysicalVBox(double spacing) {
        super(spacing);
        layout = new PhysLayout(this);
        simulation = new Box2DSpringSimulation(layout);
        initialize();
    }

    public PhysicalVBox(double spacing, Node... children) {
        super(spacing, children);
        layout = new PhysLayout(this);
        simulation = new Box2DSpringSimulation(layout);
        initialize();
    }

    private void initialize() {
        simulation.setFriction(2);
    }

    @Override
    protected void layoutChildren() {
        simulation.stopSimulation();

        List<Node> managedChildren = getManagedChildren();
        int n = managedChildren.size();

        // Store the old positions.
        Point2D[] positions = new Point2D[n];
        for (int i = 0; i < n; i++) {
            Node child = managedChildren.get(i);
            positions[i] = child.localToParent(Point2D.ZERO);
        }

        // Perform the layout.
        super.layoutChildren();

        // Determine the new positions, and translate the nodes to their old positions.
        for (int i = 0; i < n; i++) {
            Node child = managedChildren.get(i);
            Point2D newPosition = new Point2D(child.getLayoutX(), child.getLayoutY());
            child.setTranslateX(positions[i].getX() - newPosition.getX());
            child.setTranslateY(positions[i].getY() - newPosition.getY());
            positions[i] = newPosition;
        }

        // Reconnect the nodes.
        layout.clearAllConnections();
        layout.clearAllTethers();

        for (int i = 0; i < n; i++) {
            layout.addTether(managedChildren.get(i), new Tether(0, strength, positions[i]));
            for (int j = 0; j < i; j++) {
                double distance = positions[i].distance(positions[j]);
                layout.addConnection(managedChildren.get(i), managedChildren.get(j), new Spring(distance, strength));
            }
        }

        simulation.startSimulation();
    }

    @Override
    public Box2DSpringSimulation getSimulation() {
        return simulation;
    }

    @Override
    public void setStrength(double strength) {
        this.strength = strength;
        this.requestLayout();
    }

    @Override
    public double getStrength() {
        return strength;
    }
}
