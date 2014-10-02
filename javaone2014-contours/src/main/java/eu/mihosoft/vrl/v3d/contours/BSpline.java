
package eu.mihosoft.vrl.v3d.contours;

/*
 * Copyright (c) 2011 Christopher Collins, Josua Krause
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import eu.mihosoft.vrl.visolines.Vector2d;
import eu.mihosoft.vrl.visolines.Vector2f;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Based on BSplineGenerator class by Christopher Collins and Josua Krause
 * 
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class BSpline {

    // since the basic function is fixed this value should not be changed
    private static final int ORDER = 3;

    private static final int START_INDEX = ORDER - 1;

    private static final int REL_END = 1;

    private static final int REL_START = REL_END - ORDER;

    private int granularity = 6;

    /**
     * Whether the result of the set outlines are interpreted in clockwise
     * order.
     */
    private final boolean clockwise = true;

    /**
     * Sets the granularity.
     *
     * @param granularity The granularity is the number of line segments per
     * base point.
     */
    public void setGranularity(final int granularity) {
        this.granularity = granularity;
    }

    /**
     * Getter.
     *
     * @return The granularity is the number of line segments per base point.
     */
    public int getGranularity() {
        return granularity;
    }

    public List<Vector2d> convert(List<Vector2d> points, final boolean closed) {
        // covering special cases
        if (points.size() < 3) {
            return points;
        }
        // actual b-spline calculation
        final List<Vector2d> list = new ArrayList<>();
        final int count = points.size() + ORDER - 1;
        final float g = granularity;
        final Vector2d start = calcPoint(points, START_INDEX - (closed ? 0 : 2),
                0, closed);
        list.add(start);
        for (int i = START_INDEX - (closed ? 0 : 2); i < count
                + (closed ? 0 : 2); ++i) {
            for (int j = 1; j <= granularity; ++j) {
                list.add(calcPoint(points, i, j / g, closed));
            }
        }
        return list;
    }

    private static float basicFunction(final int i, final float t) {
        // the basis function for a cubic B spline
        switch (i) {
            case -2:
                return (((-t + 3) * t - 3) * t + 1) / 6;
            case -1:
                return (((3 * t - 6) * t) * t + 4) / 6;
            case 0:
                return (((-3 * t + 3) * t + 3) * t + 1) / 6;
            case 1:
                return (t * t * t) / 6;
            default:
                throw new InternalError();
        }
    }

    // evaluates a point on the B spline
    private Vector2d calcPoint(final List<Vector2d> points, final int i,
            final float t, final boolean closed) {
        float px = 0;
        float py = 0;
        for (int j = REL_START; j <= REL_END; j++) {
            final Vector2d p = points.get(closed ? getRelativeIndex(i, j,
                    points.size()) : Math.max(0,
                            Math.min(points.size() - 1, i + j)));
            final float bf = basicFunction(j, t);
            px += bf * p.x;
            py += bf * p.y;
        }
        return new Vector2d(px, py);
    }

    /**
     * Getter.
     *
     * @param index The index.
     * @param relIndex The difference.
     * @param len The length of the array.
     * @return The relative index.
     */
    protected final int getRelativeIndex(final int index, final int relIndex,
            final int len) {
        return bound(index + (clockwise ? relIndex : -relIndex), len);
    }

    /**
     * Ensures that an index lies within in the array.
     *
     * @param index The index.
     * @param len The length of the array.
     * @return The index that lies in the array.
     */
    protected static final int bound(final int index, final int len) {
        int i = index;
        i %= len;
        if (i < 0) {
            i += len;
        }
        return i;
    }

}
