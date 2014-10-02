package eu.mihosoft.vrl.visolines;

class Vector2_int {

    private static Vector2_double dVec;
    private static Vector2_float floatVec;
    private static Vector2_int intVec;
    private static Vector2_long longVec;

    public int x;
    public int y;

    public static final Vector2_int ZERO = new Vector2_int(0, 0);
    public static final Vector2_int UNITY = new Vector2_int(1, 1);
    public static final Vector2_int X_ONE = new Vector2_int(1, 0);
    public static final Vector2_int Y_ONE = new Vector2_int(0, 1);

            public Vector2_int() {

        this.x = 0;
        this.y = 0;
    }
    
        public Vector2_int(int x, int y) {

        this.x = x;
        this.y = y;
    }


    @Override
    public Vector2_int clone() {
        return new Vector2_int(x, y);
    }

        public Vector2_int negated() {
        return new Vector2_int(-x, -y);
    }

        public Vector2_int plus(Vector2_int v) {
        return new Vector2_int(x + v.x, y + v.y);
    }

        public Vector2_int minus(Vector2_int v) {
        return new Vector2_int(x - v.x, y - v.y);
    }

        public Vector2_int times(int a) {
        return new Vector2_int(x * a, y * a);
    }

        public Vector2_int times(Vector2_int a) {
        return new Vector2_int(x * a.x, y * a.y);
    }

        public Vector2_int dividedBy(int a) {
        return new Vector2_int(x / a, y / a);
    }

        public int dot(Vector2_int a) {
        return this.x * a.x + this.y * a.y;
    }

        public Vector2_int lerp(Vector2_int a, int t) {
        return this.plus(a.minus(this).times(t));
    }

        public int magnitude() {
        return (int)Math.sqrt(this.dot(this));
    }

        public Vector2_int normalized() {
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
        final Vector2_int other = (Vector2_int) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y !=other.y) {
            return false;
        }

        return true;
    }

        public static Vector2_int x(int x) {
        return new Vector2_int(x, 0);
    }

        public static Vector2_int y(int y) {
        return new Vector2_int(0, y);
    }


}
