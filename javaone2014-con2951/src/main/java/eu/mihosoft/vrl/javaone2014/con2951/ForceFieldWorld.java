/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2014.con2951;

import eu.mihosoft.vrl.v3d.contours.MetaCircle;
import eu.mihosoft.vrl.visolines.Data_byte;
import eu.mihosoft.vrl.visolines.Data_float;
import eu.mihosoft.vrl.visolines.MarchingSquares_float;
import eu.mihosoft.vrl.visolines.Path_float;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class ForceFieldWorld {

    private final List<MetaCircle> metaCircles = new ArrayList<>();
    private final Data_byte forceField;

    public ForceFieldWorld(int width, int height, double scaleX, double scaleY) {
        forceField = new Data_byte(width, height);
    }

    public void update(float scaleX, float scaleY, float offsetX, float offsetY) {
        for (int y = 0; y < forceField.getHeight(); y++) {
            for (int x = 0; x < forceField.getWidth(); x++) {
                forceField.set((byte) 0, x, y);
                for (int i = 0; i < metaCircles.size(); i++) {
                    byte val = (byte) Math.min((100*metaCircles.get(i).getStrengthAt(x*scaleX-offsetX, y * scaleY-offsetY)),255);
                    forceField.plus(val, x, y);
                }
            }
        }
    }
    
    public Data_byte getData() {
        return forceField;
    }

    public void addMetaCircle(MetaCircle mc) {
        metaCircles.add(mc);
    }

    public boolean removeMetaCircle(MetaCircle mc) {
        return metaCircles.remove(mc);
    }
}
