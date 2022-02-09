package reghzy.guigl.maths;

import com.sun.org.apache.bcel.internal.generic.NEW;
import reghzy.guigl.render.utils.RingBuffer;

/**
 * <h2>
 *     A 2-dimensional plane along the X and Y axis
 * </h2>
 * <p>
 *     The minX and minY coordinates are located at the bottom left corner of the region
 * </p>
 * <p>
 *     The maxX and maxY coordinates are located at the top right corner
 * </p>
 * <p>
 *     minX to maxX travels left to right
 * </p>
 * <p>
 *     minY to maxY travels bottom to top
 * </p>
 */
public class Region2d {
    private static final RingBuffer<Region2d> POOL = new RingBuffer<Region2d>(new Region2d[8192]);

    static {
        for (int i = 0, len = POOL.size(); i < len; i++) {
            POOL.set(i, new Region2d());
        }
    }

    public double minX;
    public double minY;
    public double maxX;
    public double maxY;

    public Region2d() {

    }

    public Region2d(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public static Region2d get() {
        return POOL.get();
    }

    public static Region2d get(double minX, double minY, double maxX, double maxY) {
        return POOL.get().set(minX, minY, maxX, maxY);
    }

    public static Region2d get(Vector2d min, Vector2d max) {
        return POOL.get().set(min, max);
    }

    public static Region2d fromXYWH(double x, double y, double width, double height) {
        return get(x, y, x + width, y + height);
    }

    //
    //                Min X _________________ Max X
    //                Max Y |               | Max Y
    //                      |               |
    //                      |    Region     |
    //            __________|               |
    //            |         |               |
    //            |   this  |_______________| Max X
    //            |            |              Min Y
    //            |____________|
    //
    //
    //              _________________
    //              | _______       |
    //              | |      |      |
    //              | |   R  |      |
    //              | |      |      |
    //              | |______|      |
    //              |_______________|
    //

    /**
     * Returns true if the given region intersects this region
     * @param region The region to check intersection on
     */
    public boolean intersects(Region2d region) {
        return region.minX < this.maxX && region.maxX > this.minX && region.maxY > this.minY && region.minY < this.maxY;
    }

    /**
     * Returns true if the given vector is within this region
     * @param v The X and Y coordinates to check
     */
    public boolean intersects(Vector2d v) {
        return v.x > this.minX && v.x < this.maxX && v.y > this.minY && v.y < this.maxY;
    }

    /**
     * Returns true if the given X and Y coordinates intersect this region
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public boolean intersects(double x, double y) {
        return x > this.minX && x < this.maxX && y > this.minY && y < this.maxY;
    }

    /**
     * Returns true if this region is entirely within the given region (meaning none
     * of this region is outside of the given one; it's all packed inside)
     * <p>
     *     This method would yield the same result if you called {@link Region2d#intersects(double, double)} twice,
     *     for both the minX/minY and maxX/maxY coordinates, but this is more optimised
     * </p>
     * @param region The region to check
     */
    public boolean isEntirelyWithin(Region2d region) {
        // return this.minX < region.maxX && this.minX > region.minX &&
        //        this.maxX < region.maxX && this.maxX > region.minX &&
        //        this.minY < region.maxY && this.minY > region.minY &&
        //        this.maxY < region.maxY && this.maxY > region.minY;
        return this.minX <= region.maxX && this.minX >= region.minX &&
               this.maxX <= region.maxX && this.maxX >= region.minX &&
               this.minY <= region.maxY && this.minY >= region.minY &&
               this.maxY <= region.maxY && this.maxY >= region.minY;
    }


    /**
     * Returns true if this region is entirely within the given region area (meaning none
     * of this region is outside of the given one; it's all packed inside)
     * <p>
     * This method would yield the same result if you called {@link Region2d#intersects(double, double)} twice,
     * for both the minX/minY and maxX/maxY coordinates, but this is more optimised
     * </p>
     */
    public boolean isEntirelyWithin(double minX, double minY, double maxX, double maxY) {
        return this.minX < maxX && this.minX > minX &&
               this.maxX < maxX && this.maxX > minX &&
               this.minY < maxY && this.minY > minY &&
               this.maxY < maxY && this.maxY > minY;
    }

    public double calculateOffsetX(Region2d region, double amount) {
        if (region.maxY <= this.minY || region.minY >= this.maxY) {
            return amount;
        }

        double offset;
        if (amount > 0.0f && region.maxX <= this.minX) {
            offset = this.minX - region.maxX;
            if (offset < amount) {
                amount = offset;
            }
        }

        if (amount < 0.0f && region.minX >= this.maxX) {
            offset = this.maxX - region.minX;
            if (offset > amount) {
                amount = offset;
            }
        }

        return amount;
    }

    public double calculateOffsetY(Region2d region, double amount) {
        if (region.maxX <= this.minX || region.minX >= this.maxX) {
            return amount;
        }

        double offset;
        if (amount > 0.0f && region.maxY <= this.minY) {
            offset = this.minY - region.maxY;
            if (offset < amount) {
                amount = offset;
            }
        }

        if (amount < 0.0f && region.minY >= this.maxY) {
            offset = this.maxY - region.minY;
            if (offset > amount) {
                amount = offset;
            }
        }

        return amount;
    }

    public double getWidth() {
        return this.maxX - this.minX;
    }

    public double getHeight() {
        return this.maxY - this.minY;
    }

    public Region2d offset(double x, double y) {
        this.minX += x;
        this.minY += y;
        this.maxX += x;
        this.maxY += y;
        return this;
    }

    public Region2d contract(double x, double y) {
        this.minX += x;
        this.minY += y;
        this.maxX -= x;
        this.maxY -= y;
        return this;
    }

    public Region2d expandFromCenter(double x, double y) {
        this.minX -= x;
        this.minY -= y;
        this.maxX += x;
        this.maxY += y;
        return this;
    }

    public Region2d expandFromTL(double x, double y) {
        this.maxX += x;
        this.maxY += y;
        return this;
    }

    public Region2d expandFromTR(double x, double y) {
        this.minX -= x;
        this.maxY += y;
        return this;
    }

    public Region2d expandFromBL(double x, double y) {
        this.minY -= y;
        this.maxX += x;
        return this;
    }

    public Region2d expandFromBR(double x, double y) {
        this.minX -= x;
        this.minY -= y;
        return this;
    }

    public Region2d set(Region2d region) {
        this.minX = region.minX;
        this.minY = region.minY;
        this.maxX = region.maxX;
        this.maxY = region.maxY;
        return this;
    }

    public Region2d set(Vector2d min, Vector2d max) {
        this.minX = min.x;
        this.minY = min.y;
        this.maxX = max.x;
        this.maxY = max.y;
        return this;
    }

    public Region2d set(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        return this;
    }

    public Vector2d getMin() {
        return Vector2d.get(this.minX, this.minY);
    }

    public Vector2d getMax() {
        return Vector2d.get(this.maxX, this.maxY);
    }

    public Region2d copy() {
        return new Region2d(this.minX, this.minY, this.maxX, this.maxY);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof Region2d) {
            Region2d r = (Region2d) obj;
            return r.minX == this.minX && r.minY == this.minY && r.maxX == this.maxY && r.maxY == this.maxY;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append('{').append(this.minX).append(',').append(this.minY).append(',').append(this.maxX).append(',').append(this.maxY).append('}').toString();
    }
}
