package battletank.chat;

import battletank.lobby.PlayerInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;

import java.util.ArrayList;

public class Chat {
    private SequentialSpace chatspace;

    public Chat(SequentialSpace chatspace) {
        this.chatspace = chatspace;

        new Thread(new MessageListener(chatspace)).start();
    }


}

class MessageListener implements Runnable {

    SequentialSpace chatspace;
    ArrayList<PlayerInfo> players;

    public MessageListener(SequentialSpace chatspace) {
        this.chatspace = chatspace;
        players = new ArrayList<>();
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        while (true) {
            try {
                Object[] chatcommand = chatspace.get(new FormalField(String.class), new FormalField(String.class));
                String target = (String) chatcommand[0];
                String message = (String) chatcommand[1];

                System.out.println("PL"+players);

                 if (target.equals("UPDATE_ONCE")) {
                    PlayerInfo pl = gson.fromJson((String) chatcommand[1], PlayerInfo.class);
                    players.add(pl);

                } else {
                    for (PlayerInfo player : players) {
                        chatspace.put(player.getName(), target, message);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
