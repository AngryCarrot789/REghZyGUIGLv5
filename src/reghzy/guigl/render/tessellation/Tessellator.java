package reghzy.guigl.render.tessellation;

import org.lwjgl.opengl.GL11;
import reghzy.guigl.maths.Vector3f;
import reghzy.guigl.maths.Vector4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Tessellator {
    /**
     * The byte buffer used for GL allocation.
     */
    private ByteBuffer byteBuffer;

    /**
     * The same memory as byteBuffer, but referenced as an integer buffer.
     */
    private IntBuffer intBuffer;

    /**
     * The same memory as byteBuffer, but referenced as an float buffer.
     */
    private FloatBuffer floatBuffer;

    /**
     * The same memory as byteBuffer, but referenced as an short buffer.
     */
    private ShortBuffer shortBuffer;

    /**
     * Raw integer array.
     */
    private int[] rawBuffer;

    /**
     * The number of vertices to be drawn in the next draw call. Reset to 0 between draw calls.
     */
    private int vertexCount;

    /**
     * The first coordinate to be used for the texture.
     */
    private double textureU;

    /**
     * The second coordinate to be used for the texture.
     */
    private double textureV;
    private int brightness;

    /**
     * The color (RGBA) value to be used for the following draw call.
     */
    private int color;

    /**
     * Whether the current draw object for this tessellator has color values.
     */
    private boolean hasColor;

    /**
     * Whether the current draw object for this tessellator has texture coordinates.
     */
    private boolean hasTexture;
    private boolean hasBrightness;

    /**
     * Whether the current draw object for this tessellator has normal values.
     */
    private boolean hasNormals;

    /**
     * The index into the raw buffer to be used for the next data.
     */
    private int rawBufferIndex;

    /**
     * The number of vertices manually added to the given draw call. This differs from vertexCount because it adds extra
     * vertices when converting quads to triangles.
     */
    private int addedVertices;

    /**
     * Disables all color information for the following draw call.
     */
    private boolean isColorDisabled;

    /**
     * The draw mode currently being used by the tessellator.
     */
    public int drawMode;

    /**
     * An offset to be applied along the x-axis for all vertices in this draw call.
     */
    public double offsetX;

    /**
     * An offset to be applied along the y-axis for all vertices in this draw call.
     */
    public double offsetY;

    /**
     * An offset to be applied along the z-axis for all vertices in this draw call.
     */
    public double offsetZ;

    /**
     * The normal to be applied to the face being drawn.
     */
    private int normal;

    /**
     * Whether this tessellator is currently in draw mode.
     */
    public boolean isDrawing;

    /**
     * The size of the buffers used (in integers).
     */
    private int bufferSize;

    public boolean autoGrow;

    private static final boolean littleEndian = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;

    public Tessellator(int bufferSize) {
        this.autoGrow = true;
        this.bufferSize = bufferSize;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSize * 4);
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.shortBuffer = this.byteBuffer.asShortBuffer();
        this.rawBuffer = new int[bufferSize];
    }

    /**
     * Draws the data set up in this tessellator and resets the state to prepare for new drawing.
     */
    public int draw() {
        if (this.isDrawing) {
            this.isDrawing = false;

            if (this.vertexCount > 0) {
                this.intBuffer.clear();
                this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
                this.byteBuffer.position(0);
                this.byteBuffer.limit(this.rawBufferIndex * 4);

                if (this.hasTexture) {
                    this.floatBuffer.position(3);
                    GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, this.floatBuffer);
                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                }

                if (this.hasBrightness) {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                    this.shortBuffer.position(14);
                    GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, this.shortBuffer);
                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                }

                if (this.hasColor) {
                    this.byteBuffer.position(20);
                    GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 32, this.byteBuffer);
                    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
                }

                if (this.hasNormals) {
                    this.byteBuffer.position(24);
                    GL11.glNormalPointer(GL11.GL_FLOAT, 32, this.byteBuffer);
                    GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
                }

                this.floatBuffer.position(0);
                GL11.glVertexPointer(3, GL11.GL_FLOAT, 32, this.floatBuffer);
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                GL11.glDrawArrays(this.drawMode, 0, this.vertexCount);
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

                if (this.hasTexture) {
                    GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                }

                if (this.hasBrightness) {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                    GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                }

                if (this.hasColor) {
                    GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
                }

                if (this.hasNormals) {
                    GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
                }
            }

            int index = this.rawBufferIndex * 4;
            this.reset();
            return index;
        }
        else {
            throw new IllegalStateException("Not tessellating!");
        }
    }

    /**
     * Clears the tessellator state in preparation for new drawing.
     */
    private void reset() {
        this.vertexCount = 0;
        this.byteBuffer.clear();
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }

    /**
     * Sets draw mode in the tessellator to draw quads.
     */
    public void startDrawingQuads() {
        this.startDrawing(GL11.GL_QUADS);
    }

    /**
     * Resets tessellator state and prepares for drawing (with the specified draw mode).
     */
    public void startDrawing(int glMode) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already tessellating!");
        }
        else {
            this.isDrawing = true;
            this.reset();
            this.drawMode = glMode;
            this.hasNormals = false;
            this.hasColor = false;
            this.hasTexture = false;
            this.hasBrightness = false;
            this.isColorDisabled = false;
        }
    }

    /**
     * Sets the texture coordinates.
     */
    public void setTextureUV(double u, double v) {
        this.hasTexture = true;
        this.textureU = u;
        this.textureV = v;
    }

    public void setBrightness(int brightness) {
        this.hasBrightness = true;
        this.brightness = brightness;
    }

    /**
     * Sets the RGB values as specified, converting from floats between 0 and 1 to integers from 0-255.
     */
    public void setColourOpaque(float par1, float par2, float par3) {
        this.setColourOpaque((int) (par1 * 255.0F), (int) (par2 * 255.0F), (int) (par3 * 255.0F));
    }

    /**
     * Sets the RGBA values for the color, converting from floats between 0 and 1 to integers from 0-255.
     */
    public void setColourRGBA(float par1, float par2, float par3, float par4) {
        this.setColourRGBA((int) (par1 * 255.0F), (int) (par2 * 255.0F), (int) (par3 * 255.0F), (int) (par4 * 255.0F));
    }

    /**
     * Sets the RGB values as specified, and sets alpha to opaque.
     */
    public void setColourOpaque(int r, int g, int b) {
        this.setColourRGBA(r, g, b, 255);
    }

    /**
     * Sets the RGBA values for the color. Also clamps them to 0-255.
     */
    public void setColourRGBA(int r, int g, int b, int a) {
        if (!this.isColorDisabled) {
            this.hasColor = true;
            r = (r < 0 ? 0 : (r > 255 ? 255 : r));
            g = (g < 0 ? 0 : (g > 255 ? 255 : g));
            b = (b < 0 ? 0 : (b > 255 ? 255 : b));
            a = (a < 0 ? 0 : (a > 255 ? 255 : a));
            if (littleEndian) {
                this.color = a << 24 | b << 16 | g << 8 | r;
            }
            else {
                this.color = r << 24 | g << 16 | b << 8 | a;
            }
        }
    }

    public void setColourRGBA(byte r, byte g, byte b) {
        this.setColourOpaque(r & 255, g & 255, b & 255);
    }

    /**
     * Adds a vertex specifying both x,y,z and the texture u,v for it.
     */
    public void addVertexWithUV(double x, double y, double z, double u, double v) {
        this.setTextureUV(u, v);
        this.addVertex(x, y, z);
    }

    public void addVertex(Vector3f vec) {
        addVertex(vec.x, vec.y, vec.z);
    }

    public void addVertex(Vector4f vec) {
        addVertex(vec.homogenised());
    }

    /**
     * Adds a vertex with the specified x,y,z to the current draw call. It will trigger a draw() if the buffer gets full
     */
    public void addVertex(double x, double y, double z) {
        if (this.rawBufferIndex >= this.bufferSize - 32 && this.autoGrow) {
            this.bufferSize *= 2;
            int[] newRawBuffer = new int[this.bufferSize];
            System.arraycopy(this.rawBuffer, 0, newRawBuffer, 0, this.rawBuffer.length);
            this.rawBuffer = newRawBuffer;
            this.byteBuffer = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
            this.intBuffer = this.byteBuffer.asIntBuffer();
            this.floatBuffer = this.byteBuffer.asFloatBuffer();
            this.shortBuffer = this.byteBuffer.asShortBuffer();
        }

        ++this.addedVertices;
        if (this.hasTexture) {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float) this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float) this.textureV);
        }

        if (this.hasBrightness) {
            this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
        }

        if (this.hasColor) {
            this.rawBuffer[this.rawBufferIndex + 5] = this.color;
        }

        if (this.hasNormals) {
            this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
        }

        this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float) (x + this.offsetX));
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float) (y + this.offsetY));
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float) (z + this.offsetZ));
        this.rawBufferIndex += 8;
        ++this.vertexCount;

        if (this.autoGrow || this.addedVertices % 4 != 0 || this.rawBufferIndex < this.bufferSize - 32) {
            return;
        }

        throw new RuntimeException("Draw buffer overflow; too many vertices. Added = " + this.addedVertices + ", bufferSize = " + this.bufferSize + ", rawIndex = " + this.rawBufferIndex);

        // this.draw();
        // this.isDrawing = true;
    }

    /**
     * Sets the color to the given opaque value (stored as byte values packed in an integer).
     */
    public void setColourOpaque(int colour) {
        int r = colour >> 16 & 255;
        int g = colour >> 8 & 255;
        int b = colour & 255;
        this.setColourOpaque(r, g, b);
    }

    /**
     * Sets the color to the given color (packed as bytes in integer) and alpha values.
     */
    public void setColourRGBA(int rgb, int alpha) {
        int r = rgb >> 16 & 255;
        int g = rgb >> 8 & 255;
        int b = rgb & 255;
        this.setColourRGBA(r, g, b, alpha);
    }

    /**
     * Disables colors for the current draw call.
     */
    public void disableColour() {
        this.isColorDisabled = true;
    }

    /**
     * Sets the normal for the current draw call.
     */
    public void setNormal(float par1, float par2, float par3) {
        this.hasNormals = true;
        byte var4 = (byte) ((int) (par1 * 127.0F));
        byte var5 = (byte) ((int) (par2 * 127.0F));
        byte var6 = (byte) ((int) (par3 * 127.0F));
        this.normal = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
    }

    /**
     * Sets the translation for all vertices in the current draw call.
     */
    public void setTranslation(double par1, double par3, double par5) {
        this.offsetX = par1;
        this.offsetY = par3;
        this.offsetZ = par5;
    }

    /**
     * Offsets the translation for all vertices in the current draw call.
     */
    public void addTranslation(double x, double y, double z) {
        this.offsetX += x;
        this.offsetY += y;
        this.offsetZ += z;
    }

    private void draw(int startQuadVertex, int endQuadVertex) {
        int vxQuadCount = endQuadVertex - startQuadVertex;
        if (vxQuadCount > 0) {
            int startVertex = startQuadVertex * 4;
            int vxCount = vxQuadCount * 4;
            this.floatBuffer.position(3);
            GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, this.floatBuffer);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            this.shortBuffer.position(14);
            GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, this.shortBuffer);
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            this.byteBuffer.position(20);
            GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 32, this.byteBuffer);
            this.floatBuffer.position(0);
            GL11.glVertexPointer(3, GL11.GL_FLOAT, 32, this.floatBuffer);
            GL11.glDrawArrays(this.drawMode, startVertex, vxCount);
        }
    }
}
