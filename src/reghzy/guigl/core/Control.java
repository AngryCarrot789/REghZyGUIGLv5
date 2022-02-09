package reghzy.guigl.core;

import reghzy.guigl.core.utils.Thickness;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.RenderData;

public abstract class Control {
    protected Thickness margin;
    public Control parent;
    protected double targetWidth;
    protected double targetHeight;

    protected boolean disableAutoGlobalUpdate = false;

    // Special info about this control, regarding rendering info
    // this should not be modified externally, only by the GuiGL/rendering engine
    public final RenderData render = new RenderData(this);

    public Control() {
        this.margin = Thickness.ZERO;
        setTargetWidth(8);
        setTargetHeight(8);
    }

    public Vector2d getAbsolutePos() {
        if (this.render.isPosBaked) {
            return this.render.pos.getCopy();
        }

        double x = this.margin.left;
        double y = this.margin.top;
        Control parent;
        while ((parent = this.parent) != null) {
            x += parent.margin.left;
            y += parent.margin.top;
        }

        this.render.isPosBaked = true;
        return this.render.pos.set(x, y).getCopy();
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

    public Region2d getRegion() {
        return Region2d.get(getAbsolutePos(), getAbsolutePos().translate(getActualSize()));
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
}
