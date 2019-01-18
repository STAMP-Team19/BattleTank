package spaces.game.connect.chat;

public interface IChatListener {
    void notifyNewMessage(String username, String message);
}
