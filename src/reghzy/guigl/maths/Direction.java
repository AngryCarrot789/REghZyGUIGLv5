package reghzy.guigl.maths;

public enum Direction {
    /**
     * 0; Down direction (left, relative to RH coordinate system)
     */
    WEST(-1, 0, true, false),

    /**
     * 1; North direction (forward, relative to RH coordinate system)
     */
    NORTH(0, -1, false, true),

    /**
     * 2; Down direction (right, relative to RH coordinate system)
     */
    EAST(1, 0, true, false),

    /**
     * 3; South direction (behind, relative to RH coordinate system)
     */
    SOUTH(0, 1, false, true),

    /**
     * 6; No direction (or, relative to self)
     */
    UNKNOWN(0, 0, false, false);

    private static final Direction[] ROTATION_LEFT = new Direction[] {SOUTH, WEST, NORTH, EAST };
    private static final Direction[] ROTATION_RIGHT = new Direction[]{NORTH, EAST, SOUTH, WEST };

    /**
     * Offset in the X axis
     */
    public final int x;

    /**
     * Offset in the X axis
     */
    public final int y;

    /**
     * Equivalent to this enum's ordinal
     * <p>
     *     This can also be used for a block's metadata to represent it's rotation
     * </p>
     */
    public final int index;

    public final int bitFlag;

    /**
     * An array of valid directions (not including {@link Direction#UNKNOWN})
     */
    public static final Direction[] VALID_DIRECTIONS = new Direction[]{WEST, NORTH, EAST, SOUTH};

    /**
     * An array of the opposites to {@link Direction#VALID_DIRECTIONS}
     */
    public static final Direction[] VALID_OPPOSITES = new Direction[]{EAST, SOUTH, WEST, NORTH};

    public static final int[] OPPOSITE_ORDINALS = new int[]{EAST.index, SOUTH.index, WEST.index, NORTH.index};
    private final boolean isHorizontal;
    private final boolean isVertical;

    Direction(int x, int y, boolean isHorizontal, boolean isVertical) {
        this.x = x;
        this.y = y;
        this.bitFlag = 1 << this.ordinal();
        this.index = this.ordinal();
        this.isHorizontal = isHorizontal;
        this.isVertical = isVertical;
    }

    public static Direction angleToDirection(double angle) {
        if (angle > 315 || angle <= 45) {
            return NORTH;
        }
        else if (angle > 45 && angle <= 135) {
            return EAST;
        }
        else if (angle > 135 && angle <= 225) {
            return SOUTH;
        }
        else {
            return WEST;
        }
    }

    public static Direction getOrientation(int id) {
        return id >= 0 && id < VALID_DIRECTIONS.length ? VALID_DIRECTIONS[id] : UNKNOWN;
    }

    public Direction getOpposite() {
        return VALID_OPPOSITES[this.index];
    }

    /**
     * Gets the direction to the left, relative to this
     */
    public Direction getLeft() {
        return ROTATION_LEFT[this.index];
    }

    /**
     * Gets the direction to the right, relative to this
     */
    public Direction getRight() {
        return ROTATION_RIGHT[this.index];
    }

    /**
     * Offset the given vector by this
     */
    public Vector2d translate(Vector2d vec) {
        return vec.translate(this.x, this.y);
    }

    /**
     * Offset the given vector by this multiplied by the given size (used to add extra distance)
     */
    public Vector2d translate(Vector2d vec, int size) {
        return vec.translate(this.x * size, this.y * size);
    }

    public boolean isHorizontal() {
        return this.isHorizontal;
    }

    public boolean isVertical() {
        return this.isVertical;
    }
}
