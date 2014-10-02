package eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.panes;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.PhysLayout;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Box2DSpringSimulation;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Spring;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Tether;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.ListChangeListener;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;


/**
 * Use one node as the center and orient the other nodes around it in clockwise
 * order. This layout currently ignores its children's sizes.
 *
 * @author Christoph Burschka &lt;christoph@burschka.de&gt;
 */
public class WheelPane extends Pane implements PhysicalPane {

    public final ObjectProperty<Node> center;
    private final PhysLayout layout;
    private final Box2DSpringSimulation simulation;
    private double radius;
    private double strength = 50;
    private double spacing = 0;

    public WheelPane() {
        center = new CenterProperty();
        layout = new PhysLayout(this);
        simulation = new Box2DSpringSimulation(layout);
        radius = Math.min(this.getWidth() * 0.5, this.getHeight() * 0.5);
        simulation.setFriction(2);
    }

    @Override
    protected void layoutChildren() {
        simulation.stopSimulation();

        super.layoutChildren();
        final Node c = center.get();

        layout.clearAllMasses();
        layout.clearAllConnections();
        if (c != null) {
            layout.setMass(c, Double.POSITIVE_INFINITY);
        }

        final List<Node> managedChildren = getManagedChildren();

        // TODO: Try not to recreate this on each layout pass.
        final Node[] children = getManagedChildren().stream().filter(e -> {
            return e != c;
        }).toArray(size -> {
            return new Node[size];
        });

        double diags[] = new double[children.length];
        for (int i = 0; i < diags.length; i++) {
            final Bounds bounds = children[i].getBoundsInLocal();
            diags[i] = Math.pow(Math.pow(bounds.getHeight(), 2) + Math.pow(bounds.getWidth(), 2), 0.5);
        }

        /**
         * Calculate the preferred radius of this wheel. The maximum of the
         * following three values is taken: - the minimum radius (defaults to 0)
         * - the circumference required to fit all outside nodes in a circle. -
         * the diameter required to fit the largest two nodes in line with the
         * center node.
         */
        double m1 = 0, m2 = 0, s = 0;
        // Find the sum of all diagonals, and the largest two diagonals.
        for (double diag : diags) {
            s += diag;
            if (diag >= m2) {
                m1 = m2;
                m2 = diag;
            } else if (diag > m1) {
                m1 = diag;
            }
        }
        final Bounds bounds = c!=null?c.getBoundsInLocal():new BoundingBox(0, 0, getWidth()*0.5, getHeight()*0.5);
        final double centerDiag = Math.pow(Math.pow(bounds.getHeight(), 2) + Math.pow(bounds.getWidth(), 2), 0.5);
        final double diameter = m1 + m2 + centerDiag + 2 * spacing;
        final double circumference = s + children.length * spacing;
        final double r = Math.max(radius, Math.max(diameter * 0.5, circumference * 0.5 / Math.PI));

        /**
         * Connect the children by springs of the appropriate length. Every
         * (adjacent and non-adjacent) pair of surrounding nodes is connected by
         * a spring that matches the length of the chord between them.
         */
        for (int _i = 0; _i < children.length; _i++) {
            double d = spacing;
            for (int j = 1; j < children.length; j++) {
                // The arc in proportion to the calculated circumference is half the endpoints' sizes plus all the space between them:
                final double arcSection = (d + (diags[_i] + diags[(_i + j) % children.length]) * 0.5) / circumference;
                // chord length on the unit circle is twice the sine of half the angle:
                final double chordLength = 2 * r * Math.sin(arcSection * Math.PI);
                layout.addConnection(children[_i], children[(_i + j) % children.length], new Spring(chordLength, strength));
                d += diags[(_i + j) % children.length] + spacing;
            }
            if (c != null) {
                layout.addConnection(c, children[_i], new Spring(r, strength));
            } else {
                // Without a center node, fix nodes to the center of the pane instead.
                layout.addTether(children[_i], new Tether(r, strength, Point2D.ZERO));
            }
        }

        simulation.startSimulation();
    }

    public final void setCenter(Node value) {
        center.set(value);
    }

    public final Node getCenter() {
        return center.get();
    }

    @Override
    public Box2DSpringSimulation getSimulation() {
        return simulation;
    }

    @Override
    public void setStrength(double strength) {
        this.strength = strength;
        requestLayout();
    }

    @Override
    public double getStrength() {
        return strength;
    }

    /**
     * Inner class tracking the pane's central node (see
     * javafx.layout.scene.layout.BorderPane).
     */
    private final class CenterProperty extends ObjectPropertyBase<Node> {

        private boolean isBeingInvalidated;
        private Node oldValue = null;

        @Override
        public Object getBean() {
            return WheelPane.this;
        }

        @Override
        public String getName() {
            return "center";
        }

        CenterProperty() {
            // Unset the center property if the central node is removed from children.
            getChildren().addListener((ListChangeListener.Change<? extends Node> c) -> {
                if (oldValue != null && !isBeingInvalidated) {
                    while (c.next()) {
                        if (c.wasRemoved() && c.getRemoved().contains(oldValue)) {

                            oldValue = null;
                            set(null);
                        }
                    }
                }
            });
        }

        @Override
        protected void invalidated() {
            // Replace the node in the list of children.
            final List<Node> children = getChildren();

            isBeingInvalidated = true;
            try {
                if (oldValue != null) {
                    children.remove(oldValue);
                }

                final Node value = get();
                this.oldValue = value;

                if (value != null) {
                    children.add(value);
                }
            } finally {
                isBeingInvalidated = false;
            }
        }
    }

    public void setMinRadius(double radius) {
        this.radius = radius;
        requestLayout();
    }

    public void setSpacing(double spacing) {
        this.spacing = spacing;
        requestLayout();
    }

}
