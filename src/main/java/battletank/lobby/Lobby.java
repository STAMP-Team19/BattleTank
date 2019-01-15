package battletank.lobby;

import battletank.world.Game;
import battletank.world.GameRules;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import spaces.game.hosting.GameHost;

import java.util.HashMap;

public class Lobby {

    private int numberOfMaxPlayers;
    private String hostname;
    private Game game;

    private SequentialSpace lobbyspace;

    public Lobby(String hostname, int numberOfMaxPlayers, GameRules rules, SequentialSpace lobbyspace){
        this.numberOfMaxPlayers = numberOfMaxPlayers;
        this.hostname = hostname;
        this.lobbyspace = lobbyspace;

        new Thread(new CommandsListener(lobbyspace, numberOfMaxPlayers, rules)).start();
    }

    public int getNumberOfMaxPlayers() {
        return numberOfMaxPlayers;
    }

    public String getHostname() {
        return hostname;
    }

    public Game getGame() {
        return game;
    }

}

class CommandsListener implements Runnable{

    GameRules rules;
    SequentialSpace lobbyspace;
    HashMap<String, PlayerInfo> info;
    int numberOfMaxPlayers;
    int numberOfActualPlayers;

    CommandsListener(SequentialSpace lobbyspace, int numberOfMaxPlayers, GameRules rules){
        this.lobbyspace = lobbyspace;
        this.numberOfMaxPlayers = numberOfMaxPlayers;
        this.rules = rules;
        numberOfActualPlayers = 0;
    }

    @Override
    public void run() {
        while(true){
            try {
                Object[] command = lobbyspace.get(new FormalField(PlayerInfo.class), new FormalField(LOBBYCOMMANDS.class));

                PlayerInfo playerInfo = (PlayerInfo) command[0];
                LOBBYCOMMANDS com = (LOBBYCOMMANDS) command[1];

                switch(com){
                    case JOIN:
                        if(numberOfActualPlayers<numberOfMaxPlayers) {
                            info.put(playerInfo.getName(), playerInfo);
                            for (PlayerInfo player : info.values()) {
                                lobbyspace.put(player.getName(), info, LOBBYCOMMANDS.REFRESH);
                            }
                            ++numberOfActualPlayers;
                        }
                        break;

                    case CHANGERULES:
                        break;

                    case DELETELOBBY:
                        for (PlayerInfo player : info.values()) {
                            lobbyspace.put(player.getName(), info, LOBBYCOMMANDS.DELETELOBBY);
                        }
                        Thread.currentThread().interrupt();
                        break;

                    case STARTGAME:
                        if(numberOfActualPlayers==numberOfMaxPlayers){
                            GameHost gameHost = new GameHost(new Game(rules, info));
                        }

                        break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
