package reghzy.guigl.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringHelper {
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
        // if (value.charAt(0) != ' ') {
        //     return false;
        // }
        // for (int i = 1, len = value.length(); i < len; i++) {
        //     if (value.charAt(i) != ' ') {
        //         return false;
        //     }
        // }
        //
        // return true
    }

    public static boolean notNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
        // if (value.charAt(0) != ' ') {
        //     return true;
        // }
        // for(int i = 1, len = value.length(); i < len; i++) {
        //     if (value.charAt(i) != ' ') {
        //         return true;
        //     }
        // }
        //
        // return false
    }

    public static boolean arrayContains(char[] chars, char c) {
        for (char cc : chars) {
            if (cc == c) {
                return true;
            }
        }

        return false;
    }

    public static String trimStartOfChar(String value, char c, int startIndex) {
        int len = value.length();
        while(true) {
            if (startIndex >= len) {
                return null;
            }
            else if (value.charAt(startIndex) == c) {
                startIndex++;
            }
            else {
                return value.substring(startIndex);
            }
        }
    }

    /**
     * Similar {@link String#substring(int, int)}, but you use a char array instead of a string
     * @param chars      The char array to substring to
     * @param startIndex The start index (inclusive)
     * @param endIndex   The end index (exclusive)
     * @return A string containing the substring of the given char array
     */
    public static String substringArray(char[] chars, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            return "";
        }
        else if (startIndex == 0 && endIndex == chars.length) {
            return new String(chars);
        }
        else if (startIndex > endIndex) {
            throw new IndexOutOfBoundsException(RZFormats.format("startIndex was bigger than endIndex ({0} > {1})", startIndex, endIndex));
        }
        else if (endIndex >= chars.length) {
            throw new IndexOutOfBoundsException(RZFormats.format("endIndex was bigger than or equal to the array's length ({0} >= {1})", endIndex, chars.length));
        }
        else {
            return new String(chars, startIndex, endIndex - startIndex);
        }
    }

    public static String removeWhitespaces(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        for(int i = 0, len = string.length(); i < len; i++) {
            char c = string.charAt(i);
            if (c != ' ') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static int parseIntegerWithError(String value, String errorMessage, Object... args) {
        Integer i = parseInteger(value);
        if (i == null) {
            throw new RuntimeException(RZFormats.format(errorMessage, args));
        }

        return i;
    }

    public static Number parseNumber(String value) {
        Number intNumber = parseInteger(value);
        if (intNumber == null) {
            return parseDouble(value);
        }

        return intNumber;
    }

    /**
     * Parses integers without throwing exceptions, simply returns null if it failed to parse (more efficient, no extra class creation on failure to parse)
     */
    public static Integer parseInteger(String value) {
        if (value == null || value.length() == 0)
            return null;

        final int radix = 10;
        int result = 0;
        boolean isNegative = false;
        int i = 0, len = value.length();
        int limit = -Integer.MAX_VALUE;
        int radixMinLimit;
        int digit;

        char firstChar = value.charAt(0);
        if (firstChar < '0') { // Possible leading "+" or "-"
            if (firstChar == '-') {
                isNegative = true;
                limit = Integer.MIN_VALUE;
            }
            else if (firstChar != '+')
                return null;

            if (len == 1) // Cannot have lone "+" or "-"
                return null;
            i++;
        }
        radixMinLimit = limit / radix;
        while (i < len) {
            // Accumulating negatively avoids surprises near MAX_VALUE
            digit = Character.digit(value.charAt(i++), radix);
            if (digit < 0) {
                return null;
            }
            if (result < radixMinLimit) {
                return null;
            }

            result *= radix;
            if (result < limit + digit) {
                return null;
            }
            result -= digit;
        }

        return isNegative ? result : -result;
    }


    /**
     * Parses an integer in the given char array, starting at the given start index and ending before the given end index
     * @param value              The value which may contain an integer somewhere
     * @param statIndexInclusive The start index (inclusive) to start parsing
     * @param endIndexExclusive  The end index (exclusive) where parsing will stop (so not including the char at this index)
     * @return An integer, or null if the parse failed
     */
    public static Integer parseInteger(char[] value, int statIndexInclusive, int endIndexExclusive) {
        if (value == null || value.length == 0)
            return null;

        int radix = 10;
        int result = 0;
        boolean isNegative = false;
        int i = statIndexInclusive, len = (endIndexExclusive - statIndexInclusive);
        int limit = -Integer.MAX_VALUE;
        int radixMinLimit;
        int digit;

        char firstChar = value[statIndexInclusive];
        if (firstChar < '0') { // Possible leading "+" or "-"
            if (firstChar == '-') {
                isNegative = true;
                limit = Integer.MIN_VALUE;
            }
            else if (firstChar != '+')
                return null;

            if (len == 1) // Cannot have lone "+" or "-"
                return null;
            i++;
        }
        radixMinLimit = limit / radix;
        while (i < endIndexExclusive) {
            // Accumulating negatively avoids surprises near MAX_VALUE
            digit = Character.digit(value[i++], radix);
            if (digit < 0) {
                return null;
            }
            if (result < radixMinLimit) {
                return null;
            }
            result *= radix;
            if (result < limit + digit) {
                return null;
            }
            result -= digit;
        }

        return isNegative ? result : -result;
    }

    public static Long parseLong(String value) {
        int radix = 10;
        long result = 0;
        boolean negative = false;
        int i = 0, len = value.length();
        long limit = -Long.MAX_VALUE;
        long radixMinLimit;
        int digit;

        if (len > 0) {
            char firstChar = value.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                }
                else if (firstChar != '+')
                    return null;

                if (len == 1) // Cannot have lone "+" or "-"
                    return null;
                i++;
            }
            radixMinLimit = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(value.charAt(i++), radix);
                if (digit < 0) {
                    return null;
                }
                if (result < radixMinLimit) {
                    return null;
                }
                result *= radix;
                if (result < limit + digit) {
                    return null;
                }
                result -= digit;
            }
        }
        else {
            return null;
        }
        return negative ? result : -result;
    }

    public static Double parseDouble(String stringValue) {
        try {
            return Double.parseDouble(stringValue);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer parseIntegerIndicator(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        char lastChar = value.charAt(value.length() - 1);
        if (lastChar == 'i' || lastChar == 'I') {
            return parseInteger(value.substring(0, value.length() - 1));
        }
        else {
            return parseInteger(value);
        }
    }

    public static Long parseLongIndicator(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        char lastChar = value.charAt(value.length() - 1);
        if (lastChar == 'l' || lastChar == 'L') {
            return parseLong(value.substring(0, value.length() - 1));
        }
        else {
            return parseLong(value);
        }
    }

    public static Double parseDoubleIndicator(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        char lastChar = value.charAt(value.length() - 1);
        if (lastChar == 'd' || lastChar == 'D' || lastChar == 'f' || lastChar == 'F') {
            return parseDouble(value.substring(0, value.length() - 1));
        }
        else {
            return parseDouble(value);
        }
    }

    public static String clampString(String value, int maxLength, char endingChar, int endCharCount) {
        if (endCharCount >= maxLength) {
            throw new RuntimeException("End char count cannot be bigger than or equal to the max length");
        }

        int len = value.length();
        if (len <= maxLength) {
            return value;
        }

        return value.substring(0, maxLength - endCharCount) + repeat(endingChar, endCharCount);
    }

    public static String getNearby(String string, String search, int near) {
        int index = string.indexOf(search);
        if (index == -1) {
            return null;
        }

        return string.substring(Math.max(0, index - near), Math.min(string.length(), index + near));
    }

    public static String getNearby(String string, char search, int near) {
        int index = string.indexOf(search);
        if (index == -1) {
            return null;
        }

        return string.substring(Math.max(0, index - near), Math.min(string.length(), index + near));
    }

    /**
     * Gets 'near' number of chars, before the given start index, and 'near + indexOffset' number of chars after the given start index
     * <p>
     *     getNearby("Do you want some coffee?", 11, 1, 4) == "want some"
     * </p>
     * @param string The string
     * @param index Start index
     * @param indexOffset A modifier to the chars after start index to extract. This usually should be 1, if you want to get the chars near 1 char (that 1 char being at index),
     * @param near The number of chars to get before and after (+ indexOffset) the index
     * @return A string, where the total chars will be no more than (near * 2) + indexOffset
     */
    public static String getNearby(String string, int index, int indexOffset, int near) {
        // "hello there"
        return string.substring(Math.max(0, index - near), Math.min(string.length(), (index + indexOffset) + near));
    }

    public static String getContentAfterSplitterIndex(String content, char splitter, int splitIndex, int startIndex) {
        boolean next = false;
        for (int i = startIndex, last = 0, curr = -1, len = content.length(), lenIndex = len - 1; i < len; i++) {
            if (next) {
                if (content.charAt(i) == splitter) {
                    return content.substring(last + 1, i);
                }
                else if (i == lenIndex) {
                    return content.substring(last + 1, i + 1);
                }
            }
            else if (content.charAt(i) == splitter) {
                curr++;
                if (curr == splitIndex) {
                    next = true;
                }
                last = i;
            }
        }

        return null;
    }

    public static String repeat(char repeat, int count) {
        char[] array = new char[count];
        Arrays.fill(array, repeat);
        return new String(array);
    }

    public static String repeat(String repeat, int count) {
        char[] array = new char[repeat.length() * count];
        for(int i = 0, strLen = repeat.length(), end = count * strLen; i < end; i += strLen) {
            repeat.getChars(0, strLen, array, i);
        }
        return new String(array);
    }

    public static String ensureLength(String string, int requiredLength, char fillValue) {
        int extra = requiredLength - string.length();
        if (extra > 0)
            return string + repeat(fillValue, extra);
        if (extra < 0)
            return string.substring(0, requiredLength);
        else
            return string;
    }

    public static String joinArray(String[] args, int startIndex, char joinCharacter) {
        if (args == null || startIndex < 0 || startIndex >= args.length) {
            return null;
        }

        if (args.length == 1) {
            return args[0];
        }

        StringBuilder string = new StringBuilder(args.length);
        for (int i = startIndex, len = args.length, lenIndex = len - 1; i < len; i++) {
            if (i == lenIndex) {
                string.append(args[i]);
            }
            else {
                string.append(args[i]).append(joinCharacter);
            }
        }

        return string.toString();
    }

    public static String joinArrayRemoveEmpty(String[] args, int startIndex, char joinCharacter) {
        if (args == null || startIndex >= args.length) {
            return null;
        }
        else if (startIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("Start index cannot be below 0: " + startIndex);
        }

        if (args.length == 1) {
            String arg0 = args[0];
            if (arg0 == null || arg0.isEmpty()) {
                return null;
            }
            else {
                return arg0;
            }
        }

        StringBuilder string = new StringBuilder(args.length);
        for (int i = startIndex, len = args.length, lenIndex = len - 1; i < len; i++) {
            String element = args[i];
            if (element == null || element.isEmpty()) {
                continue;
            }

            if (i == lenIndex) {
                string.append(element);
            }
            else {
                string.append(element).append(joinCharacter);
            }
        }

        return string.toString();
    }

    public static String joinArray(String[] args, int startIndex, String joinText) {
        if (args == null || startIndex < 0 || startIndex >= args.length) {
            return null;
        }

        if (args.length == 1) {
            return args[0];
        }

        StringBuilder string = new StringBuilder(args.length);
        for (int i = startIndex, lenIndex = args.length - 1; i < args.length; i++) {
            if (i == lenIndex) {
                string.append(args[i]);
            }
            else {
                string.append(args[i]).append(joinText);
            }
        }

        return string.toString();
    }

    @NotNull
    public static String joinArrayIgnoreNullOrEmpty(@NotNull String[] array, int startIndex, @NotNull String joinText) {
        if (array == null) {
            throw new NullPointerException("Args cannot be null");
        }
        else if (joinText == null) {
            throw new NullPointerException("Join Text cannot be null");
        }
        else if (startIndex < 0) {
            throw new IndexOutOfBoundsException("StartIndex must be 0 or above");
        }
        else if (startIndex >= array.length) {
            throw new IndexOutOfBoundsException("StartIndex must be below the array length");
        }
        else {
            if (array.length == 1) {
                return array[0].trim();
            }


            StringBuilder string = new StringBuilder(array.length);
            for (int i = startIndex, lenIndex = array.length - 1; i < array.length; i++) {
                String element = array[i];
                if (element == null || element.isEmpty()) {
                    continue;
                }

                if (i == lenIndex) {
                    string.append(array[i]);
                }
                else {
                    string.append(array[i]).append(joinText);
                }
            }

            return string.toString();
        }
    }

    public static boolean containsChar(String string, char character) {
        for(int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == character)
                return true;
        }

        return false;
    }

    public static boolean containsChar(char[] characters, char character) {
        for (char c : characters) {
            if (c == character)
                return true;
        }
        return false;
    }

    public static int countChar(String string, char character) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == character)
                count++;
        }
        return count;
    }
    
    public static int charsIndexOf(char[] array, char value, int startIndex) {
        for (int i = startIndex, len = array.length; i < len; i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Searches the array backwards (down until smallestIndex) for the specific char. If the char wasn't found, -1 is returned
     * @param array The chars
     * @param value The value to search for
     * @param smallestIndex The smallest index the backwards search can reach (inclusive). To search the entire
     *                      string, set as 0. To search the last 3 chars, set as array.length - 3
     * @return
     */
    public static int charsLastIndexOf(char[] array, char value, int smallestIndex) {
        for (int i = array.length - 1; i >= smallestIndex; i--) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Searches the array backwards (up until smallestIndex) for the specific char. If the char wasn't found, -1 is returned
     * @param str           The string
     * @param value         The value to search for
     * @param smallestIndex The smallest index the backwards search can reach (inclusive)
     *                      To search the entire string, set as 0. To search the last 3 chars, set as array.length - 3
     * @return
     */
    public static int charsLastIndexOf(String str, char value, int smallestIndex) {
        for (int i = str.length() - 1; i >= smallestIndex; i--) {
            if (str.charAt(i) == value) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns a substring starting at the given startIndex, and ending at
     * the index of the first occurrence of the given stop character.
     * returns null if the stop character isn't found after startIndex
     *
     * @param content    The content
     * @param stop       The character that, if found, stops the search and the text before it is returned
     * @param startIndex The index to start the search (inclusive; includes the character at the index)
     * @return
     */
    public static String getUpTo(String content, char stop, int startIndex) {
        int stopIndex = content.indexOf(stop, startIndex);
        if(stopIndex == -1)
            return null;

        return content.substring(startIndex, stopIndex);
    }

    /**
     * Returns the text after the first occurrence of a given string value
     */
    public static String getAfter(String content, String value, int startIndex) {
        int valueStart = content.indexOf(value, startIndex);
        if (valueStart == -1)
            return null;

        return content.substring(valueStart + value.length());
    }

    public static int countCharacter(String content, char character) {
        int count = 0;
        for (int i = 0, len = content.length(); i < len; i++) {
            if (content.charAt(i) == character)
                count++;
        }
        return count;
    }

    public static String[] split(String content, char splitter, int startIndex) {
        int splitterCount = countCharacter(content, splitter);
        if (splitterCount == 0) {
            return new String[] { content };
        }

        String[] array = new String[splitterCount + 1];
        int i = startIndex;
        int lastSplit = -1;
        int arrayIndex = 0;
        int strLen = content.length();
        int strLenIndex = strLen - 1;
        while (true) {
            if (content.charAt(i) == splitter) {
                array[arrayIndex] = content.substring(lastSplit + 1, i);
                lastSplit = i;
                arrayIndex++;
            }
            if (i == strLenIndex) {
                array[arrayIndex] = content.substring(lastSplit + 1);
                break;
            }

            i++;
        }
        return array;
    }

    public static String[] splitEscapable(String content, char splitter, char escapeChar, int startIndex) {
        int splitterCount = countCharacter(content, splitter);
        if (splitterCount == 0) {
            return new String[]{content};
        }

        String[] array = new String[splitterCount + 1];
        int i = startIndex;
        int lastSplit = -1;
        int arrayIndex = 0;
        int length = content.length();
        int endIndex = length - 1;
        while (true) {
            if (content.charAt(i) == splitter) {
                if (i > 0 && content.charAt(i - 1) == escapeChar) {
                    continue;
                }

                array[arrayIndex] = content.substring(lastSplit + 1, i);
                lastSplit = i;
                arrayIndex++;
            }
            if (i == endIndex) {
                array[arrayIndex] = content.substring(lastSplit + 1);
                break;
            }

            i++;
        }
        return array;
    }

    public static boolean endsWith(String content, char character) {
        return content.charAt(content.length() - 1) == character;
    }

    public static boolean startsWith(String content, char character) {
        return content.charAt(0) == character;
    }

    public static String remove(String original, char a, int start) {
        StringBuilder stringBuilder = new StringBuilder(original.length());
        for (int i = start, len = original.length(); i < len; i++) {
            char c = original.charAt(i);
            if (c != a) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static int countNullOrEmpty(String[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int empty = 0;
        for (String element : array) {
            if (element == null || element.isEmpty()) {
                empty++;
            }
        }

        return empty;
    }

    public static int countNullOrEmpty(String[] array, int startIndex) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int empty = 0;
        for (int i = startIndex, end = array.length; i < end; i++) {
            String element = array[i];
            if (element == null || element.isEmpty()) {
                empty++;
            }
        }

        return empty;
    }

    /**
     * Removes all null or empty elements within the given array. If the array contains no null or empty values (or is empty), the given array is returned (same reference)
     */
    @NotNull
    public static String[] removeNullOrEmpty(String[] array) {
        if (array == null) {
            return new String[0];
        }

        if (array.length == 0) {
            return array;
        }

        int nullOrEmptyCount = countNullOrEmpty(array);
        if (nullOrEmptyCount == 0 || nullOrEmptyCount == array.length) {
            return array;
        }

        String[] newArray = new String[array.length - nullOrEmptyCount];
        for (int i = 0, j = 0, len = array.length; i < len; i++) {
            String element = array[i];
            if (element == null || element.isEmpty()) {
                continue;
            }

            newArray[j++] = element;
        }

        return newArray;
    }

    /**
     * Removes all null or empty elements within the given array, starting at the given index (the values before the index will be removed). If the array contains no null or empty values (or is empty), the given array is returned (same reference)
     */
    public static String[] removeNullOrEmpty(String[] array, int startIndex) {
        if (array == null) {
            return new String[0];
        }
        else if (array.length == 0) {
            return array;
        }

        if (startIndex >= array.length) {
            throw new IndexOutOfBoundsException(RZFormats.format("{0} >= {1}", startIndex, array.length));
        }

        int nullOrEmptyCount = countNullOrEmpty(array, startIndex);
        if (nullOrEmptyCount == (array.length - startIndex)) {
            return Memory.extractAfter(array, startIndex);
        }

        String[] newArray = new String[array.length - nullOrEmptyCount - startIndex];
        for (int i = startIndex, j = 0, len = array.length; i < len; i++) {
            String element = array[i];
            if (element == null || element.isEmpty()) {
                continue;
            }

            newArray[j++] = element;
        }

        return newArray;
    }

    public static String removeAt(String value, int index) {
        if (index < 0 || index >= value.length()) {
            throw new StringIndexOutOfBoundsException(index);
        }

        return new StringBuilder(value.length() - 1).append(value, 0, index).append(value.substring(index + 1)).toString();
    }

    /**
     * Goes through all of the given arguments and tries to concatenate multiple elements as a single element if they are quoted
     * @exception RuntimeException Thrown if there was an odd number of quotes (due to a missing closing quote)
     */
    public static String[] concatQuotes(String[] array) {
        List<String> list = new ArrayList<String>(array.length);
        // p:okayThen "okay xd" -l i:"Spotloader" i:"Spot Loader" r:"Chested to reduce lag" p:"okayThen"
        // i:"Spotloader" r:"Chested to reduce lag" p:okayThen

        StringBuilder currentQuote = new StringBuilder(32);
        boolean quote = false;
        for(String value : array) {
            int quoteIndex = value.indexOf('"');
            // TODO: try and get this to work; add an escape char
            // while(true) {
            //     if (quoteIndex > 0 && value.indexOf(quoteIndex - 1) == '\\') {
            //         quoteIndex = value.indexOf('"', quoteIndex + 1);
            //         continue;
            //     }
            //
            //     break;
            // }

            if (quoteIndex == -1) {
                if (quote) {
                    currentQuote.append(value).append(' ');
                }
                else {
                    list.add(value);
                }
            }
            else if (quote) {
                currentQuote.append(removeAt(value, quoteIndex));
                if (currentQuote.length() != 0) {
                    list.add(currentQuote.toString());
                    currentQuote.setLength(0);
                }

                quote = false;
            }
            else {
                int nextQuote = charsLastIndexOf(value, '"', quoteIndex + 1);
                if (nextQuote == -1) {
                    currentQuote.append(removeAt(value, quoteIndex)).append(' ');
                    quote = true;
                }
                else {
                    list.add(new StringBuilder().append(value, 0, quoteIndex).append(value, quoteIndex + 1, nextQuote).toString());
                }
            }
        }

        if (quote) {
            throw new RuntimeException("Missing a closing quote");
        }

        return list.toArray(new String[0]);
    }

    @NotNull
    public static ArrayList<String> splitWithQuotes(@NotNull String value) {
        value = value.trim();
        if (value.isEmpty()) {
            return new ArrayList<String>();
        }

        int i = 0;
        boolean isQuoteOpen = false;
        int lastQuoteIndex = -1;
        if (value.charAt(0) == '\"') {
            isQuoteOpen = true;
            lastQuoteIndex = 0;
            i++;
        }

        // hello "there lol are ""you"" ok
        ArrayList<String> values = new ArrayList<String>(10);
        StringBuilder sb = new StringBuilder(16);
        for(int len = value.length(); i < len; i++) {
            char c = value.charAt(i);
            if (c == ' ') {
                if (isQuoteOpen) {
                    sb.append(' ');
                }
                else {
                    values.add(sb.toString());
                    sb.setLength(0);
                }
            }
            else if (c == '\"') {
                if (value.charAt(i - 1) == '\\') {
                    sb.append('"');
                    continue;
                }

                isQuoteOpen = !isQuoteOpen;
                lastQuoteIndex = i;
            }
            else if (c == '\\') {
                if (i < (len - 1) && value.charAt(i + 1) == '\"') {
                    i++;
                    sb.append('\"');
                }
                else {
                    sb.append('\\');
                }
            }
            else {
                sb.append(c);
            }
        }

        if (isQuoteOpen) {
            throw new RuntimeException(RZFormats.format("A quote was not closed ('{0}')", StringHelper.getNearby(value, lastQuoteIndex, 1, 4)));
        }

        values.add(sb.toString());
        return values;
    }

    private static final String[] SUFFIXES = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};

    /**
     * Adds "st", "nd", or "rd" to the number based on it's value
     * <p>
     *     e.g, 1st, 2nd, 3rd, 4th, 9th, 11th, 19th, 423rd
     * </p>
     * @exception RuntimeException Thrown if the value is smaller than 0
     */
    public static String getNumberOrdinal(int value) {
        if (value == 0) {
            return "0th";
        }

        boolean negative = (value < 0);
        if (negative) {
            value = -value;
        }

        switch (value % 100) {
            case 11:
            case 12:
            case 13: {
                if (negative) {
                    return new StringBuilder("-").append(value).append("th").toString();
                }
                else {
                    return new StringBuilder(value).append("th").toString();
                }
            }
            default: {
                if (negative) {
                    return new StringBuilder("-").append(value).append(SUFFIXES[value % 10]).toString();
                }
                else {
                    return new StringBuilder(value).append(SUFFIXES[value % 10]).toString();
                }
            }
        }
    }

    public static boolean isWrappedIn(String value, char a, char b) {
        if (value.length() > 2) {
            return value.charAt(0) == a && value.charAt(value.length() - 1) == b;
        }

        return false;
    }

    public static String unWrapValue(String value) {
        if (value.length() > 1) {
            return value.substring(1, value.length() - 1);
        }

        throw new RuntimeException("Value's length was smaller than 3, cannot unwrap the start and end");
    }

    private static final char[] EMPTY_ARRAY = new char[0];

    /**
     * Replaces the given region of text (within the given string) with the given replacement
     * @param string The string to modify
     * @param startIndex The start index of the text to replace
     * @param length The length of the text to replace
     * @param replacement The value to insert at startIndex
     * @return A string where (startIndex) to (startIndex + length - 1) are removed and replacement is inserted at startIndex
     */
    public static String replace(@NotNull String string, int startIndex, int length, String replacement) {
        return new StringBuilder(string.length() + replacement.length() - length).append(string, 0, startIndex).append(replacement).append(string, startIndex + length, string.length()).toString();
    }

    public static String replace(@NotNull String string, @NotNull String oldValue, @NotNull String newValue) {
        return replace(string, oldValue, newValue, 0);
    }

    public static String replace(@NotNull String string, char oldValue, @NotNull String newValue) {
        return replace(string, oldValue, newValue, 0);
    }

    public static String replace(@NotNull String string, char oldValue, @NotNull String newValue, int startIndex) {
        if (string.isEmpty()) {
            return string;
        }

        if (string.indexOf(oldValue, startIndex) == -1) {
            return string;
        }

        return replace(string.toCharArray(), oldValue, newValue.isEmpty() ? EMPTY_ARRAY : newValue.toCharArray(), startIndex);
    }

    public static String replace(@NotNull String string, @NotNull String oldValue, @NotNull String newValue, int startIndex) {
        if (string.isEmpty() || string.length() < oldValue.length() || oldValue.isEmpty()) {
            return string;
        }

        if (string.indexOf(oldValue.charAt(0), startIndex) == -1) {
            return string;
        }

        return replace(string.toCharArray(), oldValue.toCharArray(), newValue.isEmpty() ? EMPTY_ARRAY : newValue.toCharArray(), startIndex);
    }

    public static String replace(@NotNull char[] src, @NotNull char[] oldVal, @NotNull char[] newVal, int startIndex) {
        int i = startIndex;
        if ((i = Memory.indexOf(src, oldVal, i)) >= 0) {
            int oldLen = oldVal.length;
            StringBuilder buf = new StringBuilder(src.length);
            buf.append(src, 0, i).append(newVal);
            i += oldLen;
            int j = i;
            while ((i = Memory.indexOf(src, oldVal, i)) > 0) {
                buf.append(src, j, i - j).append(newVal);
                i += oldLen;
                j = i;
            }

            return buf.append(src, j, src.length - j).toString();
        }

        return new String(src);
    }

    public static String replace(@NotNull char[] src, @NotNull char[] oldVal, char newVal, int startIndex) {
        int i = startIndex;
        if ((i = Memory.indexOf(src, oldVal, i)) >= 0) {
            int oldLen = oldVal.length;
            StringBuilder buf = new StringBuilder(src.length);
            buf.append(src, 0, i).append(newVal);
            i += oldLen;
            int j = i;
            while ((i = Memory.indexOf(src, oldVal, i)) > 0) {
                buf.append(src, j, i - j).append(newVal);
                i += oldLen;
                j = i;
            }

            return buf.append(src, j, src.length - j).toString();
        }

        return new String(src);
    }

    public static String replace(@NotNull char[] src, char oldVal, @NotNull char[] newVal, int startIndex) {
        int i = startIndex;
        if ((i = charsIndexOf(src, oldVal, i)) >= 0) {
            StringBuilder buf = new StringBuilder(src.length);
            buf.append(src, 0, i).append(newVal);
            i++;
            int j = i;
            while ((i = charsIndexOf(src, oldVal, i)) > 0) {
                buf.append(src, j, i - j).append(newVal);
                i++;
                j = i;
            }

            return buf.append(src, j, src.length - j).toString();
        }

        return new String(src);
    }

    public static String replace(@NotNull String src, char oldVal, char newVal) {
        return replace(src, oldVal, newVal, 0);
    }

    public static String replace(@NotNull String src, char oldVal, char newVal, int startIndex) {
        if (oldVal == newVal) {
            return src;
        }

        return replace(src.toCharArray(), oldVal, newVal, startIndex);
    }

    public static String replace(@NotNull char[] src, char oldVal, char newVal, int startIndex) {
        int i = startIndex;
        if ((i = charsIndexOf(src, oldVal, i)) >= 0) {
            StringBuilder buf = new StringBuilder(src.length);
            buf.append(src, 0, i).append(newVal);
            i++;
            int j = i;
            while ((i = charsIndexOf(src, oldVal, i)) > 0) {
                buf.append(src, j, i - j).append(newVal);
                i++;
                j = i;
            }

            return buf.append(src, j, src.length - j).toString();
        }

        return new String(src);
    }
}
