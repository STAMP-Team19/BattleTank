package spaces.game.connect.chat;

import battletank.lobby.PlayerInfo;

import java.util.ArrayList;

public interface IChatMsgManager {
    void sendMessage(String username, String message);
    void sendInformationEvent(String event);
    void updatePlayers(ArrayList<PlayerInfo> players);
    void addPlayer(PlayerInfo player);
}
