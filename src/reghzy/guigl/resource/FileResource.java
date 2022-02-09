package reghzy.guigl.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileResource {
    private final File file;

    public FileResource(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public boolean exists() {
        return this.file.exists();
    }

    public InputStream getStream() {
        try {
            return new FileInputStream(this.file);
        }
        catch (FileNotFoundException e) {
            throw new ResourceNotFoundException("File does not exist with path: " + this.file.getAbsolutePath());
        }
    }
}
