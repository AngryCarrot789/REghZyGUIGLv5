package reghzy.guigl.core.utils;

import reghzy.guigl.maths.Vector2d;

/**
 * An immutable thickness class, typically used to represent a distance
 * between a control (on the side of the Thickness' field names) and it's parent
 */
public class Thickness {
    public static Thickness ZERO = new Thickness();
    public static Thickness ONE = new Thickness(1.0d);

    public final double left;
    public final double top;
    public final double right;
    public final double bottom;

    public Thickness() {
        this.left = 0;
        this.top = 0;
        this.right = 0;
        this.bottom = 0;
    }

    public Thickness(double all) {
        this.left = all;
        this.right = all;
        this.top = all;
        this.bottom = all;
    }

    public Thickness(double horizontal, double vertical) {
        this.left = horizontal;
        this.right = horizontal;
        this.top = vertical;
        this.bottom = vertical;
    }

    public Thickness(double left, double top, double right, double bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Thickness(Vector2d topLeft, double right, double bottom) {
        this.left = topLeft.x;
        this.top = topLeft.y;
        this.right = right;
        this.bottom = bottom;
    }

    public Thickness(Vector2d topLeft, Vector2d bottomRight) {
        this.left = topLeft.x;
        this.top = topLeft.y;
        this.right = bottomRight.x;
        this.bottom = bottomRight.y;
    }

    public Thickness offset(double left, double top, double right, double bottom) {
        return new Thickness(this.left + left, this.top + top, this.right + right, this.bottom + bottom);
    }

    public Thickness offset(double x, double y) {
        return new Thickness(this.left + x, this.top + y, this.right + x, this.bottom + y);
    }

    public Thickness offset(double value) {
        return new Thickness(this.left + value, this.top + value, this.right + value, this.bottom + value);
    }

    public Thickness copy() {
        return new Thickness(this.left, this.top, this.right, this.bottom);
    }
}
