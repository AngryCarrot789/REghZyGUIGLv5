package reghzy.guigl.utils;

import java.util.Collection;

public class StringJoiner {
    private final String delimiter;
    private final String prefix;
    private final String suffix;
    private final StringBuilder value;
    private final String emptyValue;

    public StringJoiner(CharSequence delimiter) {
        this(delimiter, "", "", 128);
    }

    public StringJoiner(CharSequence delimiter, int initialSize) {
        this(delimiter, "", "", initialSize);
    }

    public StringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix, int initialSize) {
        this.prefix = prefix == null ? "" : prefix.toString();
        this.suffix = suffix == null ? "" : suffix.toString();
        this.delimiter = (delimiter == null ? ", " : delimiter.toString());
        this.emptyValue = this.prefix + this.suffix;
        this.value = new StringBuilder(initialSize);
        if (!this.prefix.isEmpty()) {
            this.value.append(this.prefix);
        }
    }

    public StringJoiner append(CharSequence value) {
        prepareBuilder().append(value);
        return this;
    }

    public StringJoiner append(int value) {
        prepareBuilder().append(value);
        return this;
    }

    public StringJoiner append(long value) {
        prepareBuilder().append(value);
        return this;
    }

    public StringJoiner append(float value) {
        prepareBuilder().append(value);
        return this;
    }

    public StringJoiner append(double value) {
        prepareBuilder().append(value);
        return this;
    }

    public StringJoiner append(char value) {
        prepareBuilder().append(value);
        return this;
    }

    public StringJoiner append(boolean value) {
        prepareBuilder().append(value);
        return this;
    }

    public <T> StringJoiner append(T[] values) {
        for (T value : values) {
            append(String.valueOf(value));
        }

        return this;
    }

    public <T> StringJoiner append(Collection<T> values) {
        for (T value : values) {
            append(String.valueOf(value));
        }

        return this;
    }

    public StringJoiner append(char[] value) {
        prepareBuilder().append(value);
        return this;
    }

    public int length() {
        if (this.prefix.isEmpty()) {
            return this.value.length();
        }
        else {
            return this.value.length() - this.prefix.length();
        }
    }

    private StringBuilder prepareBuilder() {
        if (this.value.length() == 0) {
            return this.value;
        }

        return value.append(delimiter);
    }

    @Override
    public String toString() {
        if (this.value.length() == this.prefix.length()) {
            return emptyValue;
        }
        else if (this.suffix.isEmpty()) {
            return value.toString();
        }
        else {
            int initialLength = value.length();
            String result = value.append(suffix).toString();
            value.setLength(initialLength);
            return result;
        }
    }
}
