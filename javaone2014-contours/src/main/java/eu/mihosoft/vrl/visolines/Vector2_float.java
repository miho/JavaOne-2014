package eu.mihosoft.vrl.visolines;

class Vector2_float {

    private static Vector2_double dVec;
    private static Vector2_float floatVec;
    private static Vector2_int intVec;
    private static Vector2_long longVec;

    public float x;
    public float y;

    public static final Vector2_float ZERO = new Vector2_float(0, 0);
    public static final Vector2_float UNITY = new Vector2_float(1, 1);
    public static final Vector2_float X_ONE = new Vector2_float(1, 0);
    public static final Vector2_float Y_ONE = new Vector2_float(0, 1);

            public Vector2_float() {

        this.x = 0;
        this.y = 0;
    }
    
        public Vector2_float(float x, float y) {

        this.x = x;
        this.y = y;
    }


    @Override
    public Vector2_float clone() {
        return new Vector2_float(x, y);
    }

        public Vector2_float negated() {
        return new Vector2_float(-x, -y);
    }

        public Vector2_float plus(Vector2_float v) {
        return new Vector2_float(x + v.x, y + v.y);
    }

        public Vector2_float minus(Vector2_float v) {
        return new Vector2_float(x - v.x, y - v.y);
    }

        public Vector2_float times(float a) {
        return new Vector2_float(x * a, y * a);
    }

        public Vector2_float times(Vector2_float a) {
        return new Vector2_float(x * a.x, y * a.y);
    }

        public Vector2_float dividedBy(float a) {
        return new Vector2_float(x / a, y / a);
    }

        public float dot(Vector2_float a) {
        return this.x * a.x + this.y * a.y;
    }

        public Vector2_float lerp(Vector2_float a, float t) {
        return this.plus(a.minus(this).times(t));
    }

        public float magnitude() {
        return (float)Math.sqrt(this.dot(this));
    }

        public Vector2_float normalized() {
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
        final Vector2_float other = (Vector2_float) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y !=other.y) {
            return false;
        }

        return true;
    }

        public static Vector2_float x(float x) {
        return new Vector2_float(x, 0);
    }

        public static Vector2_float y(float y) {
        return new Vector2_float(0, y);
    }


}
