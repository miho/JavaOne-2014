package eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout.panes;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Box2DSpringSimulation;


/**
 *
 * @author christoph
 */


public interface PhysicalPane {
    public abstract Box2DSpringSimulation getSimulation();
    public abstract void setStrength(double strength);
    public abstract double getStrength();
}
