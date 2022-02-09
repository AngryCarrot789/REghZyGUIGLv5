package reghzy.guigl.core.utils;

public final class ColourARGB {
    public float r;
    public float g;
    public float b;
    public float a;

    public static final ColourARGB RED = new ColourARGB(1.0f, 0.0f, 0.0f);
    public static final ColourARGB GREEN = new ColourARGB(0.0f, 1.0f, 0.0f);
    public static final ColourARGB BLUE = new ColourARGB(0.0f, 0.0f, 1.0f);
    public static final ColourARGB CYAN = new ColourARGB(0.1f, 0.72f, 0.95f);

    public static final ColourARGB DARK_GREY = new ColourARGB(0xFF252525);
    public static final ColourARGB BLACK = new ColourARGB(0xFF000000);

    public ColourARGB(int int32, boolean useAlpha) {
        if (useAlpha) {
            this.a = ((int32 >> 24) & 255) / 255.0f;
        }
        else {
            this.a = 1.0f;
        }

        this.r = ((int32 >> 16) & 255) / 255.0f;
        this.g = ((int32 >>  8) & 255) / 255.0f;
        this.b = ((int32) & 255) / 255.0f;
    }

    public ColourARGB(int int32) {
        this(int32, true);
    }

    public ColourARGB(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }

    public ColourARGB(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static ColourARGB fromBytes(byte red, byte green, byte blue) {
        return new ColourARGB((float) red / 255.0f, (float) green / 255.0f, (float) blue / 255.0f, 1.0f);
    }

    public static ColourARGB fromBytes(byte red, byte green, byte blue, byte alpha) {
        return new ColourARGB((float) red / 255.0f, (float) green / 255.0f, (float) blue / 255.0f, (float) alpha / 255.0f);
    }

    public byte redToByte() {
        return (byte) ((byte) (this.r * 255.0f) & 255);
    }

    public byte greenToByte() {
        return (byte) ((byte) (this.g * 255.0f) & 255);
    }

    public byte blueToByte() {
        return (byte) ((byte) (this.b * 255.0f) & 255);
    }

    public byte alphaToByte() {
        return (byte) ((byte) (this.a * 255.0f) & 255);
    }

    public int toInt32() {
        int r = (int) (this.r * 255.0f) & 255;
        int g = (int) (this.r * 255.0f) & 255;
        int b = (int) (this.r * 255.0f) & 255;
        int a = (int) (this.r * 255.0f) & 255;
        return (r << 24) | (g << 16) | (b << 8) | a;
    }
}
