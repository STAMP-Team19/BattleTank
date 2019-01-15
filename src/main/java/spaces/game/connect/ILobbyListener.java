package spaces.game.connect;

import battletank.lobby.PlayerInfo;

import java.util.Map;

public interface ILobbyListener {
    void notifyLobby(Map<String, PlayerInfo> playersList);
    void deleteLobby();
}
