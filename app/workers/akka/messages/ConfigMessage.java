package workers.akka.messages;

/**
 * author aybek.bukabayev
 */
public class ConfigMessage {
    private final int clientId;
    private final String config;

    public ConfigMessage(String config,int clientId) {
        this.config = config;
        this.clientId = clientId;
    }

    public String getConfig(){
        return config;
    }

    public int getClientId() {
        return clientId;
    }
}
