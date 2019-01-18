package spaces.game.connect.chat;

import battletank.lobby.PlayerInfo;
import com.google.gson.Gson;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.util.ArrayList;

public class ChatMsgManager implements IChatMsgManager {

    private RemoteSpace chatspace;

    public ChatMsgManager(String username, IChatListener listener, String ip) {
        String uri = "tcp://"+ip+":9003/chat?keep";

        try {
            chatspace = new RemoteSpace(uri);

            new Thread(new ChatObserver(username, chatspace, listener)).start();
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

    @Override
    public void addPlayer(PlayerInfo player) {
        try {
            chatspace.put("UPDATE_ONCE", new Gson().toJson(player));
            chatspace.put("[SERVER]", player.getName()+" has joined the chat.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ChatObserver implements Runnable{

    RemoteSpace chatspace;
    IChatListener listener;
    String username;

    public ChatObserver(String username, RemoteSpace chatspace, IChatListener listener) {
        this.chatspace = chatspace;
        this.listener = listener;
        this.username = username;
    }

    @Override
    public void run() {
        while(true){
            try {
                Object[] chatmessage = chatspace.get(new ActualField(username),
                        new FormalField(String.class),
                        new FormalField(String.class));

                String author = (String) chatmessage[1];
                String message = (String) chatmessage[2];

                listener.notifyNewMessage(author, message);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
