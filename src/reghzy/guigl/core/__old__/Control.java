package reghzy.guigl.core.__old__;

import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.event.events.HorizontalAlignChangedEvent;
import reghzy.guigl.core.event.events.KeyEvent;
import reghzy.guigl.core.event.events.MouseButtonEvent;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.core.event.events.VerticalAlignChangedEvent;
import reghzy.guigl.core.event.events.WindowResizeEvent;
import reghzy.guigl.core.utils.ColourARGB;
import reghzy.guigl.core.utils.Thickness;
import reghzy.guigl.math.Region2d;
import reghzy.guigl.math.Vector2;

/**
 * <h2>
 *     The base class for every UI element
 * </h2>
 */
public class Control {
    public static Control focused;
    public static Control lastFocused;

    public Thickness margin;
    public Control parent;
    public Region2d region;
    public double x;
    public double y;
    public double width;
    public double height;
    public boolean isMouseOver;
    public boolean isMouseDown;
    public boolean renderNextTick;
    public boolean isFocusable;

    public ColourARGB background;
    public ColourARGB border;
    public Thickness borderThickness;

    public Control() {
        this.margin = new Thickness(0);
        this.region = new Region2d(0, 0, 80, 30);
        this.isFocusable = true;
        this.renderNextTick = true;
        this.borderThickness = new Thickness(1);
    }

    // public void loadStyleSheet(ResourceMap sheet) {
    //     if (sheet.hasKey("background")) {
    //         this.background = sheet.getColour("background");
    //     }
    //     if (sheet.hasKey("border")) {
    //         this.border = sheet.getColour("border");
    //     }
    //     if (sheet.hasKey("border-thickness")) {
    //         this.borderThickness = sheet.get("border-thickness");
    //     }
    // }

    public void setSize(Region2d region) {
        this.x = region.minX;
        this.y = region.minY;
        this.width = region.getWidth();
        this.height = region.getHeight();
    }

    /**
     * Gets the location of this control relative to its parent
     * <p>
     * This location is based on the top-left corner
     * </p>
     */
    public Vector2 getLocation() {
        return new Vector2(this.margin.left, this.margin.top);
    }

    public double getLocationX() {
        return this.margin.left;
    }

    public double getLocationY() {
        return this.margin.top;
    }

    public Vector2 getAbsoluteLocation() {
        double x = this.margin.left;
        double y = this.margin.top;
        Control parent;
        while ((parent = this.parent) != null) {
            x += parent.margin.left;
            y += parent.margin.top;
        }

        return new Vector2(x, y);
    }

    public double getAbsoluteX() {
        double x = this.margin.left;
        Control parent;
        while ((parent = this.parent) != null) {
            x += parent.margin.left;
        }

        return x;
    }

    public double getAbsoluteY() {
        double y = this.margin.top;
        Control parent;
        while ((parent = this.parent) != null) {
            y += parent.margin.top;
        }

        return y;
    }

    public void onPreviewMouseEnter(MouseMoveEvent e) { onMouseEnter(e); }

    public void onPreviewMouseLeave(MouseMoveEvent e) { onMouseLeave(e); }

    public void onPreviewMouseMove(MouseMoveEvent e) { onMouseMove(e); }

    public void onPreviewMouseButton(MouseButtonEvent e) { onMouseButton(e); }

    public void onPreviewMouseUp(MouseButtonEvent e) { onMouseUp(e); }

    public void onPreviewMouseDown(MouseButtonEvent e) { onMouseDown(e); }

    public void onPreviewKey(KeyEvent e) { onKey(e);}

    public void onPreviewKeyUp(KeyEvent e) { onKeyUp(e); }

    public void onPreviewKeyDown(KeyEvent e) { onKeyDown(e); }

    public void onPreviewHorizontalAlignChanged(HorizontalAlignChangedEvent e) { onHorizontalAlignChanged(e); }

    public void onPreviewVerticalAlignChanged(VerticalAlignChangedEvent e) { onVerticalAlignChanged(e); }


    public void onMouseEnter(MouseMoveEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onMouseEnter(e);
    }

    public void onMouseLeave(MouseMoveEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onMouseLeave(e);
    }

    public void onMouseMove(MouseMoveEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onMouseMove(e);
    }

    public void onMouseButton(MouseButtonEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onMouseButton(e);
    }

    public void onMouseUp(MouseButtonEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onMouseUp(e);
    }

    public void onMouseDown(MouseButtonEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onMouseDown(e);
    }

    public void onKey(KeyEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onKey(e);
    }

    public void onKeyUp(KeyEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onKeyUp(e);
    }

    public void onKeyDown(KeyEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onKeyDown(e);
    }

    public void onHorizontalAlignChanged(HorizontalAlignChangedEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onHorizontalAlignChanged(e);
    }

    public void onVerticalAlignChanged(VerticalAlignChangedEvent e) {
        if (this.parent == null || e.isCancelled()) return;
        this.parent.onVerticalAlignChanged(e);
    }

    public void onParentResized(WindowResizeEvent event) {
        this.renderNextTick = true;
        // if (this.horizontalAlignment == HorizontalAlignment.STRETCH) {
        //     this.width += event.getNewWidth() - this.margin.left - this.margin.right;
        // }
        // else if (this.horizontalAlignment == HorizontalAlignment.CENTER) {
        //     this.margin.left += (event.getChangeX() / 2);
        // }
        // else if (this.horizontalAlignment == HorizontalAlignment.RIGHT) {
        //     this.margin.left += event.getChangeX();
        // }
        // if (this.verticalAlignment == VerticalAlignment.STRETCH) {
        //     this.height += event.getChangeY();
        // }
        // else if (this.verticalAlignment == VerticalAlignment.CENTER) {
        //     this.margin.top += (event.getChangeY() / 2);
        // }
        // else if (this.verticalAlignment == VerticalAlignment.BOTTOM) {
        //     this.height += event.getChangeY();
        // }
    }

    public void onGainedFocus() {

    }

    public void onLostFocus() {

    }

    public boolean canLoseFocus() {
        return true;
    }

    public void tick(GuiGLEngine engine) {

    }

    public boolean tryFocus() {
        if (focused == null) {
            lastFocused = null;
            focused = this;
            onGainedFocus();
            return true;
        }
        else if (focused.canLoseFocus()) {
            lastFocused = focused;
            focused = this;
            lastFocused.onLostFocus();
            focused.onGainedFocus();
            return true;
        }
        else {
            return false;
        }
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }
}