package workers.akka.messages;

/**
 * author aybek.bukabayev
 */
public class StatusUpdate {
    private final int likes;

    public StatusUpdate(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

}
