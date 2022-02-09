package reghzy.guigl.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FolderResource {
    private final File file;

    public FolderResource(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public boolean exists() {
        return this.file.exists();
    }

    public FileResource getResource(String path) {
        return new FileResource(new File(this.file, path));
    }
}
