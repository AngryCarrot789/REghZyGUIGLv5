package reghzy.guigl.math;

public class Vector2 {
    public double x;
    public double y;

    public Vector2() {

    }

    public Vector2(double all) {
        this.x = all;
        this.y = all;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(double all) {
        this.x += all;
        this.y += all;
        return this;
    }

    public Vector2 add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2 add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2 sub(double all) {
        this.x -= all;
        this.y -= all;
        return this;
    }

    public Vector2 sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2 sub(Vector2 v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2 multiply(double all) {
        this.x *= all;
        this.y *= all;
        return this;
    }

    public Vector2 multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2 multiply(Vector2 v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vector2 divide(double all) {
        this.x /= all;
        this.y /= all;
        return this;
    }

    public Vector2 divide(double x, double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public Vector2 divide(Vector2 v) {
        this.x /= v.x;
        this.y /= v.y;
        return this;
    }

    public Vector2 set(double all) {
        this.x = all;
        this.y = all;
        return this;
    }

    public Vector2 set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public Vector2 copy() {
        return new Vector2(this.x, this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof Vector2) {
            Vector2 v = (Vector2) obj;
            return v.x == this.x && v.y == this.y;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append('{').append(this.x).append(',').append(this.y).append('}').toString();
    }
}
