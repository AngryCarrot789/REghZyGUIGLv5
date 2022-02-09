package reghzy.guigl.utils;

public class HashHelper {
    public static int getHash1i(int a) {
        return a;
    }

    /** The maximum values A or B can be are 32767. Any more and the hash code will probably fail at some point
     * @param a a
     * @param b b
     * @return hash
     */
    public static int getHash2i(int a, int b) {
        return a + (b << 15);
    }

    /**
     * Max value for A, B, C or D is 1023. otherwise hash collision might happen
     * @param a a
     * @param b b
     * @param c c
     * @return hash
     */
    public static int getHash3i(int a, int b, int c) {
        return a + (b << 10) + (c << 20);
    }

    /**
     * Max value for A, B, C or D is 127. otherwise hash collision might happen
     * @param a a
     * @param b b
     * @param c c
     * @param d d
     * @return hash
     */
    public static int getHash4i(int a, int b, int c, int d) {
        return a + (b << 7) + (c << 14) + (d << 21);
    }

    public static int getHash1f(float a) {
        return Float.hashCode(a);
    }

    public static int getHash2f(float a, float b) {
        return Float.hashCode(a) + (Float.hashCode(b) << 15);
    }

    public static int getHash3f(float a, float b, float c) {
        return Float.hashCode(a) + (Float.hashCode(b) << 10) + (Float.hashCode(c) << 20);
    }

    public static int getHash4f(float a, float b, float c, float d) {
        return Float.hashCode(a) + (Float.hashCode(b) << 7) + (Float.hashCode(c) << 14) + (Float.hashCode(d) << 21);
    }
}
