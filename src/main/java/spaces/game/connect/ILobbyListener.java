package spaces.game.connect;

import battletank.lobby.PlayerInfo;

import java.util.ArrayList;
import java.util.Map;

public interface ILobbyListener {
    void notifyLobby(ArrayList<PlayerInfo> playersList);
    void startGame();
    void deleteLobby();
    void notifyLobbymap(int level);
    void endGame();
}
