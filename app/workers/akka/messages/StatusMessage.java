package workers.akka.messages;

/**
 * author aybek.bukabayev
 */
public class StatusMessage {
    private final String message;

    public StatusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
