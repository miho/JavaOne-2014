/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.v3d.contours;

import eu.mihosoft.vrl.visolines.Data_float;
import eu.mihosoft.vrl.visolines.MarchingSquares_float;
import eu.mihosoft.vrl.visolines.Path_float;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class MetaCircleWorld {

    private MarchingSquares_float ms;
    private final List<MetaCircle> metaCircles = new ArrayList<>();
    private Data_float forceField;
    private double scaleX = 1.0;
    private double scaleY = 1.0;

    public MetaCircleWorld(int width, int height, double scaleX, double scaleY) {
        forceField = new Data_float(width, height);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        ms = new MarchingSquares_float(forceField);
    }

    public List<Path_float> update() {

        for (int y = 0; y < forceField.getHeight(); y++) {
            for (int x = 0; x < forceField.getWidth(); x++) {
                forceField.set(0, x, y);
                for (int i = 0; i < metaCircles.size(); i++) {
                    float val = (float) metaCircles.get(i).getStrengthAt(x, y);
                    forceField.plus(val, x, y);
                }
            }
        }
        
        List<Path_float> paths = new ArrayList<>();
        float dt = 0.125f;
        for(int i = 0; i < 5;i++) {
            paths.add(ms.computePaths((i+1)*dt));
        }
        
        return paths;
    }

    public void resize(int width, int height, double scaleX, double scaleY) {
        forceField = new Data_float(width, height);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        ms = new MarchingSquares_float(forceField);
    }

    public void addMetaCircle(MetaCircle mc) {
        metaCircles.add(mc);
    }

    public boolean removeMetaCircle(MetaCircle mc) {
        return metaCircles.remove(mc);
    }
}
