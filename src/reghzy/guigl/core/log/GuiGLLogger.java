package reghzy.guigl.core.log;

import org.fusesource.jansi.Ansi;
import reghzy.guigl.utils.ExceptionHelper;
import reghzy.guigl.utils.RZFormats;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

public class GuiGLLogger {
    protected final SimpleDateFormat timeFormat;
    protected final String name;
    protected final Map<ChatColour, String> replacements = new EnumMap<ChatColour, String>(ChatColour.class);
    protected final ChatColour[] colors = ChatColour.values();

    public static final String LABEL_INFO = "§r[§eINFO§r]";
    public static final String LABEL_WARN = "§r[§cWARNING§r]";
    public static final String LABEL_ERR0 = "§r[§4ERROR§r]";

    public GuiGLLogger(String name) {
        this.name = name;
        this.timeFormat = new SimpleDateFormat("hh:mm:ss.SSS");
        this.replacements.put(ChatColour.BLACK, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString());
        this.replacements.put(ChatColour.DARK_BLUE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString());
        this.replacements.put(ChatColour.DARK_GREEN, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString());
        this.replacements.put(ChatColour.DARK_AQUA, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString());
        this.replacements.put(ChatColour.DARK_RED, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString());
        this.replacements.put(ChatColour.DARK_PURPLE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString());
        this.replacements.put(ChatColour.GOLD, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString());
        this.replacements.put(ChatColour.GRAY, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString());
        this.replacements.put(ChatColour.DARK_GRAY, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).bold().toString());
        this.replacements.put(ChatColour.BLUE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString());
        this.replacements.put(ChatColour.GREEN, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString());
        this.replacements.put(ChatColour.AQUA, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString());
        this.replacements.put(ChatColour.RED, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString());
        this.replacements.put(ChatColour.LIGHT_PURPLE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString());
        this.replacements.put(ChatColour.YELLOW, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString());
        this.replacements.put(ChatColour.WHITE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString());
        this.replacements.put(ChatColour.MAGIC, Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString());
        this.replacements.put(ChatColour.BOLD, Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString());
        this.replacements.put(ChatColour.STRIKETHROUGH, Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString());
        this.replacements.put(ChatColour.UNDERLINE, Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString());
        this.replacements.put(ChatColour.ITALIC, Ansi.ansi().a(Ansi.Attribute.ITALIC).toString());
        this.replacements.put(ChatColour.RESET, Ansi.ansi().a(Ansi.Attribute.RESET).toString());
    }

    public void info(String text) {
        write(LABEL_INFO, text);
    }

    public void infoFormat(String format, Object... args) {
        write(LABEL_INFO, RZFormats.formatColour(format, args));
    }

    public void warn(String text) {
        write(LABEL_WARN, text);
    }

    public void warnFormat(String format, Object... args) {
        write(LABEL_WARN, RZFormats.formatColour(format, args));
    }

    public void error(String text) {
        write(LABEL_ERR0, text);
    }

    public void errorFormat(String format, Object... args) {
        write(LABEL_ERR0, RZFormats.formatColour(format, args));
    }


    public void infoNoLabel(String text) {
        write("", text);
    }

    public void infoFormatNoLabel(String format, Object... args) {
        write("", RZFormats.formatColour(format, args));
    }

    public void warnNoLabel(String text) {
        write("", text);
    }

    public void warnFormatNoLabel(String format, Object... args) {
        write("", RZFormats.formatColour(format, args));
    }

    public void errorNoLabel(String text) {
        write("", text);
    }

    public void errorFormatNoLabel(String format, Object... args) {
        write("", RZFormats.formatColour(format, args));
    }

    public void write(String level, String message) {
        String result = level + ' ' + message;
        Map<ChatColour, String> replacements = this.replacements;
        for (ChatColour color : this.colors) {
            String replace = replacements.get(color);
            if (replace == null) {
                result = result.replaceAll("(?i)" + color.toString(), "");
            }
            else {
                result = result.replaceAll("(?i)" + color.toString(), replace);
            }
        }

        System.out.println(RZFormats.format("[{0}] [{1}] {2}", this.timeFormat.format(new Date()), this.name, result + Ansi.ansi().reset().toString()));
    }

    public void printException(Throwable e) {
        ExceptionHelper.printException(e, this);
    }
}
