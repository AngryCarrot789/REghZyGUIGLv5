package reghzy.guigl.core;

import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.core.event.handlers.RoutedEventStore;
import reghzy.guigl.core.utils.HorizontalAlignment;
import reghzy.guigl.core.utils.Thickness;
import reghzy.guigl.core.utils.VerticalAlignment;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.RenderData;
import reghzy.guigl.utils.RZFormats;

import java.sql.SQLSyntaxErrorException;

public abstract class Control {
    protected Thickness margin;
    public Control parent;
    protected double targetWidth;
    protected double targetHeight;

    protected boolean disableAutoGlobalUpdate = false;

    protected HorizontalAlignment horizontalAlignment;
    protected VerticalAlignment verticalAlignment;

    public final RoutedEventStore<MouseMoveEvent> eventPreviewOnMouseEnter = new RoutedEventStore<MouseMoveEvent>(this);
    public final RoutedEventStore<MouseMoveEvent> eventPreviewOnMouseMove = new RoutedEventStore<MouseMoveEvent>(this);
    public final RoutedEventStore<MouseMoveEvent> eventPreviewOnMouseLeave = new RoutedEventStore<MouseMoveEvent>(this);
    public final RoutedEventStore<MouseMoveEvent> eventOnMouseEnter = new RoutedEventStore<MouseMoveEvent>(this);
    public final RoutedEventStore<MouseMoveEvent> eventOnMouseMove = new RoutedEventStore<MouseMoveEvent>(this);
    public final RoutedEventStore<MouseMoveEvent> eventOnMouseLeave = new RoutedEventStore<MouseMoveEvent>(this);

    protected boolean isPreviewMouseOver;
    protected boolean isMouseOver;

    protected String name;

    // Special info about this control, regarding rendering info
    // this should not be modified externally, only by the GuiGL/rendering engine
    public final RenderData render = new RenderData(this);

    public Control() {
        this.margin = Thickness.ZERO;
        setTargetWidth(8);
        setTargetHeight(8);
    }

    public void onPreviewMouseEnter(MouseMoveEvent e) {
        GuiGLEngine.getInstance().getLogger().infoFormat("{0} -> onPreviewMouseEnter()", getDebugParentChain(true));
    }

    public void onPreviewMouseMove(MouseMoveEvent e) {
        GuiGLEngine.getInstance().getLogger().infoFormat("{0} -> onPreviewMouseMove()", getDebugParentChain(true));
    }

    public void onPreviewMouseLeave(MouseMoveEvent e) {
        GuiGLEngine.getInstance().getLogger().infoFormat("{0} -> onPreviewMouseLeave()", getDebugParentChain(true));
    }

    public void onMouseEnter(MouseMoveEvent e) {
        GuiGLEngine.getInstance().getLogger().infoFormat("{0} -> onMouseEnter()", getDebugParentChain(true));
    }

    public void onMouseMove(MouseMoveEvent e) {
        GuiGLEngine.getInstance().getLogger().infoFormat("{0} -> onMouseMove()", getDebugParentChain(true));
    }

    public void onMouseLeave(MouseMoveEvent e) {
        GuiGLEngine.getInstance().getLogger().infoFormat("{0} -> onMouseLeave()", getDebugParentChain(true));
    }

    public boolean isMouseOver() {
        return this.isMouseOver;
    }

    /**
     * Whether the mouse can hit this control at the given coordinates
     * @param relative Mouse pos, relative to this (0,0 at top left corner)
     * @param actual Actual mouse pos, relative to window
     */
    public boolean isHittable(Vector2d relative, Vector2d actual) {
        return true;
    }

    /**
     * Gets the relative mouse position of this control to the active window
     */
    public Vector2d getMousePosRelative() {
        return GuiGLEngine.getInstance().getRenderEngine().getActiveWindow().getMouse().getMousePos().subtract(getAbsolutePos());
    }

    public Vector2d getAbsolutePos() {
        if (!this.render.isPosBaked) {
            bakePosition();
        }

        return this.render.pos.getCopy();
    }

    public Vector2d getOffsetTopLeft() {
        return Vector2d.get(this.margin.left, this.margin.top);
    }

    protected void bakePosition() {
        double x = this.margin.left;
        double y = this.margin.top;
        Control parent = this.parent;
        while (parent != null) {
            x += parent.margin.left;
            y += parent.margin.top;
            parent = parent.parent;
        }

        this.render.isPosBaked = true;
        this.render.pos.set(x, y);
    }

    public double getActualWidth() {
        return getActualSize().x;
    }

    public double getActualHeight() {
        return getActualSize().y;
    }

    public Vector2d getActualSize() {
        if (!this.render.isSizeBaked) {
            bakeSize();
        }

        return this.render.size.getCopy();
    }

    protected void bakeSize() {
        this.render.isSizeBaked = true;
        this.render.size.set(this.targetWidth, this.targetHeight);
    }

    public Region2d getRelativeRegion() {
        return Region2d.get(Vector2d.get(0.0d), getActualSize());
    }

    public Region2d getScreenRegion() {
        return Region2d.get(getAbsolutePos(), getAbsolutePos().translate(getActualSize()));
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        markForGlobalUpdate();
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        markForGlobalUpdate();
    }

    public <T extends Control> T setDepth(float depth) {
        this.render.renderDepth = depth;
        return (T) this;
    }

    public void setTargetWidth(double targetWidth) {
        this.targetWidth = targetWidth;
        markForGlobalUpdate();
    }

    public void setTargetHeight(double targetHeight) {
        this.targetHeight = targetHeight;
        markForGlobalUpdate();
    }

    public void setSize(Vector2d size) {
        this.targetWidth = size.x;
        this.targetHeight = size.y;
        markForGlobalUpdate();
    }

    public Thickness getMargin() {
        return this.margin;
    }

    public void setMargin(Thickness margin) {
        if (margin == null) {
            throw new RuntimeException("New margin cannot be null");
        }

        this.margin = margin;
        markForGlobalUpdate();
    }

    public boolean isDisableAutoGlobalUpdate() {
        return disableAutoGlobalUpdate;
    }

    /**
     * This should only be used during the control's initialisation, otherwise the control and its render data may become synchronised
     */
    public void setDisableAutoGlobalUpdate(boolean disableAutoGlobalUpdate) {
        this.disableAutoGlobalUpdate = disableAutoGlobalUpdate;
    }

    /**
     * This should only be used during the control's initialisation, otherwise the control and its render data may become synchronised
     */
    public void disableAutoGlobalUpdate() {
        this.disableAutoGlobalUpdate = true;
    }

    /**
     * This should only be used during the control's initialisation, otherwise the control and its render data may become synchronised
     */
    public void enableAutoGlobalUpdate() {
        this.disableAutoGlobalUpdate = true;
    }

    /**
     * Marks this object as requiring a new update and render
     */
    public void markForGlobalUpdate() {
        if (this.disableAutoGlobalUpdate) {
            return;
        }

        markForReRender();
    }

    public void markForReRender() {
        this.render.needsRender = true;
        markPosChanged();
    }

    public void markPosChanged() {
        this.render.isPosBaked = false;
        this.render.isSizeBaked = false;
    }

    public abstract Class<? extends ControlRenderer<?>> getRenderClass();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDebugName() {
        if (this.name == null) {
            return this.getClass().getSimpleName();
        }
        else {
            return RZFormats.format("{0}({1})", this.getClass().getSimpleName(), this.name);
        }
    }

    public String getDebugParentChain(boolean includeThis) {
        StringBuilder sb = new StringBuilder(128);

        Control parent = this.parent;
        while (parent != null) {
            sb.append(parent.getDebugName()).append('\\');
            parent = parent.parent;
        }

        if (includeThis) {
            return sb.append(this.getDebugName()).toString();
        }
        else {
            sb.setLength(sb.length() - 1);
            return sb.toString();
        }
    }
}
