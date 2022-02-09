package reghzy.guigl.core.utils;

/**
 * A simple helper class for wrapping a boolean "toggle"
 */
public class Toggle {
    private boolean isToggled;

    public boolean isToggled() {
        return this.isToggled;
    }

    public void toggle() {
        this.isToggled = !this.isToggled;
    }
}
