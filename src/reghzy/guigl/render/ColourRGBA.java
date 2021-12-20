package reghzy.guigl.render;

public final class ColourRGBA {
    public float r;
    public float g;
    public float b;
    public float a;

    public ColourRGBA(int int32, boolean useAlpha) {
        this.r = ((int32 >> 24) & 255) / 255.0f;
        this.g = ((int32 >> 16) & 255) / 255.0f;
        this.b = ((int32 >>  8) & 255) / 255.0f;
        if (useAlpha) {
            this.a = ((int32) & 255) / 255.0f;
        }
        else {
            this.a = 1.0f;
        }
    }

    public ColourRGBA(int int32) {
        this(int32, false);
    }

    public ColourRGBA(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }

    public ColourRGBA(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColourRGBA(byte red, byte green, byte blue) {
        this.r = (float) red / 255.0f;
        this.g = (float) green / 255.0f;
        this.b = (float) blue / 255.0f;
        this.a = 1.0f;
    }

    public ColourRGBA(byte red, byte green, byte blue, byte alpha) {
        this.r = (float) red / 255.0f;
        this.g = (float) green / 255.0f;
        this.b = (float) blue / 255.0f;
        this.a = (float) alpha / 255.0f;
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
