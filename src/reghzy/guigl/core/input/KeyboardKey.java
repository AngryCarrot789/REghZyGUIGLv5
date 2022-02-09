package reghzy.guigl.core.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.NonnullDefault;

import java.util.HashMap;

public enum KeyboardKey {
    space(GLFW.GLFW_KEY_SPACE),
    apostrophy(GLFW.GLFW_KEY_APOSTROPHE),
    comma(GLFW.GLFW_KEY_COMMA),
    minus(GLFW.GLFW_KEY_MINUS),
    period(GLFW.GLFW_KEY_PERIOD),
    backSlash(GLFW.GLFW_KEY_BACKSLASH),
    forwardSlash(GLFW.GLFW_KEY_SLASH),
    semiColon(GLFW.GLFW_KEY_SEMICOLON),
    equals(GLFW.GLFW_KEY_EQUAL),
    leftBracket(GLFW.GLFW_KEY_LEFT_BRACKET),
    rightBracket(GLFW.GLFW_KEY_RIGHT_BRACKET),
    grave(GLFW.GLFW_KEY_GRAVE_ACCENT),
    escape(GLFW.GLFW_KEY_ESCAPE),
    enter(GLFW.GLFW_KEY_ENTER),
    tab(GLFW.GLFW_KEY_TAB),
    backspace(GLFW.GLFW_KEY_BACKSPACE),
    insert(GLFW.GLFW_KEY_INSERT),
    delete(GLFW.GLFW_KEY_DELETE),
    right(GLFW.GLFW_KEY_RIGHT),
    left(GLFW.GLFW_KEY_LEFT),
    down(GLFW.GLFW_KEY_DOWN),
    up(GLFW.GLFW_KEY_UP),
    pageUp(GLFW.GLFW_KEY_PAGE_UP),
    pageDown(GLFW.GLFW_KEY_PAGE_DOWN),
    home(GLFW.GLFW_KEY_HOME),
    end(GLFW.GLFW_KEY_END),
    pause(GLFW.GLFW_KEY_PAUSE),
    capsLock(GLFW.GLFW_KEY_CAPS_LOCK),
    scrollLock(GLFW.GLFW_KEY_SCROLL_LOCK),
    numLock(GLFW.GLFW_KEY_NUM_LOCK),
    printScreen(GLFW.GLFW_KEY_PRINT_SCREEN),
    kpDecimal(GLFW.GLFW_KEY_KP_DECIMAL),
    kpDevice(GLFW.GLFW_KEY_KP_DIVIDE),
    kpMultiply(GLFW.GLFW_KEY_KP_MULTIPLY),
    kpSubtract(GLFW.GLFW_KEY_KP_SUBTRACT),
    kpAdd(GLFW.GLFW_KEY_KP_ADD),
    kpEnter(GLFW.GLFW_KEY_KP_ENTER),
    kpEquals(GLFW.GLFW_KEY_KP_EQUAL),
    shiftLeft(GLFW.GLFW_KEY_LEFT_SHIFT),
    controlLeft(GLFW.GLFW_KEY_LEFT_CONTROL),
    altLeft(GLFW.GLFW_KEY_LEFT_ALT),
    superLeft(GLFW.GLFW_KEY_LEFT_SUPER),
    shiftRight(GLFW.GLFW_KEY_RIGHT_SHIFT),
    controlRight(GLFW.GLFW_KEY_RIGHT_CONTROL),
    altRight(GLFW.GLFW_KEY_RIGHT_ALT),
    superRight(GLFW.GLFW_KEY_RIGHT_SUPER),
    menu(GLFW.GLFW_KEY_MENU),
    function1(GLFW.GLFW_KEY_F1),
    function2(GLFW.GLFW_KEY_F2),
    function3(GLFW.GLFW_KEY_F3),
    function4(GLFW.GLFW_KEY_F4),
    function5(GLFW.GLFW_KEY_F5),
    function6(GLFW.GLFW_KEY_F6),
    function7(GLFW.GLFW_KEY_F7),
    function8(GLFW.GLFW_KEY_F8),
    function9(GLFW.GLFW_KEY_F9),
    function10(GLFW.GLFW_KEY_F10),
    function11(GLFW.GLFW_KEY_F11),
    function12(GLFW.GLFW_KEY_F12),
    function13(GLFW.GLFW_KEY_F13),
    function14(GLFW.GLFW_KEY_F14),
    function15(GLFW.GLFW_KEY_F15),
    function16(GLFW.GLFW_KEY_F16),
    function17(GLFW.GLFW_KEY_F17),
    function18(GLFW.GLFW_KEY_F18),
    function19(GLFW.GLFW_KEY_F19),
    function20(GLFW.GLFW_KEY_F20),
    function21(GLFW.GLFW_KEY_F21),
    function22(GLFW.GLFW_KEY_F22),
    function23(GLFW.GLFW_KEY_F23),
    function24(GLFW.GLFW_KEY_F24),
    function25(GLFW.GLFW_KEY_F25),
    kp0(GLFW.GLFW_KEY_KP_0),
    kp1(GLFW.GLFW_KEY_KP_1),
    kp2(GLFW.GLFW_KEY_KP_2),
    kp3(GLFW.GLFW_KEY_KP_3),
    kp4(GLFW.GLFW_KEY_KP_4),
    kp5(GLFW.GLFW_KEY_KP_5),
    kp6(GLFW.GLFW_KEY_KP_6),
    kp7(GLFW.GLFW_KEY_KP_7),
    kp8(GLFW.GLFW_KEY_KP_8),
    kp9(GLFW.GLFW_KEY_KP_9),
    num0(GLFW.GLFW_KEY_0),
    num1(GLFW.GLFW_KEY_1),
    num2(GLFW.GLFW_KEY_2),
    num3(GLFW.GLFW_KEY_3),
    num4(GLFW.GLFW_KEY_4),
    num5(GLFW.GLFW_KEY_5),
    num6(GLFW.GLFW_KEY_6),
    num7(GLFW.GLFW_KEY_7),
    num8(GLFW.GLFW_KEY_8),
    num9(GLFW.GLFW_KEY_9),
    keyA(GLFW.GLFW_KEY_A),
    keyB(GLFW.GLFW_KEY_B),
    keyC(GLFW.GLFW_KEY_C),
    keyD(GLFW.GLFW_KEY_D),
    keyE(GLFW.GLFW_KEY_E),
    keyF(GLFW.GLFW_KEY_F),
    keyG(GLFW.GLFW_KEY_G),
    keyH(GLFW.GLFW_KEY_H),
    keyI(GLFW.GLFW_KEY_I),
    keyJ(GLFW.GLFW_KEY_J),
    keyK(GLFW.GLFW_KEY_K),
    keyL(GLFW.GLFW_KEY_L),
    keyM(GLFW.GLFW_KEY_M),
    keyN(GLFW.GLFW_KEY_N),
    keyO(GLFW.GLFW_KEY_O),
    keyP(GLFW.GLFW_KEY_P),
    keyQ(GLFW.GLFW_KEY_Q),
    keyR(GLFW.GLFW_KEY_R),
    keyS(GLFW.GLFW_KEY_S),
    keyT(GLFW.GLFW_KEY_T),
    keyU(GLFW.GLFW_KEY_U),
    keyV(GLFW.GLFW_KEY_V),
    keyW(GLFW.GLFW_KEY_W),
    keyX(GLFW.GLFW_KEY_X),
    keyY(GLFW.GLFW_KEY_Y),
    keyZ(GLFW.GLFW_KEY_Z),

    last(GLFW.GLFW_KEY_LAST);

    private static final HashMap<Integer, KeyboardKey> MAP = new HashMap<Integer, KeyboardKey>();
    public final int code;
    KeyboardKey(int code) {
        this.code = code;
    }

    public static KeyboardKey fromCode(int code) {
        KeyboardKey key = MAP.get(code);
        if (key == null) {
            throw new RuntimeException("Unknown key with code " + code);
        }

        return key;
    }

    public boolean equals(KeyboardKey key) {
        return key.code == this.code;
    }

    @Override
    public String toString() {
        return "KeyboardKey{name=" + name() + ",code=" + code + "}";
    }

    static {
        for(KeyboardKey key : values()) {
            MAP.put(key.code, key);
        }
    }
}
