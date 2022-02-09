package reghzy.guigl.core.router;

import reghzy.guigl.core.Control;

public class TunnelHitPair {
    public final Control control;
    public boolean hitNewPos;
    public boolean hitOldPos;

    public TunnelHitPair(Control control) {
        this.control = control;
    }
}
