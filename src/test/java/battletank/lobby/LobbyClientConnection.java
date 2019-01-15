package battletank.lobby;

import battletank.world.GameRules;
import spaces.game.connect.ILobbyListener;
import spaces.game.connect.LobbyCommandsListenerSender;
import spaces.game.hosting.LobbyProvider;

import java.util.Map;

public class LobbyClientConnection implements ILobbyListener {

    public void main(String[] args){
        LobbyProvider provider = new LobbyProvider();
        provider.createLobby("Peter", 3, new GameRules());
        LobbyCommandsListenerSender controller =
                new LobbyCommandsListenerSender("Peter", this);

        controller.sendCommand(new PlayerInfo("Arvid"), LOBBYCOMMANDS.JOIN);
        controller.sendCommand(new PlayerInfo("Mads"), LOBBYCOMMANDS.JOIN);
    }

    @Override
    public void notifyLobby(Map<String, PlayerInfo> playersList) {
        System.out.println("REFRESH : "+playersList);
    }

    @Override
    public void deleteLobby() {
        System.out.println("DELETELOBBY");
    }
}
