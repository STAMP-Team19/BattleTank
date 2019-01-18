package spaces.game.connect.chat;

public class ChatMsgManager implements IChatMsgManager {
    @Override
    public void sendMessage(String username, String message) {
        System.out.println("Message '"+message+"' from "+username+" sent.");
    }

    @Override
    public void sendInformationEvent(String event) {
        System.out.println("Event Message "+event+" sent.");
    }
}
