package battletank.lobby;

import battletank.world.Game;
import battletank.world.GameRules;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

import java.util.HashMap;

public class Lobby {

    private int numberOfMaxPlayers;
    private String hostname;
    private Game game;

    public Lobby(String hostname, int numberOfMaxPlayers, GameRules rules){
        this.numberOfMaxPlayers = numberOfMaxPlayers;
        this.hostname = hostname;
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

    SequentialSpace lobbyspace;
    HashMap<String, PlayerInfo> info;

    CommandsListener(SequentialSpace lobbyspace){
        this.lobbyspace = lobbyspace;
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
                        info.put(playerInfo.getName(), playerInfo);
                        for(PlayerInfo player:info.values()){
                            lobbyspace.put(player.getName(), info, LOBBYCOMMANDS.REFRESH);
                        }
                        break;

                    case CHANGERULES:
                        break;

                    case DELETELOBBY:
                        break;

                    case STARTGAME:
                        break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
