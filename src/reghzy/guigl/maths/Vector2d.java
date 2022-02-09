package reghzy.guigl.maths;

import com.sun.javafx.geom.Vec3d;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import sun.reflect.generics.tree.ReturnType;

/**
 * Vector2d is used for accurate 2D graphics, used for mainly positioning
 */
public class Vector2d {
    // chances this won't be enough? unlikely
    private static final int POOL_SIZE = 16384;
    private static final int POOL_SZS1 = POOL_SIZE - 1;
    private static final Vector2d[] POOL;
    private static int NEXT = 0;

    static {
        POOL = new Vector2d[POOL_SIZE];
        for (int i = 0; i < POOL.length; i++) {
            POOL[i] = new Vector2d();
        }
    }

    public static final double EPSILON = 1.0E-6D;

    public double x;
    public double y;

    public Vector2d() {

    }

    public Vector2d(double all) {
        this.x = all;
        this.y = all;
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(int x, int y, Vector2d extra) {
        this.x = x + extra.x;
        this.y = y + extra.y;
    }

    public Vector2d(double x, double y, Vector2d extra) {
        this.x = x + extra.x;
        this.y = y + extra.y;
    }

    public Vector2d(float x, float y, Vector2d extra) {
        this.x = x + extra.x;
        this.y = y + extra.y;
    }

    public Vector2d(Vector2d vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    /**
     * Returns a temporary vector2 instance. This should only ever be used for a temporary calculation,
     * and NEVER be stored, because its values will most likely change eventually as the application runs
     */
    @NotNull
    private static Vector2d get0() {
        if (NEXT > POOL_SZS1) {
            return POOL[NEXT = 0];
        }
        else {
            return POOL[NEXT++];
        }
    }

    /**
     * Gets a temporary Vector2. There is no guarantee that the returned vector's values will stay the same over time
     * <p>
     * Therefore, this instance should NEVER be stored, only used temporarily in a function or 2
     * </p>
     */
    public static Vector2d get() {
        return get0().set(0.0d);
    }

    /**
     * Gets a temporary Vector2. There is no guarantee that the returned vector's values will stay the same over time
     * <p>
     * Therefore, this instance should NEVER be stored, only used temporarily in a function or 2
     * </p>
     */
    @NotNull
    public static Vector2d get(double all) {
        return get0().set(all);
    }

    /**
     * Gets a temporary Vector2. There is no guarantee that the returned vector's values will stay the same over time
     * <p>
     * Therefore, this instance should NEVER be stored, only used temporarily in a function or 2
     * </p>
     */
    @NotNull
    public static Vector2d get(int all) {
        return get0().set(all);
    }

    /**
     * Gets a temporary Vector2. There is no guarantee that the returned vector's values will stay the same over time
     * <p>
     * Therefore, this instance should NEVER be stored, only used temporarily in a function or 2
     * </p>
     */
    @NotNull
    public static Vector2d get(double x, double y) {
        return get0().set(x, y);
    }

    /**
     * Gets a temporary Vector2. There is no guarantee that the returned vector's values will stay the same over time
     * <p>
     * Therefore, this instance should NEVER be stored, only used temporarily in a function or 2
     * </p>
     */
    @NotNull
    public static Vector2d get(int x, int y) {
        return get0().set(x, y);
    }

    /**
     * Gets a temporary Vector2. There is no guarantee that the returned vector's values will stay the same over time
     * <p>
     * Therefore, this instance should NEVER be stored, only used temporarily in a function or 2
     * </p>
     */
    @NotNull
    public static Vector2d get(@NotNull Vector2d vector) {
        return get0().set(vector);
    }

    @NotNull
    public static Vector2d get(@NotNull Vector3f vec) {
        return get0().set(vec.x, vec.y);
    }

    /**
     * Gets a temporary Vector2. There is no guarantee that the returned vector's values will stay the same over time
     * <p>
     * Therefore, this instance should NEVER be stored, only used temporarily in a function or 2
     * </p>
     */
    @NotNull
    public static Vector2d get(@NotNull Vec3d vector) {
        return get0().set(vector.x, vector.y);
    }

    public Vector2d translate(double all) {
        this.x += all;
        this.y += all;
        return this;
    }

    public Vector2d translate(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2d translate(Vector2d v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2d translate(Direction direction) {
        this.x += direction.x;
        this.y += direction.y;
        return this;
    }

    public Vector2d translate(Direction direction, double size) {
        this.x += (direction.x * size);
        this.y += (direction.y * size);
        return this;
    }

    public Vector2d translate(Direction direction, int size) {
        this.x += (direction.x * size);
        this.y += (direction.y * size);
        return this;
    }

    public Vector2d subtract(double all) {
        this.x -= all;
        this.y -= all;
        return this;
    }

    public Vector2d subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2d subtract(Vector2d v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2d multiply(double all) {
        this.x *= all;
        this.y *= all;
        return this;
    }

    public Vector2d multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2d multiply(Vector2d v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vector2d divide(double all) {
        this.x /= all;
        this.y /= all;
        return this;
    }

    public Vector2d divide(double x, double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public Vector2d divide(Vector2d v) {
        this.x /= v.x;
        this.y /= v.y;
        return this;
    }

    public Vector2d set(double all) {
        this.x = all;
        this.y = all;
        return this;
    }

    public Vector2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2d set(Vector2d v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public Vector2d absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        return this;
    }

    public double mag() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double magSq() {
        return this.x * this.x + this.y * this.y;
    }

    public double distance(Vector2d o) {
        return Math.sqrt(Maths.square(this.x - o.x) + Maths.square(this.y - o.y));
    }

    public double distanceSquared(Vector2d o) {
        return Maths.square(this.x - o.x) + Maths.square(this.y - o.y);
    }

    public Vector2d midpoint(Vector2d other) {
        this.x = (this.x + other.x) / 2.0D;
        this.y = (this.y + other.y) / 2.0D;
        return this;
    }

    @Contract(pure = true)
    public Vector2d getMidpoint(Vector2d other) {
        double x = (this.x + other.x) / 2.0D;
        double y = (this.y + other.y) / 2.0D;
        return get0().set(x, y);
    }

    @Contract(pure = true)
    public double dot(Vector2d other) {
        return this.x * other.x + this.y * other.y;
    }

    public int intX() {
        return Maths.ifastfloor(this.x);
    }

    public int intY() {
        return Maths.ifastfloor(this.y);
    }

    public Vector2d setEmpty() {
        this.x = 0.0D;
        this.y = 0.0D;
        return this;
    }

    /**
     * Returns an absolute copy of this vector instance. This will not be a temporary vector; it is fully unique, and therefore can be stored
     */
    @Contract(pure = true)
    public Vector2d copy() {
        return new Vector2d(this.x, this.y);
    }

    /**
     * Returns a temporary copy of this vector
     * @see Vector2d#get(Vector2d)
     */
    public Vector2d getCopy() {
        return get0().set(this.x, this.y);
    }

    public Vector2f toVec2f() {
        return new Vector2f((float) this.x, (float) this.y);
    }

    public Vector3f toVec3f() {
        return new Vector3f((float) this.x, (float) this.y, 0.0f);
    }

    public Vector3f toVec3fXZ() {
        return new Vector3f((float) this.x, 0.0f, (float) this.y);
    }

    public Vector3f toVec3f(float z) {
        return new Vector3f((float) this.x, (float) this.y, z);
    }

    public Vector3f toVec3fXZ(float y) {
        return new Vector3f((float) this.x, y, (float) this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof Vector2d) {
            Vector2d v = (Vector2d) obj;
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
