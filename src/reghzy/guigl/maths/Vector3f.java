package reghzy.guigl.maths;

import reghzy.guigl.render.utils.RingBuffer;
import reghzy.guigl.utils.HashHelper;

/**
 * Vector3f is used for 3D OpenGL graphics
 */
public class Vector3f implements Cloneable {
    private static final RingBuffer<Vector3f> POOL = new RingBuffer<Vector3f>(new Vector3f[16384]);

    static {
        for (int i = 0, len = POOL.size(); i < len; i++) {
            POOL.set(i, new Vector3f());
        }
    }

    public float x;
    public float y;
    public float z;

    public static Vector3f ONE = new Vector3f(1, 1, 1);
    public static Vector3f ZERO = new Vector3f(0, 0, 0);
    public static Vector3f UNIT_X = new Vector3f(1, 0, 0);
    public static Vector3f UNIT_Y = new Vector3f(0, 1, 0);
    public static Vector3f UNIT_z = new Vector3f(0, 0, 1);
    public static Vector3f UP = new Vector3f(0, 1, 0);
    public static Vector3f DOWN = new Vector3f(0, -1, 0);
    public static Vector3f LEFT = new Vector3f(1, 0, 0);
    public static Vector3f RIGHT = new Vector3f(-1, 0, 0);
    public static Vector3f BACK = new Vector3f(0, 0, 1);
    public static Vector3f FRONT = new Vector3f(0, 0, -1);

    public Vector3f() {
        set(0, 0, 0);
    }

    public Vector3f(float x, float y, float z) {
        set(x, y, z);
    }

    public static Vector3f get() {
        return POOL.get();
    }

    public static Vector3f get(float all) {
        return POOL.get().set(all, all, all);
    }

    public static Vector3f get(float x, float y, float z) {
        return POOL.get().set(x, y, z);
    }

    public static Vector3f get(double x, double y, double z) {
        return POOL.get().set((float) x, (float) y, (float) z);
    }

    public static Vector3f get(double all) {
        float a = (float) all;
        return POOL.get().set(a, a, a);
    }

    public static Vector3f get(Vector2d vec2d, double z) {
        return POOL.get().set((float) vec2d.x, (float) vec2d.y, (float) z);
    }

    public static Vector3f get(Vector2d vec2d, float z) {
        return POOL.get().set((float) vec2d.x, (float) vec2d.y, z);
    }

    /**
     * sets this instances values to the given values and then returns this instance, not a copy
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * sets this instances values to the given vector's values and then returns this instance, not a copy
     * @param v
     * @return
     */
    public Vector3f set(Vector3f v) {
        this.set(v.x, v.y, v.z);
        return this;
    }

    public Vector3f setX(float x) {
        this.x = x;
        return this;
    }

    public Vector3f setY(float y) {
        this.y = y;
        return this;
    }

    public Vector3f setZ(float z) {
        this.z = z;
        return this;
    }

    public Vector3f set(float all) {
        this.x = all;
        this.y = all;
        this.z = all;
        return this;
    }

    /**
     * multiplies this instances values by the given values and then returns this instance, not a copy
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3f multiply(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    /**
     * multiplies this instances values by the given vector's values and then returns this instance, not a copy
     * @param v
     * @return
     */
    public Vector3f multiply(Vector3f v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }

    public Vector3f multiply(float all) {
        this.x *= all;
        this.y *= all;
        this.z *= all;
        return this;
    }

    /**
     * adds this instances values by the given values and then returns this instance, not a copy
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3f add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * adds this instances values by the given vector's values and then returns this instance, not a copy
     * @param v
     * @return
     */
    public Vector3f add(Vector3f v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    /**
     * subtracts this instances values by the given values and then returns this instance, not a copy
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3f subtract(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * subtracts this instances vaalues by the given vector's values and then returns this instance, not a copy
     * @param v
     * @return
     */
    public Vector3f subtract(Vector3f v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    public Vector3f makeNegative() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    /**
     * returns the squared magnitude of the x, y and z values
     * @return
     */
    public float magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * returns the magnitude of the x, y and z values
     * @return
     */
    public float magnitude() {
        return (float) Math.sqrt(magnitudeSquared());
    }

    /**
     * returns a normalised copy of this vector, where the x, y and z values are never smaller than 0 or bigger than 1
     * @return
     */
    public Vector3f normalised() {
        float mag = magnitude();
        if (mag == 0)
            return new Vector3f(0, 0, 0);
        return new Vector3f(this.x / mag, this.y / mag, this.z / mag);
    }

    /**
     * returns the dot produce between this vector and another vector's value
     * @param v
     * @return
     */
    public float dot(Vector3f v) {
        return this.x * v.x +
               this.y * v.y +
               this.z * v.z;
    }

    /**
     * returns a new vector which is the cross product of this vector and another vector's values
     * @param v
     * @return
     */
    public Vector3f cross(Vector3f v) {
        return new Vector3f(
                this.y * v.z - this.z * this.y,
                this.z * v.x - this.x * this.z,
                this.x * v.y - this.y * this.x);
    }

    /**
     * returns the angle between this vector and another vector
     * @param v
     * @return
     */
    public float angle(Vector3f v) {
        return (float) Math.acos(this.normalised().dot(v));
    }

    /**
     * returns whether this vector's x, y and z values are between -1 and 1
     * @return
     */
    public boolean isUnit() {
        return x >= -1.0f && x <= 1.0f &&
               y >= -1.0f && y <= 1.0f &&
               z >= -1.0f && z <= 1.0f;
    }

    /**
     * Clamps any negative number to 0, leaves positive numbers
     * as they are, and returns this vector instance (not a copy)
     * @return
     */
    public Vector3f clampPositive() {
        if (this.x < 0)
            this.x = 0;
        if (this.y < 0)
            this.y = 0;
        if (this.z < 0)
            this.z = 0;
        return this;
    }

    /**
     * If the X/Y/Z value is 0, it becomes 1. otherwise, if it's bigger than 0, it becomes 0
     * @return
     */
    public Vector3f invertUnit() {
        if (this.x == 0) {
            this.x = 1;
        }
        else {
            this.x = 0;
        }
        if (this.y == 0) {
            this.y = 1;
        }
        else {
            this.y = 0;
        }
        if (this.z == 0) {
            this.z = 1;
        }
        else {
            this.z = 0;
        }
        return this;
    }

    /**
     * returns a vector containing the values of a multiplication between the given matrix and the given vector
     * @param m
     * @param v
     * @return
     */
    public static Vector3f multiplyDirection(Matrix4x4 m, Vector3f v) {
        return new Vector3f(m.m[0] * v.x + m.m[1] * v.y + m.m[2] * v.z,
                           m.m[4] * v.x + m.m[5] * v.y + m.m[6] * v.z,
                           m.m[8] * v.x + m.m[9] * v.y + m.m[10] * v.z);
    }

    /**
     * returns a clone of this vector, containing the same x, y and z values
     * @return
     */
    @Override
    public Vector3f clone() {
        try {
            return (Vector3f) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public Vector3f copy() {
        return new Vector3f(this.x, this.y, this.z);
    }

    /**
     * returns a hashcode of the x, y and z values all into one. this hashcode only has a precision of x, y and z values up to 1023
     * @return
     */
    @Override
    public int hashCode() {
        return HashHelper.getHash3f(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof Vector3f) {
            Vector3f v = (Vector3f) obj;
            return v.x == this.x &&
                   v.y == this.y &&
                   v.z == this.z;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Vector3{" + this.x + "," + this.y + "," + this.z + "}";
    }
}
