package reghzy.guigl.utils.reflect;

/**
 * A class which has access to getting and setting a field
 * @param <T> The type of class in which the field is defined in (e.g ArrayList for elementData)
 * @param <V> The field's value type (e.g {@link Object[]} for {@link java.util.ArrayList#elementData})
 */
public interface FieldAccessor<T, V> {
    /**
     * Gets the field's value for the given target instance
     * @throws RuntimeException If there was a problem getting the field, e.g the accessibility was externally changed (was no longer public), or the field didn't exist
     */
    V get(T target) throws RuntimeException;

    /**
     * Sets the field's value for the given target instance
     * @throws RuntimeException If there was a problem setting the field, e.g the accessibility was externally changed (was no longer public), the field didn't exist,
     * or the target/value types were not correct (e.g the given value was a String, but the field type was an ArrayList)
     */
    void set(T target, V value) throws RuntimeException;
}
