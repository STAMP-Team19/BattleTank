package spaces.game.connect.chat;

public interface IChatMsgManager {
    void sendMessage(String username, String message);
    void sendInformationEvent(String event);
}
