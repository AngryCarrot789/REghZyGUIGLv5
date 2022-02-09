package reghzy.guigl.core.primitive;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.utils.ColourARGB;
import reghzy.guigl.core.utils.Thickness;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.primitive.RenderRectangle;

public class Rectangle extends Control {
    public ColourARGB background;

    public static Rectangle newRect(Vector2d margin, Vector2d size) {
        Rectangle rectangle = new Rectangle();
        rectangle.disableAutoGlobalUpdate();
        rectangle.setMargin(new Thickness(margin.x, margin.y, 0.0f, 0.0f));
        rectangle.setSize(size);
        rectangle.enableAutoGlobalUpdate();
        return rectangle;
    }

    @Override
    public Class<? extends ControlRenderer<?>> getRenderClass() {
        return RenderRectangle.class;
    }
}
