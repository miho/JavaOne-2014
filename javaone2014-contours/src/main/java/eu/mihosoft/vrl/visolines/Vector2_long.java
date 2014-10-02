package eu.mihosoft.vrl.visolines;

class Vector2_long {

    private static Vector2_double dVec;
    private static Vector2_float floatVec;
    private static Vector2_int intVec;
    private static Vector2_long longVec;

    public long x;
    public long y;

    public static final Vector2_long ZERO = new Vector2_long(0, 0);
    public static final Vector2_long UNITY = new Vector2_long(1, 1);
    public static final Vector2_long X_ONE = new Vector2_long(1, 0);
    public static final Vector2_long Y_ONE = new Vector2_long(0, 1);

            public Vector2_long() {

        this.x = 0;
        this.y = 0;
    }
    
        public Vector2_long(long x, long y) {

        this.x = x;
        this.y = y;
    }


    @Override
    public Vector2_long clone() {
        return new Vector2_long(x, y);
    }

        public Vector2_long negated() {
        return new Vector2_long(-x, -y);
    }

        public Vector2_long plus(Vector2_long v) {
        return new Vector2_long(x + v.x, y + v.y);
    }

        public Vector2_long minus(Vector2_long v) {
        return new Vector2_long(x - v.x, y - v.y);
    }

        public Vector2_long times(long a) {
        return new Vector2_long(x * a, y * a);
    }

        public Vector2_long times(Vector2_long a) {
        return new Vector2_long(x * a.x, y * a.y);
    }

        public Vector2_long dividedBy(long a) {
        return new Vector2_long(x / a, y / a);
    }

        public long dot(Vector2_long a) {
        return this.x * a.x + this.y * a.y;
    }

        public Vector2_long lerp(Vector2_long a, long t) {
        return this.plus(a.minus(this).times(t));
    }

        public long magnitude() {
        return (long)Math.sqrt(this.dot(this));
    }

        public Vector2_long normalized() {
        return this.dividedBy(this.magnitude());
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y+"]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector2_long other = (Vector2_long) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y !=other.y) {
            return false;
        }

        return true;
    }

        public static Vector2_long x(long x) {
        return new Vector2_long(x, 0);
    }

        public static Vector2_long y(long y) {
        return new Vector2_long(0, y);
    }


}
