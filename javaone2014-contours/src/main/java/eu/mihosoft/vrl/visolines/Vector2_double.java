package eu.mihosoft.vrl.visolines;

class Vector2_double {

    private static Vector2_double dVec;
    private static Vector2_float floatVec;
    private static Vector2_int intVec;
    private static Vector2_long longVec;

    public double x;
    public double y;

    public static final Vector2_double ZERO = new Vector2_double(0, 0);
    public static final Vector2_double UNITY = new Vector2_double(1, 1);
    public static final Vector2_double X_ONE = new Vector2_double(1, 0);
    public static final Vector2_double Y_ONE = new Vector2_double(0, 1);

            public Vector2_double() {

        this.x = 0;
        this.y = 0;
    }
    
        public Vector2_double(double x, double y) {

        this.x = x;
        this.y = y;
    }


    @Override
    public Vector2_double clone() {
        return new Vector2_double(x, y);
    }

        public Vector2_double negated() {
        return new Vector2_double(-x, -y);
    }

        public Vector2_double plus(Vector2_double v) {
        return new Vector2_double(x + v.x, y + v.y);
    }

        public Vector2_double minus(Vector2_double v) {
        return new Vector2_double(x - v.x, y - v.y);
    }

        public Vector2_double times(double a) {
        return new Vector2_double(x * a, y * a);
    }

        public Vector2_double times(Vector2_double a) {
        return new Vector2_double(x * a.x, y * a.y);
    }

        public Vector2_double dividedBy(double a) {
        return new Vector2_double(x / a, y / a);
    }

        public double dot(Vector2_double a) {
        return this.x * a.x + this.y * a.y;
    }

        public Vector2_double lerp(Vector2_double a, double t) {
        return this.plus(a.minus(this).times(t));
    }

        public double magnitude() {
        return (double)Math.sqrt(this.dot(this));
    }

        public Vector2_double normalized() {
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
        final Vector2_double other = (Vector2_double) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y !=other.y) {
            return false;
        }

        return true;
    }

        public static Vector2_double x(double x) {
        return new Vector2_double(x, 0);
    }

        public static Vector2_double y(double y) {
        return new Vector2_double(0, y);
    }


}
