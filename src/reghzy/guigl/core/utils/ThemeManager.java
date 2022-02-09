package reghzy.guigl.core.utils;

import reghzy.guigl.resource.FileResource;
import reghzy.guigl.resource.FolderResource;
import reghzy.guigl.resource.ResourceNotFoundException;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class ThemeManager {
    private final ResourceMap map;
    private final FolderResource folder;
    private final DocumentBuilderFactory documentFactory;

    public ThemeManager(FolderResource folder) {
        this.folder = folder;
        this.map = new ResourceMap();
        this.documentFactory = DocumentBuilderFactory.newInstance();
    }

    public void loadTheme(String name) {
        FileResource resource = this.folder.getResource(name);
        if (!resource.exists()) {
            throw new ResourceNotFoundException("Missing theme resource: " + name);
        }

        loadThemeFromInput(resource.getStream());
    }

    public void loadThemeFromInput(InputStream inputStream) {

    }

    public enum ThemeTypes {
        DARK("dark.xml");

        public final String name;
        ThemeTypes(String name) {
            this.name = name;
        }
    }
}
