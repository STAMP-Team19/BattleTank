package battletank.lobby;

import battletank.world.GameRules;
import spaces.game.connect.ILobbyListener;
import spaces.game.connect.LobbyCommandsListenerSender;
import spaces.game.hosting.LobbyProvider;

import java.util.Map;

public class LobbyClientConnection{

    public static void main(String[] args){

        try {
        LobbyProvider provider = new LobbyProvider();
        provider.createLobby("Peter", 3, new GameRules());

        Thread.sleep(1000);

        LobbyCommandsListenerSender controller =
                new LobbyCommandsListenerSender("Peter","0.0.0.0", new LobbyClientTest());

        System.out.println("Lobby Open: "+controller.isLobbyOpen());

        controller.sendCommand(new PlayerInfo("Peter"), LOBBYCOMMANDS.OPEN);
        Thread.sleep(1000);
        System.out.println("Lobby Open: "+controller.isLobbyOpen());
        Thread.sleep(1000);
        controller.sendCommand(new PlayerInfo("Peter"), LOBBYCOMMANDS.JOIN);
            Thread.sleep(1000);
        controller.sendCommand(new PlayerInfo("Peter"), LOBBYCOMMANDS.JOIN);
        Thread.sleep(1000);
        controller.sendCommand(new PlayerInfo("Mads"), LOBBYCOMMANDS.JOIN);
        Thread.sleep(1000);
        controller.sendCommand(new PlayerInfo("Troels"), LOBBYCOMMANDS.JOIN);
        Thread.sleep(1000);
        controller.sendCommand(new PlayerInfo("Mads"), LOBBYCOMMANDS.LEAVE);
        Thread.sleep(1000);
        controller.sendCommand(new PlayerInfo("Peter"), LOBBYCOMMANDS.STARTGAME);
        Thread.sleep(1000);
        controller.sendCommand(new PlayerInfo("Mads"), LOBBYCOMMANDS.JOIN);
        Thread.sleep(1000);
        controller.sendCommand(new PlayerInfo("Peter"), LOBBYCOMMANDS.DELETELOBBY);

        System.out.println("Lobby Open: "+controller.isLobbyOpen());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
