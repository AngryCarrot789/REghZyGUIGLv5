package reghzy.guigl.maths;

public class VectorSpeed {
    public static void multiplyVector3By(Vector3f a, Vector3f b) {
        a.set(a.x * b.x, a.y * b.y, a.z * b.z);
    }
}
