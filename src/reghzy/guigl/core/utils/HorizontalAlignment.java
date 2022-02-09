package reghzy.guigl.core.utils;

public enum HorizontalAlignment {
    LEFT,
    CENTER,
    RIGHT,
    STRETCH;

    public boolean isWidthAffected() {
        return this != STRETCH;
    }
}