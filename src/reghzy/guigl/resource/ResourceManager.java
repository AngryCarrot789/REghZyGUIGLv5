package reghzy.guigl.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourceManager {
    private final File file;

    public ResourceManager(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public InputStream getStream(FileResource resource) {
        return resource.getStream();
    }

    public FileResource getStream(String path) {
        return new FileResource(new File(this.file, path));
    }

    public FolderResource getFolder(String path) {
        return new FolderResource(new File(this.file, path));
    }

    public File getResource(String path) {
        return new File(this.file, path);
    }

    public File getResource(String directory, String path) {
        return new File(new File(this.file, directory), path);
    }

    public InputStream getInput(String path) {
        try {
            return new FileInputStream(new File(this.file, path));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + path, e);
        }
    }

    public InputStream getInput(String directory, String path) {
        try {
            return new FileInputStream(new File(new File(this.file, directory), path));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + path, e);
        }
    }
}
