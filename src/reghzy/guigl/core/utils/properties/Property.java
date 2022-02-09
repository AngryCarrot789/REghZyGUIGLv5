package reghzy.guigl.core.utils.properties;

import reghzy.guigl.core.utils.properties.get.Getter;

public class Property<T> implements Getter<T>, Setter<T> {
    private final boolean canGet;
    private final boolean canSet;

    protected final String name;
    protected T value;

    public Property(String name, boolean canGet, boolean canSet) {
        this.name = name;
        this.canGet = canGet;
        this.canSet = canSet;
    }

    public static <T> Property<T> getter(String name) {
        return new Property<T>(name, true, false);
    }

    public static <T> Property<T> setter(String name) {
        return new Property<T>(name, false, true);
    }

    public static <T> Property<T> getset(String name) {
        return new Property<T>(name, true, true);
    }

    public T get() {
        if (!this.canGet) {
            throw new UnsupportedOperationException(this.name + " getter is not allow");
        }

        return value;
    }

    public T set(T value) {
        if (!this.canSet) {
            throw new UnsupportedOperationException(this.name + " setter is not allow");
        }

        return this.value = value;
    }

    public String name() {
        return this.name;
    }
}
