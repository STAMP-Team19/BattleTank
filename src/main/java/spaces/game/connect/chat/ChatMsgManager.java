package spaces.game.connect.chat;

import battletank.lobby.PlayerInfo;
import com.google.gson.Gson;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.util.ArrayList;

public class ChatMsgManager implements IChatMsgManager {

    private RemoteSpace chatspace;

    public ChatMsgManager(String username, IChatListener listener, String ip) {
        String uri = "tcp://"+ip+":9003/chat?keep";

        try {
            chatspace = new RemoteSpace(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String username, String message) {
        System.out.println("Message '"+message+"' from "+username+" sent.");
        try {
            chatspace.put(username, message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendInformationEvent(String event) {
        System.out.println("Event Message "+event+" sent.");
        try {
            chatspace.put("[EVENT]", event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePlayers(ArrayList<PlayerInfo> players) {
        try {
            chatspace.put("UPDATE", new Gson().toJson(players));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
