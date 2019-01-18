package spaces.game.hosting;

import battletank.lobby.PlayerInfo;
import spaces.game.connect.ILobbyListener;

import java.util.ArrayList;
import java.util.Map;

public class LobbyClient implements ILobbyListener {

    @Override
    public void notifyLobby(ArrayList<PlayerInfo> playersList) {
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

    @Override
    public void notifyLobbymap(int level) {
        System.out.println("LEVEL: " + level);
    }

    @Override
    public void endGame() {
        System.out.println("ENDGAME");
    }
}
