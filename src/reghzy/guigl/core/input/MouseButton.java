package reghzy.guigl.core.input;

import org.lwjgl.glfw.GLFW;

public enum MouseButton {
    left(GLFW.GLFW_MOUSE_BUTTON_LEFT),
    right(GLFW.GLFW_MOUSE_BUTTON_RIGHT),
    middle(GLFW.GLFW_MOUSE_BUTTON_MIDDLE),
    button4(GLFW.GLFW_MOUSE_BUTTON_4),
    button5(GLFW.GLFW_MOUSE_BUTTON_5),
    button6(GLFW.GLFW_MOUSE_BUTTON_6),
    button7(GLFW.GLFW_MOUSE_BUTTON_7),
    button8(GLFW.GLFW_MOUSE_BUTTON_8);

    public final int code;
    MouseButton(int code) {
        this.code = code;
    }

    public static MouseButton fromCode(int code) {
        return values()[code];
    }

    public boolean equals(MouseButton button) {
        return button.code == this.code;
    }

    @Override
    public String toString() {
        return "MouseButton{code:" + code + "}";
    }
}