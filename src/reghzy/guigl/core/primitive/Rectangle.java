package reghzy.guigl.core.primitive;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.core.utils.ColourARGB;
import reghzy.guigl.core.utils.Thickness;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.primitive.RenderRectangle;

public class Rectangle extends Control {
    public ColourARGB activeBackground;
    public ColourARGB mouseOverBackground;
    public ColourARGB defaultBackground;

    public static Rectangle newRect(Vector2d margin, Vector2d size) {
        Rectangle rectangle = new Rectangle();
        rectangle.disableAutoGlobalUpdate();
        rectangle.setMargin(new Thickness(margin.x, margin.y, 0.0f, 0.0f));
        rectangle.setSize(size);
        rectangle.enableAutoGlobalUpdate();
        return rectangle;
    }

    public Rectangle() {
        this.activeBackground = defaultBackground;
    }

    @Override
    public void onMouseEnter(MouseMoveEvent e) {
        super.onMouseEnter(e);
        this.activeBackground = this.mouseOverBackground;
    }

    @Override
    public void onMouseLeave(MouseMoveEvent e) {
        super.onMouseLeave(e);
        this.activeBackground = this.defaultBackground;
    }

    @Override
    public Class<? extends ControlRenderer<?>> getRenderClass() {
        return RenderRectangle.class;
    }

    public ColourARGB getActiveBackground() {
        if (this.activeBackground == null) {
            return this.activeBackground = this.defaultBackground;
        }

        return this.activeBackground;
    }
}
