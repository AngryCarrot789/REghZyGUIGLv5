package reghzy.guigl.core.event.events;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.Cancellable;
import reghzy.guigl.core.event.RoutedEvent;
import reghzy.guigl.maths.Vector2d;

@Cancellable
public class MouseMoveEvent extends RoutedEvent {
    private final Vector2d oldPos;
    private final Vector2d newPos;

    public MouseMoveEvent(Vector2d oldPos, Vector2d newPos) {
        this.oldPos = oldPos;
        this.newPos = newPos;
    }

    public double getChangeX() {
        return this.newPos.x - this.oldPos.x;
    }

    public double getChangeY() {
        return this.newPos.y - this.oldPos.y;
    }

    public Vector2d getChange() {
        return Vector2d.get(this.newPos).subtract(this.oldPos);
    }

    public Vector2d getOld() {
        return oldPos;
    }

    public Vector2d getNew() {
        return newPos;
    }

    public Vector2d getNewRelativeTo(Control control) {
        return this.newPos.getCopy().subtract(control.getAbsolutePos());
    }

    public Vector2d getOldRelativeTo(Control control) {
        return this.newPos.getCopy().subtract(control.getAbsolutePos());
    }
}
