package reghzy.guigl.maths;

import sun.misc.DoubleConsts;
import sun.misc.FloatConsts;

public class Maths {
    public static final float PI = 3.1415926535897931f;
    public static final float PI_NEGATIVE = -3.141592653589f;
    public static final float PI_DOUBLE = 6.283185307178f;
    public static final float PI_DOUBLE_NEGATIVE = -6.283185307178f;
    public static final float PI_HALF = 1.5707963267945f;
    public static final float PI_HALF_NEGATIVE = -1.5707963267945f;
    public static final float DEG_TO_RAD_CONST = 57.29577951309679f;

    public static float min3(float a, float b, float c) {
        return Math.min(Math.min(a, b), c);
    }

    public static float max3(float a, float b, float c) {
        return Math.max(Math.max(a, b), c);
    }

    public static Vector2f clamp(Vector2f value, float min, float max) {
        return new Vector2f(clamp(value.x, min, max), clamp(value.y, min, max));
    }

    public static Vector3f clamp(Vector3f value, float min, float max) {
        return new Vector3f(clamp(value.x, min, max), clamp(value.y, min, max), clamp(value.z, min, max));
    }

    public static Vector2f clampVec2f(Vector2f instance, float min, float max) {
        instance.x = clamp(instance.x, min, max);
        instance.y = clamp(instance.y, min, max);
        return instance;
    }

    public static Vector3f clampVec3f(Vector3f instance, float min, float max) {
        instance.x = clamp(instance.x, min, max);
        instance.y = clamp(instance.y, min, max);
        instance.z = clamp(instance.z, min, max);
        return instance;
    }

    public static float degToRad(float degrees) {
        return degrees / DEG_TO_RAD_CONST;
    }

    public static float radToDeg(float degrees) {
        return degrees / DEG_TO_RAD_CONST;
    }

    public static float distance(Vector3f a, Vector3f b) {
        return (float) Math.pow((float) Math.pow(b.x - a.x, 2) +
                                (float) Math.pow(b.y - a.y, 2) +
                                (float) Math.pow(b.z - a.z, 2), 0.5);
    }

    public static double round(double value, int places) {
        if (places <= 0) {
            return Math.round(value);
        }

        long factor = (long) Math.pow(10, places);
        long rounded = Math.round(value * factor);
        return (double) rounded / factor;
    }

    public static boolean between(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean between(double value, double min, double max) {
        return value >= min && value <= max;
    }

    public static boolean between(long value, long min, long max) {
        return value >= min && value <= max;
    }

    /**
     * Clamps the given value between the given min and max values (both inclusive)
     * @param value The value
     * @param min   The smallest possible value to be returned
     * @param max   The biggest possible value to be returned
     * @return The value, min, or max
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        else {
            return value;
        }
    }

    /**
     * Clamps the given value between the given min and max values (both inclusive)
     * @param value The value
     * @param min   The smallest possible value to be returned
     * @param max   The biggest possible value to be returned
     * @return The value, min, or max
     */
    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        else {
            return value;
        }
    }

    /**
     * Clamps the given value between the given min and max values (both inclusive)
     * @param value The value
     * @param min   The smallest possible value to be returned
     * @param max   The biggest possible value to be returned
     * @return The value, min, or max
     */
    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        else {
            return value;
        }
    }

    public static int min3(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int max3(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    /**
     * Clamps the given value to the highest value of the given multiple (e.g getMaxOfMultiple(37, 16) == 48)
     * @param value    The value
     * @param multiple The multiple
     * @return The value (will never be more than value + multiple, it will always be below)
     */
    public static int maxOfMultiple(int value, int multiple) {
        int mod = value % multiple;
        if (mod == 0) {
            return value;
        }
        else {
            return value + (multiple - mod);
        }
    }

    /**
     * Linear interpolation between 2 values,
     * @param from       Start value
     * @param to         End value
     * @param multiplier Lerp multiplier
     */
    public static double lerp(double from, double to, double multiplier) {
        return from + multiplier * (to - from);
    }

    public static Vector3f lerp(Vector3f from, Vector3f to, float multiplier) {
        return new Vector3f(from.x + multiplier * (to.x - from.x),
                            from.y + multiplier * (to.y - from.y),
                            from.z + multiplier * (to.z - from.z));
    }

    public static Vector2f lerp(Vector2f from, Vector2f to, float multiplier) {
        return new Vector2f(from.x + multiplier * (to.x - from.x),
                            from.y + multiplier * (to.y - from.y));
    }

    public static Vector2d lerp(Vector2d from, Vector2d to, float multiplier) {
        return new Vector2d(from.x + multiplier * (to.x - from.x),
                           from.y + multiplier * (to.y - from.y));
    }

    private static final double D_SWS1 = DoubleConsts.SIGNIFICAND_WIDTH - 1;
    private static final float F_SWS1 = FloatConsts.SIGNIFICAND_WIDTH - 1;

    public static long floor(double value) {
        int exponent = Math.getExponent(value);
        if (exponent < 0) {
            return (int) ((value == 0.0) ? value : ((value < 0.0) ? -1.0 : 0.0));
        }
        else if (exponent >= D_SWS1) { // significant width - 1
            return (int) value;
        }

        long bits = Double.doubleToRawLongBits(value);
        long mask = DoubleConsts.SIGNIF_BIT_MASK >> exponent;
        if ((mask & bits) == 0L) {
            return (int) value;
        }
        else {
            double result = Double.longBitsToDouble(bits & (~mask));
            if (-1.0 * value > 0.0) {
                result = result + -1.0;
            }

            return (int) result;
        }
    }

    public static int ifloor(double value) {
        return (int) floor(value);
    }

    public static int floor(float value) {
        int exponent = Math.getExponent(value);
        if (exponent < 0) {
            return (int) ((value == 0.0f) ? value : ((value < 0.0f) ? -1.0 : 0.0));
        }
        else if (exponent >= F_SWS1) { // significant width - 1
            return (int) value;
        }

        int bits = Float.floatToRawIntBits(value);
        int mask = FloatConsts.SIGNIF_BIT_MASK >> exponent;
        if ((mask & bits) == 0) {
            return (int) value;
        }
        else {
            float result = Float.intBitsToFloat(bits & (~mask));
            if (-1.0f * value > 0.0f) {
                result = result + -1.0f;
            }

            return (int) result;
        }
    }

    public static long ceil(double value) {
        int exponent = Math.getExponent(value);
        if (exponent < 0) {
            return ((value == 0.0) ? ((int) value) : ((value < 0.0) ? 0 : 1));
        }
        else if (exponent >= D_SWS1) {
            return (int) value;
        }

        long bits = Double.doubleToRawLongBits(value);
        long mask = DoubleConsts.SIGNIF_BIT_MASK >> exponent;
        if ((mask & bits) == 0L) {
            return (int) value;
        }
        else {
            double result = Double.longBitsToDouble(bits & (~mask));
            return value > 0.0 ? (int) (result + 1.0) : (int) result;
        }
    }

    public static int iceil(double value) {
        return (int) ceil(value);
    }

    public static int ceil(float value) {
        int exponent = Math.getExponent(value);
        if (exponent < 0) {
            return ((value == 0.0f) ? ((int) value) : ((value < 0.0f) ? 0 : 1));
        }
        else if (exponent >= F_SWS1) {
            return (int) value;
        }

        int bits = Float.floatToRawIntBits(value);
        int mask = FloatConsts.SIGNIF_BIT_MASK >> exponent;
        if ((mask & bits) == 0) {
            return (int) value;
        }
        else {
            float result = Float.intBitsToFloat(bits & (~mask));
            return value > 0.0f ? (int) (result + 1.0f) : (int) result;
        }
    }

    public static long fastfloor(double value) {
        long cast = (long) value;
        if ((double) cast == value) {
            return cast;
        }
        else {
            return cast - (Double.doubleToRawLongBits(value) >>> 63);
        }
    }

    public static int ifastfloor(double value) {
        long cast = (long) value;
        if ((double) cast == value) {
            return (int) cast;
        }
        else {
            return (int) (cast - (Double.doubleToRawLongBits(value) >>> 63));
        }
    }

    public static int fastfloor(float value) {
        int cast = (int) value;
        if ((float) cast == value) {
            return cast;
        }
        else {
            return cast - (Float.floatToRawIntBits(value) >>> 31);
        }
    }

    public static long fastceil(double num) {
        long cast = (long) num;
        if ((double) cast == num) {
            return cast;
        }
        else {
            return cast + (~Double.doubleToRawLongBits(num) >>> 63);
        }
    }

    public static int ifastceil(double num) {
        long cast = (long) num;
        if ((double) cast == num) {
            return (int) cast;
        }
        else {
            return (int) (cast + (~Double.doubleToRawLongBits(num) >>> 63));
        }
    }

    public static int fastceil(float num) {
        int cast = (int) num;
        if ((float) cast == num) {
            return cast;
        }
        else {
            return cast + (~Float.floatToRawIntBits(num) >>> 31);
        }
    }

    public static long round(double value) {
        return floor(value + 0.5d);
    }

    public static int iround(double value) {
        return ifloor(value + 0.5);
    }

    public static int round(float value) {
        return floor(value + 0.5f);
    }

    public static double square(double d) {
        return d * d;
    }

    public static float square(float d) {
        return d * d;
    }
}
