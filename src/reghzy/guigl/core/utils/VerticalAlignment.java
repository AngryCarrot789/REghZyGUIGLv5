package reghzy.guigl.core.utils;

public enum VerticalAlignment {
    TOP,
    CENTER,
    BOTTOM,
    STRETCH;

    public boolean isHeightAffected() {
        return this != STRETCH;
    }
}
