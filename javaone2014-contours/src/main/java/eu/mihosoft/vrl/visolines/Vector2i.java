/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.visolines;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Vector2i extends Vector2_int {

    public Vector2i() {
    }

    public Vector2i(int x, int y) {
        super(x, y);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.x;
        hash = 19 * hash + this.y;
        return hash;
    }

}
