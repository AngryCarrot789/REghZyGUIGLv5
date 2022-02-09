package reghzy.guigl.utils.reflect;

public interface PrimitiveFieldAccessor<T, V> extends FieldAccessor<T, V> {
    byte getByte(T target);

    short getShort(T target);

    int getInt(T target);

    long getLong(T target);

    float getFloat(T target);

    double getDouble(T target);

    boolean getBool(T target);

    char getChar(T target);

    void setByte(T target, byte value);

    void setShort(T target, short value);

    void setInt(T target, int value);

    void setLong(T target, long value);

    void setFloat(T target, float value);

    void setDouble(T target, double value);

    void setBool(T target, boolean value);

    void setChar(T target, char value);
}
