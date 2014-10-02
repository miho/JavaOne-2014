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
public class Vector2l extends Vector2_long {

    public Vector2l() {
    }

    public Vector2l(long x, long y) {
        super(x, y);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (int) (this.x ^ (this.x >>> 32));
        hash = 43 * hash + (int) (this.y ^ (this.y >>> 32));
        return hash;
    }

}
