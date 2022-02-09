package reghzy.guigl.core.utils.properties;

public interface Setter<T> {
    /**
     * Sets the value, and returns the new value (aka the parameter value)
     * @param value New value
     * @return New value
     */
    T set(T value);
}
