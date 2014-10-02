///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
package eu.mihosoft.vrl.v3d.contours;

import eu.mihosoft.vrl.visolines.Vector2f;


public class MetaCircle {

    private Vector2f pos = new Vector2f();

    private double strength = 3;

    public Vector2f position() {
        return pos;
    }

    public void setStrength(double pStrength) {
        strength = pStrength;
    }

    public double getStrength() {
        return strength;
    }

    public double getStrengthAt(float x, float y) {
        float dx;
        float dy;
        dx = pos.x - x;
        dy = pos.y - y;
        return strength / (dx * dx + dy * dy);
    }
}