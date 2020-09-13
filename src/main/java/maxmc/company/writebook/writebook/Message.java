package maxmc.company.writebook.writebook;



/**
 * @author SanseYooyea
 */

public enum Message {
    /**
     *
     */
    successMessage("");

    public String getMessage() {
        return message;
    }

    private final String message;

    private Message(String message) {
        this.message = message;
    }
}
