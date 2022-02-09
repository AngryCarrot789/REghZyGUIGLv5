package reghzy.guigl.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reghzy.guigl.core.log.ChatColour;

import java.util.Arrays;
import java.util.Map;

/**
 * A class for formatting text/messages to be send to players/console
 *
 * <p>
 *     Fun fact, minecraft's colour codes are based on the the CGA 16-colour palette
 *     <a href="https://en.wikipedia.org/wiki/Color_Graphics_Adapter#Color_palette">Link to wiki</a>
 * </p>
 */
public final class RZFormats {
    public static final String ACCEPTABLE_COLOURS = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";

    private RZFormats() { }

    /**
     * repeats the given character the given number of times
     */
    public static String repeat(char character, int times) {
        char[] array = new char[times];
        Arrays.fill(array, character);
        return new String(array);
    }

    /**
     * Makes the content green
     */
    public static String green(String content) {
        return ChatColour.GREEN + content;
    }

    /**
     * Makes the content gold
     */
    public static String gold(String content) {
        return ChatColour.GOLD + content;
    }

    /**
     * Makes the content gold
     */
    public static String cyan(String content) {
        return ChatColour.DARK_AQUA + content;
    }

    /**
     * Makes the content red
     */
    public static String red(String content) {
        return ChatColour.RED + content;
    }

    /**
     * Converts replaces the given "alternate" prefix character (aka, 1 char before a valid colour code) with the actual colour code prefix character
     */
    public static String translateColourCode(String text, char alternatePrefix) {
        char[] chars = text.toCharArray();
        StringBuilder string = new StringBuilder(chars.length);
        for (int i = 0, len = chars.length, lenIndex = len - 1; i < len; i++) {
            char c = chars[i];
            if (c == alternatePrefix) {
                if (i == lenIndex) {
                    string.append(c);
                    break;
                }
                else if (ACCEPTABLE_COLOURS.indexOf(chars[i + 1]) == -1) {
                    string.append(c).append(chars[++i]);
                }
                else {
                    string.append('§').append(chars[++i]);
                }
            }
            else {
                string.append(c);
            }
        }

        return string.toString();
    }

    /**
     * Replaces any matching colour code, only if the character before is the '&' character, with the actual character
     */
    public static String colour(String text) {
        return translateColourCode(text, '&');
    }

    public static String colour(String text, ChatColour colour) {
        return new StringBuilder().append(colour).append(text).toString();
    }

    /**
     * Removes all colour translations (using the actual colour code prefix, not the '&' one)
     */
    public static String removeColour(String text) {
        return removeColour(text.toCharArray(), '§');
    }

    public static String removeBothColour(String text) {
        return removeBothColour(text.toCharArray());
    }

    public static String removeColour(String text, char c) {
        return removeColour(text.toCharArray(), c);
    }

    /**
     * Removes all colour translations (using the actual colour code prefix, not the '&' one)
     */
    public static String removeColour(char[] chars, char colourCode) {
        StringBuilder string = new StringBuilder(chars.length);
        for (int i = 0, len = chars.length, lenIndex = len - 1; i < len; i++) {
            char c = chars[i];
            if (c == colourCode) {
                if (i == lenIndex) {
                    return string.append(c).toString();
                }
                else if ((ACCEPTABLE_COLOURS.indexOf(chars[i + 1]) != -1)) {
                    i++;
                }
            }
            else {
                string.append(c);
            }
        }

        return string.toString();
    }

    public static String removeBothColour(char[] chars) {
        StringBuilder string = new StringBuilder(chars.length);
        for (int i = 0, len = chars.length, lenIndex = len - 1; i < len; i++) {
            char c = chars[i];
            if (c == '&' || c == '§') {
                if (i == lenIndex) {
                    return string.append(c).toString();
                }
                else if ((ACCEPTABLE_COLOURS.indexOf(chars[i + 1]) != -1)) {
                    i++;
                }
            }
            else {
                string.append(c);
            }
        }

        return string.toString();
    }

    public static String formatColour(String value, Object... args) {
        return colour(format(value, args));
    }

    public static String interpolateColour(String value, @NotNull Map<String, ?> map) {
        return colour(interpolate(value, map));
    }

    public static String interpolateColour(String value, @NotNull Map<String, ?> map, boolean ignoreNull) {
        return colour(interpolate(value, map, ignoreNull));
    }

    /**
     * Similar to {@link java.text.MessageFormat#format(String, Object...)}, only more efficient, and
     * escaping a bracket char is done with the '\' char, instead of the ' char
     * <p>
     *     Uses a number between {@code {}'s} to index an object in the given arguments. Null objects are handled by the StringBuilder
     * </p>
     * <p>
     *     Escaping an open bracket ('{') is supported, however escaping a closing bracket ('}') after an
     *     opened bracket is not supported... and there would be little to no reason to do so anyway
     * </p>
     * @param value The value to be formatted
     * @param args The values to splice in
     * @return A string where all of the {...} cases have been replaced with the given arguments
     * @throws RuntimeException If the value between the { and } is empty, or not an integer
     * @throws IndexOutOfBoundsException If the index if out of range (smaller than 0 or bigger than the array's length)
     */
    public static String format(@Nullable String value, Object... args) {
        if (value == null) {
            return null;
        }

        if (value.isEmpty()) {
            return value;
        }

        int firstOpenIndex = value.indexOf('{');
        if (firstOpenIndex == -1) {
            return value;
        }

        char[] chars = value.toCharArray();
        int lastCloseIndex = StringHelper.charsLastIndexOf(chars, '}', firstOpenIndex);
        if (lastCloseIndex == -1) {
            if (firstOpenIndex == 0 || chars[firstOpenIndex - 1] != '\\') { // check if the format is escaped using \\{
                throw new RuntimeException("Initial format check - missing closing '}' after the first opening bracket '{' at index " + firstOpenIndex);
            }

            return value;
        }

        int maxIndex = args.length - 1;
        StringBuilder builder = new StringBuilder(value.length() * 2);
        builder.append(value, 0, firstOpenIndex);
        for (int i = firstOpenIndex, end = chars.length, endIndex = end - 1; i < end; i++) {
            char c = chars[i];
            if (c == '{') {
                // last character
                if (i == endIndex) {
                    builder.append(c);
                    break;
                }

                // escape
                if (i > 0 && chars[i - 1] == '\\') {
                    builder.setCharAt(i - 1, '{');
                    continue;
                }

                int nextClose = StringHelper.charsIndexOf(chars, '}', i + 1);
                if (nextClose == -1) {
                    throw new RuntimeException("Missing a closing '}' after an opening bracket '{' at index " + i);
                    // builder.append(chars, i, chars.length - i - 1);
                    // break;
                }

                int parseIndex = i + 1;
                if (parseIndex == nextClose) {
                    throw new RuntimeException("Value between '{' and '}' was empty! At index " + parseIndex);
                }

                Integer index = StringHelper.parseInteger(chars, parseIndex, nextClose);
                if (index == null) {
                    throw new RuntimeException("Value between '{' and '}' was not an integer! At index " + parseIndex);
                }

                if (index < 0 || index > maxIndex) {
                    throw new IndexOutOfBoundsException("The index (" + index + ") was out of range (array size was " + args.length + ") at string index " + parseIndex);
                }

                // let the sb handle null objects :)
                builder.append(args[index]);
                i = nextClose;
            }
            else {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    /**
     * Similar to {@link RZFormats#format(String, Object...)}, but instead of using an array of objects and the
     * value containing formatted indexes, the value instead contains formatted keys which map to a value in the HashMap.
     * This is also basically the same as C#'s string interpolation, using the $ (dollar) char before the " (quote) char
     * <p>
     *     This will ignore null values, but it will still throw a {@link RuntimeException}
     *     if the map does not contain a specific key
     * </p>
     * @param value               The value to be formatted
     * @param map                 The map containing objects to be spliced into the final output
     * @return A string where all of the {...} cases have been replaced with values in the given map
     * @throws RuntimeException   If there are missing open/close brackets, or the value between them is empty
     * @throws RuntimeException  If the value between the '{' and '}' is not contained in the given HashMap
     */
    public static String interpolate(@Nullable String value, @NotNull Map<String, ?> map) {
        return interpolate(value, map, true);
    }

    /**
     * Similar to {@link RZFormats#format(String, Object...)}, but instead of using an array of objects and the
     * value containing formatted indexes, the value instead contains formatted keys which map to a value in the HashMap.
     * This is also basically the same as C#'s string interpolation, using the $ (dollar) char before the " (quote) char
     * <p>
     *     Escaping an open bracket ('{') is supported, however escaping a closing bracket ('}') after an
     *     opened bracket is not supported... and there would be little to 0 reason to do so
     * </p>
     * @param value The value to be formatted
     * @param map   The map containing objects to be spliced into the final output
     * @param ignoreNull If true and {@link Map#get(Object)} return null ({@link Map#containsKey(Object)} will still be checked),
     *                   then "null" will be appended to the string (which is what {@link StringBuilder#append(Object)}
     *                   would do). Otherwise, a {@link NullPointerException} will be thrown.
     * @return A string where all of the {...} cases have been replaced with values in the given map
     * @throws RuntimeException   If there are missing open/close brackets, or the value between them is empty
     * @throws RuntimeException  If the value between the '{' and '}' is not contained in the given HashMap
     * @throws NullPointerException If the value in the HashMap is null, and the ignoreNull parameter is false
     */
    public static String interpolate(@Nullable String value, @NotNull Map<String, ?> map, boolean ignoreNull) {
        if (value == null) {
            return null;
        }

        if (value.isEmpty()) {
            return value;
        }

        int firstOpenIndex = value.indexOf('{');
        if (firstOpenIndex == -1) {
            return value;
        }

        char[] chars = value.toCharArray();
        int lastCloseIndex = StringHelper.charsLastIndexOf(chars, '}', firstOpenIndex);
        if (lastCloseIndex == -1) {
            if (firstOpenIndex == 0 || chars[firstOpenIndex - 1] != '\\') { // check if the format is escaped using \\{
                throw new RuntimeException("Missing a closing '}' after the first opening bracket '{' at string index " + firstOpenIndex);
            }

            return value;
        }

        StringBuilder builder = new StringBuilder(value.length() * 2);
        builder.append(value, 0, firstOpenIndex);
        for (int i = firstOpenIndex, end = chars.length, endIndex = end - 1; i < end; i++) {
            char c = chars[i];
            if (c == '{') {
                if (i == endIndex) { // last character
                    return builder.append(c).toString();
                }

                if (i > 0 && chars[i - 1] == '\\') { // escape
                    builder.setCharAt(i - 1, '{');
                    continue;
                }

                int nextClose = StringHelper.charsIndexOf(chars, '}', i + 1);
                if (nextClose == -1) {
                    throw new RuntimeException("Missing a closing '}' after an opening bracket '{', at string index " + i);
                }

                int parseIndex = i + 1;
                if (parseIndex == nextClose) {
                    throw new RuntimeException("Value between '{' and '}' was empty, at string index " + parseIndex);
                }

                String key = StringHelper.substringArray(chars, parseIndex, nextClose);
                if (key.isEmpty()) {
                    throw new RuntimeException("Value between '{' and '}' was empty, at string index " + parseIndex);
                }

                Object obj = map.get(key);
                if (obj == null) {
                    if (map.containsKey(key)) {
                        if (ignoreNull) {
                            builder.append((Object) null);
                        }
                        else {
                            throw new NullPointerException("Value with key '" + key + "' was null, at string index: " + parseIndex);
                        }
                    }
                    else {
                        throw new RuntimeException("Map did not contain the key '" + key + "', at string index: " + parseIndex);
                    }
                }
                else {
                    builder.append(obj);
                }

                i = nextClose;
            }
            else {
                builder.append(c);
            }
        }

        return builder.toString();
    }

}
