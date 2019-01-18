package battletank.lobby;

import spaces.game.connect.ILobbyListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class LobbyClientTest implements ILobbyListener {

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
    public void notifyLobbymap(int level) { System.out.println("SETMAP");
    }

    @Override
    public void endGame() {
        System.out.println("ENDGAME");
    }
}
