package spaces.game.connect.chat;

public class ChatMsgManager implements IChatMsgManager {

    private IChatListener listener;
    private String ip;

    public ChatMsgManager(String username, IChatListener listener, String ip) {
        this.listener = listener;
        this.ip = ip;
    }

    @Override
    public void sendMessage(String username, String message) {
        System.out.println("Message '"+message+"' from "+username+" sent.");
    }

    @Override
    public void sendInformationEvent(String event) {
        System.out.println("Event Message "+event+" sent.");
    }
}
