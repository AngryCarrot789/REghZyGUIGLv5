package reghzy.guigl.core.event;

public enum EventPriority {
    /**
     * Event must be received first
     */
    HIGHEST,

    /**
     * Event must be received very soon after it is fired
     */
    HIGH,

    /**
     * The event isn't that important; it can be received just normally, after the important stuff
     */
    NORMAL,

    /**
     * Event has a low priority, where it will usually be received last
     */
    LOW,

    /**
     * The event is received last, and is not modified at all; this should only be used for monitoring
     */
    MONITOR
}
