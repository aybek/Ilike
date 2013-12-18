package workers.akka.messages;

/**
 * author aybek.bukabayev
 */
public class ResultMessage {
    private final String result;

    public ResultMessage(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}

