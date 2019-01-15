package spaces.game.hosting;

import battletank.lobby.PlayerInfo;
import spaces.game.connect.ILobbyListener;

import java.util.Map;

public class LobbyClient implements ILobbyListener {

    @Override
    public void notifyLobby(Map<String, PlayerInfo> playersList) {
        System.out.println("REFRESH : "+playersList);
    }

    @Override
    public void startGame() {
        System.out.println("STARTGAME");
    }

    @Override
    public void deleteLobby() {
        System.out.println("DELETELOBBY");
    }
}
