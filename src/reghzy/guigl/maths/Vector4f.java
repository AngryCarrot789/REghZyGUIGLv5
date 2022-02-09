package reghzy.guigl.maths;

import reghzy.guigl.render.utils.RingBuffer;
import reghzy.guigl.utils.HashHelper;

public class Vector4f {
    private static final RingBuffer<Vector4f> POOL = new RingBuffer<Vector4f>(new Vector4f[8192]);

    static {
        for (int i = 0, len = POOL.size(); i < len; i++) {
            POOL.set(i, new Vector4f());
        }
    }

    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f() { }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Vector4f get() {
        return POOL.get();
    }

    public static Vector4f getNoW(float all) {
        return POOL.get().set(all, all, all, 0.0f);
    }

    public static Vector4f getW(float all) {
        return POOL.get().set(all, all, all, all);
    }

    public static Vector4f get(float x, float y, float z, float w) {
        return POOL.get().set(x, y, z, w);
    }

    public Vector4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector3f homogenised() {
        return Vector3f.get(this.x / this.w, this.y / this.w, this.z / this.w);
    }

    public Vector3f xyz() {
        return Vector3f.get(this.x, this.y, this.z);
    }

    public boolean isUnit() {
        return x >= -1.0f && x <= 1.0f &&
               y >= -1.0f && y <= 1.0f &&
               z >= -1.0f && z <= 1.0f;
    }

    @Override
    public Vector4f clone() {
        try {
            return (Vector4f) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public Vector4f copy() {
        return new Vector4f(this.x, this.y, this.z, this.w);
    }

    @Override
    public int hashCode() {
        return HashHelper.getHash4f(this.x, this.y, this.z, this.w);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector4f)) {
            return false;
        }
        Vector4f v = (Vector4f) obj;
        return this.x == v.x &&
               this.y == v.y &&
               this.z == v.z &&
               this.w == v.w;
    }

    @Override
    public String toString() {
        return "Vector4{" + this.x + "," + this.y + "," + this.z + "," + this.z + "}";
    }
}
