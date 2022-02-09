package reghzy.guigl.maths;

import reghzy.guigl.utils.HashHelper;

public class Vector2f {
    public float x;
    public float y;

    public static Vector2f One = new Vector2f(1, 1);
    public static Vector2f Zero = new Vector2f(0 , 0);
    public static Vector2f UnitX = new Vector2f(1.0f, 0.0f);
    public static Vector2f UnitY = new Vector2f(0.0f, 1.0f);

    public Vector2f() { }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float magnitudeSquared() {
        return x * x + y * y;
    }

    public float magnitude() {
        return (float) Math.sqrt(magnitudeSquared());
    }

    public Vector2f normalised() {
        float mag = magnitude();
        return new Vector2f(this.x / mag, this.y / mag);
    }

    public boolean isUnit() {
        return x >= -1.0f && x <= 1.0f && y >= -1.0f && y <= 1.0f;
    }

    @Override
    protected Object clone() {
        return new Vector2f(this.x, this.y);
    }

    @Override
    public int hashCode() {
        return HashHelper.getHash2f(this.x, this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2f)) {
            return false;
        }
        Vector2f vector2F = (Vector2f) obj;
        return vector2F.x == this.x && vector2F.y == this.y;
    }

    @Override
    public String toString() {
        return "Vector2{" + this.x + "," + this.y + "}";
    }
}
