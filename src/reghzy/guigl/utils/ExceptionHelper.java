package reghzy.guigl.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reghzy.guigl.core.log.ChatColour;
import reghzy.guigl.core.log.GuiGLLogger;

public class ExceptionHelper {
    private static final ChatColour PACKAGE_COLOUR = ChatColour.YELLOW;
    private static final ChatColour CLASS_COLOUR = ChatColour.GREEN;
    private static final ChatColour UNMAMPPED_METHOD = ChatColour.AQUA;
    private static final ChatColour REMAMPPED_METHOD = ChatColour.DARK_AQUA;
    private static final ChatColour SOURCE_NATIVE = ChatColour.RED;
    private static final ChatColour SOURCE_FILE = ChatColour.DARK_GRAY;
    private static final ChatColour SOURCE_UNKNOWN = ChatColour.RED;
    
    public static void printException(@NotNull Throwable throwable, @NotNull GuiGLLogger logger) {
        try {
            logger.infoNoLabel("&6" + throwable);
            StackTraceElement[] enclosing = throwable.getStackTrace();
            printStackTraceColour(enclosing, logger);
            Throwable cause = throwable.getCause();
            int loop = 0;
            while (cause != null) {
                if (++loop > 50) {
                    logger.infoFormat("50+ exception loop!");
                    return;
                }

                logger.infoFormatNoLabel("&eCaused By &6{0}", cause);
                StackTraceElement[] trace = cause.getStackTrace();
                int m = trace.length - 1;
                int n = enclosing.length - 1;
                while (m >= 0 && n >= 0 && trace[m].equals(enclosing[n])) {
                    m--;
                    n--;
                }

                // the '- 1' is used to keep the cause of the lower stack trace from the above one
                int framesInCommon = trace.length - 1 - m;
                for (int i = 0; i <= m; i++) {
                    logger.infoFormatNoLabel("    &7at {0}", formatStackTrace(trace[i]));
                }

                if (framesInCommon != 0) {
                    logger.infoFormatNoLabel("    &7... &c{0} &7more", framesInCommon);
                }

                cause = cause.getCause();
                enclosing = trace;
            }
        }
        catch (Throwable e) {
            throw new RuntimeException("Failed to print exception", e);
        }
    }

    public static void printStackTraceColour(StackTraceElement[] trace, GuiGLLogger logger) {
        for (StackTraceElement element : trace) {
            logger.infoFormatNoLabel("    &7at {0}", formatStackTrace(element));
        }
    }

    public static void printStackTraceColour(StackTraceElement[] trace, int start, int end, GuiGLLogger logger, boolean console) {
        for (int i = start, ending = Math.min(trace.length, end); i < ending; i++) {
            logger.infoFormatNoLabel("    &7at {0}", formatStackTrace(trace[i]));
        }
    }

    public static String formatStackTrace(StackTraceElement element) {
        int lineNumber = element.getLineNumber();
        String className = element.getClassName();
        String packageName = null;
        int lastSplit = className.lastIndexOf('.');
        if (lastSplit != -1) {
            packageName = className.substring(0, lastSplit);
            className = className.substring(lastSplit + 1);
        }

        String fileName = element.getFileName();
        String originalMethod = element.getMethodName();
        StringBuilder string = new StringBuilder(128);
        if (packageName == null) {
            string.append(PACKAGE_COLOUR).append(className).append(".");
        }
        else {
            string.append(PACKAGE_COLOUR).append(packageName).append(".").append(CLASS_COLOUR).append(className).append(PACKAGE_COLOUR).append('.');
        }

        ChatColour methodColour = UNMAMPPED_METHOD;
        string.append(methodColour).append(originalMethod).append("(");
        if (element.isNativeMethod()) {
            string.append(SOURCE_NATIVE).append("Native Method");
        }
        else if (fileName != null && lineNumber >= 0) {
            string.append(SOURCE_FILE).append(ChatColour.BOLD).append(fileName).append(":").append(lineNumber);
        }
        else if (fileName == null) {
            string.append(SOURCE_UNKNOWN).append("Unknown Source");
        }
        else {
            string.append(SOURCE_FILE).append(ChatColour.BOLD).append(fileName);
        }

        return string.append(methodColour).append(')').toString();
    }
}
