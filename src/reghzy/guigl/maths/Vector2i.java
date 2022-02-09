package reghzy.guigl.maths;

public class Vector2i {
    public int x;
    public int y;

    public Vector2i() {

    }

    public Vector2i(int all) {
        this.x = all;
        this.y = all;
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i add(int all) {
        this.x += all;
        this.y += all;
        return this;
    }

    public Vector2i add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2i add(Vector2i v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2i sub(int all) {
        this.x -= all;
        this.y -= all;
        return this;
    }

    public Vector2i sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2i sub(Vector2i v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2i multiply(int all) {
        this.x *= all;
        this.y *= all;
        return this;
    }

    public Vector2i multiply(int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2i multiply(Vector2i v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vector2i divide(int all) {
        this.x /= all;
        this.y /= all;
        return this;
    }

    public Vector2i divide(int x, int y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public Vector2i divide(Vector2i v) {
        this.x /= v.x;
        this.y /= v.y;
        return this;
    }

    public Vector2i set(int all) {
        this.x = all;
        this.y = all;
        return this;
    }

    public Vector2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2i set(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public Vector2i copy() {
        return new Vector2i(this.x, this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof Vector2i) {
            Vector2i v = (Vector2i) obj;
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
