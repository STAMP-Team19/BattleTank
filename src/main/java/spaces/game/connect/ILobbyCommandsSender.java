package spaces.game.connect;

import battletank.lobby.LOBBYCOMMANDS;
import battletank.lobby.PlayerInfo;

public interface ILobbyCommandsSender {
    void sendCommand(PlayerInfo playerInfo, LOBBYCOMMANDS command);
    boolean isLobbyOpen();
}
