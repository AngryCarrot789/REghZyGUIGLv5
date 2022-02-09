package reghzy.guigl.utils.reflect;

import org.jetbrains.annotations.NotNull;
import reghzy.guigl.utils.RZFormats;

import java.lang.reflect.Field;

/**
 * A field accessor that uses reflection to get/set fields
 * @param <T> The type of class in which the field is defined in (e.g ArrayList for elementData)
 * @param <V> The field's value type
 */
public class ReflectFieldAccessor<T, V> implements PrimitiveFieldAccessor<T, V> {
    private final Class<T> ownerClass;
    private final String fieldName;
    private final Field field;

    /**
     * Crates a reflect field accessor that uses the given field
     */
    public ReflectFieldAccessor(@NotNull Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        this.field = field;
        this.ownerClass = (Class<T>) field.getDeclaringClass();
        this.fieldName = field.getName();
    }

    private ReflectFieldAccessor(Class<T> owner, Field field, String fieldName) {
        this.ownerClass = owner;
        this.field = field;
        this.fieldName = fieldName;
    }

    /**
     * Creates a field accessor that uses reflection to get/set the field
     * @param targetClass The class in which the given field is stored in (can be a derived class, where the field is stored in a super class)
     * @param fieldType   The class/type of the field. This will be checked against the actual field in the given target class
     * @param fieldName   Name of the field
     * @param <T>         Target class type
     * @param <V>         Field type
     * @return A field accessor
     */
    public static <T, V> ReflectFieldAccessor<T, V> create(Class<T> targetClass, Class<V> fieldType, String fieldName) {
        Field field = Reflect.findPrivateField(targetClass, fieldName);
        if (fieldType.isAssignableFrom(field.getType())) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            return new ReflectFieldAccessor<T, V>(targetClass, field, fieldName);
        }

        throw new RuntimeException(RZFormats.format("Incompatible field type. Field {0} cannot be assigned to {1}", field.getType().getName(), fieldType.getName()));
    }

    /**
     * Creates a field accessor that uses reflection to get/set the field
     * <p>
     *     This bypasses the field type checks that {@link ReflectFieldAccessor#create(Class, Class, String)}
     *     does, therefore assuming the correct type is always passed
     * </p>
     * @param targetClass The class in which the given field is stored in (can be a derived class, where the field is stored in a super class)
     * @param fieldName Name of the field
     * @param <T> Target class type
     * @param <V> Field type
     * @return A field accessor
     */
    public static <T, V> ReflectFieldAccessor<T, V> create(Class<T> targetClass, String fieldName) {
        Field field = Reflect.findPrivateField(targetClass, fieldName);
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        return new ReflectFieldAccessor<T, V>(targetClass, field, fieldName);
    }

    public Class<?> getOwnerClass() {
        return ownerClass;
    }

    public Field getField() {
        return field;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public V get(T target) throws RuntimeException {
        try {
            return (V) this.field.get(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
        catch (Throwable e) {
            throw new RuntimeException(RZFormats.format("Failed to get field '{0}.{1}'", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void set(T target, V value) throws RuntimeException {
        try {
            this.field.set(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
        catch (Throwable e) {
            throw new RuntimeException(RZFormats.format("Failed to set field '{0}.{1}'", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public byte getByte(T target) {
        try {
            return this.field.getByte(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("byte field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public short getShort(T target) {
        try {
            return this.field.getShort(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("short field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public int getInt(T target) {
        try {
            return this.field.getInt(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("int field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public long getLong(T target) {
        try {
            return this.field.getLong(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("long field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public float getFloat(T target) {
        try {
            return this.field.getFloat(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("float field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public double getDouble(T target) {
        try {
            return this.field.getDouble(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("double field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public boolean getBool(T target) {
        try {
            return this.field.getBoolean(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("boolean field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public char getChar(T target) {
        try {
            return this.field.getChar(target);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("char field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void setByte(T target, byte value) {
        try {
            this.field.setByte(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("byte field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void setShort(T target, short value) {
        try {
            this.field.setShort(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("short field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void setInt(T target, int value) {
        try {
            this.field.setInt(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("int field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void setLong(T target, long value) {
        try {
            this.field.setLong(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("long field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void setFloat(T target, float value) {
        try {
            this.field.setFloat(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("float field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void setDouble(T target, double value) {
        try {
            this.field.setDouble(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("double field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void setBool(T target, boolean value) {
        try {
            this.field.setBoolean(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("bool field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }

    @Override
    public void setChar(T target, char value) {
        try {
            this.field.setChar(target, value);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(RZFormats.format("char field '{0}.{1}' was inaccessible", this.ownerClass.getName(), this.fieldName), e);
        }
    }
}
