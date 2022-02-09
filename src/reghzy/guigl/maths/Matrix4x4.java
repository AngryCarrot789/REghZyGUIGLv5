package reghzy.guigl.maths;

import org.jetbrains.annotations.Contract;
import reghzy.guigl.render.utils.RingBuffer;

import java.util.Arrays;

public class Matrix4x4 {
    private static final float[] t = new float[16]; // temp buffer for copying matrix values into, while applying multiplication

    // used as a temporary matrix storage, which saves constantly allocating float arrays and matrix instances
    private static final RingBuffer<Matrix4x4> POOL = new RingBuffer<Matrix4x4>(new Matrix4x4[16384]);

    static {
        for(int i = 0, len = POOL.size(); i < len; i++) {
            POOL.set(i, new Matrix4x4());
        }
    }

    public float[] m;

    public Matrix4x4() {
        m = new float[16];
    }

    public Matrix4x4(float[] m) {
        this.m = m;
    }

    public void makeIdentity() {
        m[0]  = 1.0f; m[1]  = 0.0f; m[2]  = 0.0f; m[3]  = 0.0f;
        m[4]  = 0.0f; m[5]  = 1.0f; m[6]  = 0.0f; m[7]  = 0.0f;
        m[8]  = 0.0f; m[9]  = 0.0f; m[10] = 1.0f; m[11] = 0.0f;
        m[12] = 0.0f; m[13] = 0.0f; m[14] = 0.0f; m[15] = 1.0f;
    }

    public void makeRotationX(float r) {
        float cosR = (float) Math.cos(r);
        float sinR = (float) Math.sin(r);
        m[0]  = 1.0f; m[1]  = 0.0f; m[2]  = 0.0f;  m[3]  = 0.0f;
        m[4]  = 0.0f; m[5]  = cosR; m[6]  = -sinR; m[7]  = 0.0f;
        m[8]  = 0.0f; m[9]  = sinR; m[10] = cosR;  m[11] = 0.0f;
        m[12] = 0.0f; m[13] = 0.0f; m[14] = 0.0f;  m[15] = 1.0f;
    }

    public void makeRotationY(float r) {
        float cosR = (float) Math.cos(r);
        float sinR = (float) Math.sin(r);
        m[0]  = cosR;  m[1]  = 0.0f; m[2]  = sinR; m[3]  = 0.0f;
        m[4]  = 0.0f;  m[5]  = 1.0f; m[6]  = 0.0f; m[7]  = 0.0f;
        m[8]  = -sinR; m[9]  = 0.0f; m[10] = cosR; m[11] = 0.0f;
        m[12] = 0.0f;  m[13] = 0.0f; m[14] = 0.0f; m[15] = 1.0f;
    }

    public void makeRotationZ(float r) {
        float cosR = (float) Math.cos(r);
        float sinR = (float) Math.sin(r);
        m[0]  = cosR; m[1]  = -sinR; m[2]  = 0.0f; m[3]  = 0.0f;
        m[4]  = sinR; m[5]  = cosR;  m[6]  = 0.0f; m[7]  = 0.0f;
        m[8]  = 0.0f; m[9]  = 0.0f;  m[10] = 1.0f; m[11] = 0.0f;
        m[12] = 0.0f; m[13] = 0.0f;  m[14] = 0.0f; m[15] = 1.0f;
    }

    public void makeTranslation(Vector3f v) {
        m[0]  = 1.0f; m[1]  = 0.0f; m[2]  = 0.0f; m[3]  = v.x;
        m[4]  = 0.0f; m[5]  = 1.0f; m[6]  = 0.0f; m[7]  = v.y;
        m[8]  = 0.0f; m[9]  = 0.0f; m[10] = 1.0f; m[11] = v.z;
        m[12] = 0.0f; m[13] = 0.0f; m[14] = 0.0f; m[15] = 1.0f;
    }

    public void makeTranslation(float x, float y, float z) {
        m[0]  = 1.0f; m[1]  = 0.0f; m[2]  = 0.0f; m[3]  = x;
        m[4]  = 0.0f; m[5]  = 1.0f; m[6]  = 0.0f; m[7]  = y;
        m[8]  = 0.0f; m[9]  = 0.0f; m[10] = 1.0f; m[11] = z;
        m[12] = 0.0f; m[13] = 0.0f; m[14] = 0.0f; m[15] = 1.0f;
    }

    public void makeScale(Vector3f v) {
        m[0]  = v.x;  m[1]  = 0.0f; m[2]  = 0.0f; m[3]  = 0.0f;
        m[4]  = 0.0f; m[5]  = v.y;  m[6]  = 0.0f; m[7]  = 0.0f;
        m[8]  = 0.0f; m[9]  = 0.0f; m[10] = v.z;  m[11] = 0.0f;
        m[12] = 0.0f; m[13] = 0.0f; m[14] = 0.0f; m[15] = 1.0f;
    }

    public void makeScale(float x, float y, float z) {
        m[0]  = x;    m[1]  = 0.0f; m[2]  = 0.0f; m[3]  = 0.0f;
        m[4]  = 0.0f; m[5]  = y;    m[6]  = 0.0f; m[7]  = 0.0f;
        m[8]  = 0.0f; m[9]  = 0.0f; m[10] = z;    m[11] = 0.0f;
        m[12] = 0.0f; m[13] = 0.0f; m[14] = 0.0f; m[15] = 1.0f;
    }

    public Vector3f getAxisPartX() {
        return Vector3f.get(this.m[0], this.m[4], this.m[8]);
    }

    public Vector3f getAxisPartY() {
        return Vector3f.get(this.m[1], this.m[5], this.m[9]);
    }

    public Vector3f getAxisPartZ() {
        return Vector3f.get(this.m[2], this.m[6], this.m[10]);
    }

    public Vector3f getTranslationPart() {
        return Vector3f.get(this.m[3], this.m[7], this.m[11]);
    }

    public Vector3f getScalePart() {
        return Vector3f.get(this.m[0], this.m[5], this.m[10]);
    }

    @Contract(pure = true)
    public Matrix4x4 getTransposed() {
        Matrix4x4 n = POOL.get();
        n.m[0]  = m[0]; n.m[1]  = m[4]; n.m[2]  = m[8];  n.m[3]  = m[12];
        n.m[4]  = m[1]; n.m[5]  = m[5]; n.m[6]  = m[9];  n.m[7]  = m[13];
        n.m[8]  = m[2]; n.m[9]  = m[6]; n.m[10] = m[10]; n.m[11] = m[14];
        n.m[12] = m[3]; n.m[13] = m[7]; n.m[14] = m[11]; n.m[15] = m[15];
        return n;
    }

    public static Matrix4x4 getInverse(Matrix4x4 m) {
        Matrix4x4 inv = POOL.get();
        inv.m[0] = m.m[5] * m.m[10] * m.m[15] -
                   m.m[5] * m.m[11] * m.m[14] -
                   m.m[9] * m.m[6] * m.m[15] +
                   m.m[9] * m.m[7] * m.m[14] +
                   m.m[13] * m.m[6] * m.m[11] -
                   m.m[13] * m.m[7] * m.m[10];

        inv.m[4] = -m.m[4] * m.m[10] * m.m[15] +
                   m.m[4] * m.m[11] * m.m[14] +
                   m.m[8] * m.m[6] * m.m[15] -
                   m.m[8] * m.m[7] * m.m[14] -
                   m.m[12] * m.m[6] * m.m[11] +
                   m.m[12] * m.m[7] * m.m[10];

        inv.m[8] = m.m[4] * m.m[9] * m.m[15] -
                   m.m[4] * m.m[11] * m.m[13] -
                   m.m[8] * m.m[5] * m.m[15] +
                   m.m[8] * m.m[7] * m.m[13] +
                   m.m[12] * m.m[5] * m.m[11] -
                   m.m[12] * m.m[7] * m.m[9];

        inv.m[12] = -m.m[4] * m.m[9] * m.m[14] +
                    m.m[4] * m.m[10] * m.m[13] +
                    m.m[8] * m.m[5] * m.m[14] -
                    m.m[8] * m.m[6] * m.m[13] -
                    m.m[12] * m.m[5] * m.m[10] +
                    m.m[12] * m.m[6] * m.m[9];

        inv.m[1] = -m.m[1] * m.m[10] * m.m[15] +
                   m.m[1] * m.m[11] * m.m[14] +
                   m.m[9] * m.m[2] * m.m[15] -
                   m.m[9] * m.m[3] * m.m[14] -
                   m.m[13] * m.m[2] * m.m[11] +
                   m.m[13] * m.m[3] * m.m[10];

        inv.m[5] = m.m[0] * m.m[10] * m.m[15] -
                   m.m[0] * m.m[11] * m.m[14] -
                   m.m[8] * m.m[2] * m.m[15] +
                   m.m[8] * m.m[3] * m.m[14] +
                   m.m[12] * m.m[2] * m.m[11] -
                   m.m[12] * m.m[3] * m.m[10];

        inv.m[9] = -m.m[0] * m.m[9] * m.m[15] +
                   m.m[0] * m.m[11] * m.m[13] +
                   m.m[8] * m.m[1] * m.m[15] -
                   m.m[8] * m.m[3] * m.m[13] -
                   m.m[12] * m.m[1] * m.m[11] +
                   m.m[12] * m.m[3] * m.m[9];

        inv.m[13] = m.m[0] * m.m[9] * m.m[14] -
                    m.m[0] * m.m[10] * m.m[13] -
                    m.m[8] * m.m[1] * m.m[14] +
                    m.m[8] * m.m[2] * m.m[13] +
                    m.m[12] * m.m[1] * m.m[10] -
                    m.m[12] * m.m[2] * m.m[9];

        inv.m[2] = m.m[1] * m.m[6] * m.m[15] -
                   m.m[1] * m.m[7] * m.m[14] -
                   m.m[5] * m.m[2] * m.m[15] +
                   m.m[5] * m.m[3] * m.m[14] +
                   m.m[13] * m.m[2] * m.m[7] -
                   m.m[13] * m.m[3] * m.m[6];

        inv.m[6] = -m.m[0] * m.m[6] * m.m[15] +
                   m.m[0] * m.m[7] * m.m[14] +
                   m.m[4] * m.m[2] * m.m[15] -
                   m.m[4] * m.m[3] * m.m[14] -
                   m.m[12] * m.m[2] * m.m[7] +
                   m.m[12] * m.m[3] * m.m[6];

        inv.m[10] = m.m[0] * m.m[5] * m.m[15] -
                    m.m[0] * m.m[7] * m.m[13] -
                    m.m[4] * m.m[1] * m.m[15] +
                    m.m[4] * m.m[3] * m.m[13] +
                    m.m[12] * m.m[1] * m.m[7] -
                    m.m[12] * m.m[3] * m.m[5];

        inv.m[14] = -m.m[0] * m.m[5] * m.m[14] +
                    m.m[0] * m.m[6] * m.m[13] +
                    m.m[4] * m.m[1] * m.m[14] -
                    m.m[4] * m.m[2] * m.m[13] -
                    m.m[12] * m.m[1] * m.m[6] +
                    m.m[12] * m.m[2] * m.m[5];

        inv.m[3] = -m.m[1] * m.m[6] * m.m[11] +
                   m.m[1] * m.m[7] * m.m[10] +
                   m.m[5] * m.m[2] * m.m[11] -
                   m.m[5] * m.m[3] * m.m[10] -
                   m.m[9] * m.m[2] * m.m[7] +
                   m.m[9] * m.m[3] * m.m[6];

        inv.m[7] = m.m[0] * m.m[6] * m.m[11] -
                   m.m[0] * m.m[7] * m.m[10] -
                   m.m[4] * m.m[2] * m.m[11] +
                   m.m[4] * m.m[3] * m.m[10] +
                   m.m[8] * m.m[2] * m.m[7] -
                   m.m[8] * m.m[3] * m.m[6];

        inv.m[11] = -m.m[0] * m.m[5] * m.m[11] +
                    m.m[0] * m.m[7] * m.m[9] +
                    m.m[4] * m.m[1] * m.m[11] -
                    m.m[4] * m.m[3] * m.m[9] -
                    m.m[8] * m.m[1] * m.m[7] +
                    m.m[8] * m.m[3] * m.m[5];

        inv.m[15] = m.m[0] * m.m[5] * m.m[10] -
                    m.m[0] * m.m[6] * m.m[9] -
                    m.m[4] * m.m[1] * m.m[10] +
                    m.m[4] * m.m[2] * m.m[9] +
                    m.m[8] * m.m[1] * m.m[6] -
                    m.m[8] * m.m[2] * m.m[5];

        float det = m.m[0] * inv.m[0] + m.m[1] * inv.m[4] + m.m[2] * inv.m[8] + m.m[3] * inv.m[12];
        inv = Matrix4x4.divide(inv, det);
        return inv;
    }

    public static Matrix4x4 zero() {
        Matrix4x4 matrix4 = POOL.get();
        Arrays.fill(matrix4.m, 0.0f);
        return matrix4;
    }

    public static Matrix4x4 identity() {
        Matrix4x4 matrix4 = POOL.get();
        matrix4.makeIdentity();
        return matrix4;
    }

    public static Matrix4x4 rotX(float r) {
        Matrix4x4 matrix4 = POOL.get();
        matrix4.makeRotationX(r);
        return matrix4;
    }

    public static Matrix4x4 rotY(float r) {
        Matrix4x4 matrix4 = POOL.get();
        matrix4.makeRotationY(r);
        return matrix4;
    }

    public static Matrix4x4 rotZ(float r) {
        Matrix4x4 matrix4 = POOL.get();
        matrix4.makeRotationZ(r);
        return matrix4;
    }

    public static Matrix4x4 translation(Vector3f v) {
        Matrix4x4 matrix4 = POOL.get();
        matrix4.makeTranslation(v);
        return matrix4;
    }

    public static Matrix4x4 translation(float x, float y, float z) {
        Matrix4x4 matrix4 = POOL.get();
        matrix4.makeTranslation(x, y, z);
        return matrix4;
    }

    public static Matrix4x4 scale(Vector3f v) {
        Matrix4x4 matrix4 = POOL.get();
        matrix4.makeScale(v);
        return matrix4;
    }

    public static Matrix4x4 scale(float x, float y, float z) {
        Matrix4x4 matrix4 = POOL.get();
        matrix4.makeScale(x, y, z);
        return matrix4;
    }

    public Matrix4x4 multiply(Matrix4x4 matrix) {
        return multiply(this, matrix);
        // float[] m = this.m;      // our matrix
        // float[] t = Matrix4x4.t; // temporary matrix
        // float[] b = matrix.m;    // parameter matrix
        // System.arraycopy(m, 0, t, 0, 16); // copy our values into a temp matrix, and use temp for calculations
        // m[0]  = b[0] * t[0]  + b[4] * t[1]  + b[8]  * t[2]  + b[12] * t[3];
        // m[1]  = b[1] * t[0]  + b[5] * t[1]  + b[9]  * t[2]  + b[13] * t[3];
        // m[2]  = b[2] * t[0]  + b[6] * t[1]  + b[10] * t[2]  + b[14] * t[3];
        // m[3]  = b[3] * t[0]  + b[7] * t[1]  + b[11] * t[2]  + b[15] * t[3];
        // m[4]  = b[0] * t[4]  + b[4] * t[5]  + b[8]  * t[6]  + b[12] * t[7];
        // m[5]  = b[1] * t[4]  + b[5] * t[5]  + b[9]  * t[6]  + b[13] * t[7];
        // m[6]  = b[2] * t[4]  + b[6] * t[5]  + b[10] * t[6]  + b[14] * t[7];
        // m[7]  = b[3] * t[4]  + b[7] * t[5]  + b[11] * t[6]  + b[15] * t[7];
        // m[8]  = b[0] * t[8]  + b[4] * t[9]  + b[8]  * t[10] + b[12] * t[11];
        // m[9]  = b[1] * t[8]  + b[5] * t[9]  + b[9]  * t[10] + b[13] * t[11];
        // m[10] = b[2] * t[8]  + b[6] * t[9]  + b[10] * t[10] + b[14] * t[11];
        // m[11] = b[3] * t[8]  + b[7] * t[9]  + b[11] * t[10] + b[15] * t[11];
        // m[12] = b[0] * t[12] + b[4] * t[13] + b[8]  * t[14] + b[12] * t[15];
        // m[13] = b[1] * t[12] + b[5] * t[13] + b[9]  * t[14] + b[13] * t[15];
        // m[14] = b[2] * t[12] + b[6] * t[13] + b[10] * t[14] + b[14] * t[15];
        // m[15] = b[3] * t[12] + b[7] * t[13] + b[11] * t[14] + b[15] * t[15];
        // return this;
    }

    public static Matrix4x4 multiply(Matrix4x4 matrixA, Matrix4x4 matrixB) {
        Matrix4x4 out = POOL.get();
        float[] a = matrixA.m;
        float[] b = matrixB.m;
        float[] o = out.m;
        o[0]  = b[0] * a[0]  + b[4] * a[1]  + b[8]  * a[2]  + b[12] * a[3];
        o[1]  = b[1] * a[0]  + b[5] * a[1]  + b[9]  * a[2]  + b[13] * a[3];
        o[2]  = b[2] * a[0]  + b[6] * a[1]  + b[10] * a[2]  + b[14] * a[3];
        o[3]  = b[3] * a[0]  + b[7] * a[1]  + b[11] * a[2]  + b[15] * a[3];
        o[4]  = b[0] * a[4]  + b[4] * a[5]  + b[8]  * a[6]  + b[12] * a[7];
        o[5]  = b[1] * a[4]  + b[5] * a[5]  + b[9]  * a[6]  + b[13] * a[7];
        o[6]  = b[2] * a[4]  + b[6] * a[5]  + b[10] * a[6]  + b[14] * a[7];
        o[7]  = b[3] * a[4]  + b[7] * a[5]  + b[11] * a[6]  + b[15] * a[7];
        o[8]  = b[0] * a[8]  + b[4] * a[9]  + b[8]  * a[10] + b[12] * a[11];
        o[9]  = b[1] * a[8]  + b[5] * a[9]  + b[9]  * a[10] + b[13] * a[11];
        o[10] = b[2] * a[8]  + b[6] * a[9]  + b[10] * a[10] + b[14] * a[11];
        o[11] = b[3] * a[8]  + b[7] * a[9]  + b[11] * a[10] + b[15] * a[11];
        o[12] = b[0] * a[12] + b[4] * a[13] + b[8]  * a[14] + b[12] * a[15];
        o[13] = b[1] * a[12] + b[5] * a[13] + b[9]  * a[14] + b[13] * a[15];
        o[14] = b[2] * a[12] + b[6] * a[13] + b[10] * a[14] + b[14] * a[15];
        o[15] = b[3] * a[12] + b[7] * a[13] + b[11] * a[14] + b[15] * a[15];
        return out;
    }

    public Vector4f multiply(Vector4f v) {
        return Vector4f.get(m[0] * v.x +  m[1] * v.y +  m[2] * v.z +  m[3] *  v.w,
                            m[4] * v.x +  m[5] * v.y +  m[6] * v.z +  m[7] *  v.w,
                            m[8] * v.x +  m[9] * v.y +  m[10] * v.z + m[11] * v.w,
                            m[12] * v.x + m[13] * v.y + m[14] * v.z + m[15] * v.w);
    }

    public Vector4f multiply(Vector3f v) {
        return Vector4f.get(m[0] * v.x + m[1] * v.y + m[2] * v.z + m[3],
                            m[4] * v.x + m[5] * v.y + m[6] * v.z + m[7],
                            m[8] * v.x + m[9] * v.y + m[10] * v.z + m[11],
                            m[12] * v.x + m[13] * v.y + m[14] * v.z + m[15]);
    }

    public static Vector4f multiply(Matrix4x4 m, Vector4f v) {
        return Vector4f.get(m.m[0]  * v.x +  m.m[1] * v.y + m.m[2]  * v.z + m.m[3]  * v.w,
                            m.m[4]  * v.x + m.m[5]  * v.y + m.m[6]  * v.z + m.m[7]  * v.w,
                            m.m[8]  * v.x + m.m[9]  * v.y + m.m[10] * v.z + m.m[11] * v.w,
                            m.m[12] * v.x + m.m[13] * v.y + m.m[14] * v.z + m.m[15] * v.w);
    }

    public Vector3f multiplyPoint(Vector3f v) {
        float w = m[12] * v.x + m[13] * v.y + m[14] * v.z + m[15];
        return Vector3f.get(m[0] * v.x + m[1] * v.y + m[2]  * v.z + m[3],
                            m[4] * v.x + m[5] * v.y + m[6]  * v.z + m[7],
                            m[8] * v.x + m[9] * v.y + m[10] * v.z + m[11]).multiply(w);
    }

    public Vector3f multiplyDirection(Vector3f v) {
        return Vector3f.get(m[0] * v.x + m[1] * v.y + m[2] * v.z,
                            m[4] * v.x + m[5] * v.y + m[6] * v.z,
                            m[8] * v.x + m[9] * v.y + m[10] * v.z);
    }

    public static Matrix4x4 divide(Matrix4x4 m, float a) {
        Matrix4x4 n = POOL.get();
        for (int i = 0; i < 16; i++) {
            n.m[i] = m.m[i] / a;
        }
        return n;
    }

    public static Matrix4x4 projection(float vpWidth, float vpHeight, float n, float f, float fov) {
        float fr = (float) (1.0f / Math.tan(fov * Math.PI / 360.0f)); // fov in radians
        float a = vpHeight / vpWidth;   // aspect ratio
        float d = n - f;                // distance between near and far
        Matrix4x4 projection = POOL.get();
        float[] m = projection.m;
        m[0] = fr * a;  m[1] = 0.0f;  m[2] = 0.0f;         m[3] = 0.0f;
        m[4] = 0.0f;    m[5] = fr;    m[6] = 0.0f;         m[7] = 0.0f;
        m[8] = 0.0f;    m[9] = 0.0f;  m[10] = (n + f) / d; m[11] = (2 * n * f) / d;
        m[12] = 0.0f;   m[13] = 0.0f; m[14] = -1.0f;       m[15] = 0.0f;
        return projection;
    }

    // doesn't work :(
    public static Matrix4x4 orthographic(float left, float top, float right, float bottom, float near, float far) {
        Matrix4x4 orthographic = POOL.get();
        float[] m = orthographic.m;
        m[0] = 2 / (right - left);  m[1] = 0.0f;                m[2] = 0.0f;                m[3] = -(right + left) / (right - left);
        m[4] = 0.0f;                m[5] = 2 / (top - bottom);  m[6] = 0.0f;                m[7] = -(top + bottom) / (top - bottom);
        m[8] = 0.0f;                m[9] = 0.0f;                m[10] = -2 / (far - near);  m[11] = -(far + near) / (far - near);
        m[12] = 0.0f;               m[13] = 0.0f;               m[14] = 0.0f;               m[15] = 1.0f;
        return orthographic;
    }

    public static Matrix4x4 worldToLocal(Vector3f position, Vector3f rotation, Vector3f scale) {
        Matrix4x4 a = Matrix4x4.scale(1.0f / scale.x, 1.0f / scale.y, 1.0f / scale.z);
        Matrix4x4 b = Matrix4x4.rotZ(-rotation.z);
        Matrix4x4 c = Matrix4x4.rotX(-rotation.x);
        Matrix4x4 d = Matrix4x4.rotY(-rotation.y);
        Matrix4x4 e = Matrix4x4.translation(-position.x, -position.y, -position.z);
        return a.multiply(b).multiply(c).multiply(d).multiply(e);
        // return Matrix4x4.multiply(Matrix4x4.multiply(Matrix4x4.multiply(Matrix4x4.multiply(a, b), c), d), e);
    }

    public static Matrix4x4 localToWorld(Vector3f position, Vector3f rotation, Vector3f scale) {
        Matrix4x4 a = Matrix4x4.translation(position);
        Matrix4x4 b = Matrix4x4.rotY(rotation.y);
        Matrix4x4 c = Matrix4x4.rotX(rotation.x);
        Matrix4x4 d = Matrix4x4.rotZ(rotation.z);
        Matrix4x4 e = Matrix4x4.scale(scale);
        return a.multiply(b).multiply(c).multiply(d).multiply(e);
        // return Matrix4x4.multiply(Matrix4x4.multiply(Matrix4x4.multiply(Matrix4x4.multiply(a, b), c), d), e);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof Matrix4x4) {
            Matrix4x4 matrix4 = (Matrix4x4) obj;
            return Arrays.equals(matrix4.m, this.m);
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.m);
    }

    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder(128);
        sb.append("Matrix4{");
        for(int i = 0; i < 16; i++) {
            sb.append(this.m[i]).append(',');
        }
        sb.append("}");
        return sb.toString();
    }

    @Contract(pure = true)
    public Matrix4x4 copy() {
        return new Matrix4x4(this.m.clone());
    }
}
