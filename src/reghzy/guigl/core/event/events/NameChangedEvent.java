package reghzy.guigl.core.event.events;

import reghzy.guigl.core.event.RoutedEvent;

public class NameChangedEvent extends RoutedEvent {
    private String oldName;
    private String newName;

    public NameChangedEvent(String oldName, String newName) {

        this.oldName = oldName;
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}