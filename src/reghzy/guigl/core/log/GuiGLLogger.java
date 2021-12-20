package reghzy.guigl.core.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GuiGLLogger {
    private final SimpleDateFormat timeFormat;
    private final String name;

    public GuiGLLogger(String name) {
        this.name = name;
        this.timeFormat = new SimpleDateFormat("hh:mm:ss");
    }

    public void info(String text) {
        write("INFO", text);
    }

    public void warn(String text) {
        write("WARNING", text);
    }

    public void error(String text) {
        write("ERROR", text);
    }

    private void write(String level, String message) {
        System.out.println(new StringBuffer().append(this.timeFormat.format(new Date())).append(" [").append(this.name).append("] [").append(level).append("] ").append(message));
    }
}
