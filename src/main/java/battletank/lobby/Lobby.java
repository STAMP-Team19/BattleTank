package battletank.lobby;

import battletank.world.Game;
import battletank.world.GameRules;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;
import spaces.game.hosting.GameHost;

import java.util.HashMap;

public class Lobby {

    private int numberOfMaxPlayers;
    private String hostname;
    private Game game;

    private SequentialSpace lobbyspace;

    public Lobby(String hostname, int numberOfMaxPlayers, GameRules rules, SequentialSpace lobbyspace, SpaceRepository spaceRepository){
        this.numberOfMaxPlayers = numberOfMaxPlayers;
        this.hostname = hostname;
        this.lobbyspace = lobbyspace;

        new Thread(new CommandsListener(lobbyspace, numberOfMaxPlayers, rules, spaceRepository)).start();
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

    boolean isOpen;

    SpaceRepository spaceRepository;

    CommandsListener(SequentialSpace lobbyspace, int numberOfMaxPlayers, GameRules rules, SpaceRepository spaceRepository){
        this.lobbyspace = lobbyspace;
        this.numberOfMaxPlayers = numberOfMaxPlayers;
        this.rules = rules;
        this.spaceRepository = spaceRepository;
        numberOfActualPlayers = 0;

        info = new HashMap<>();
        isOpen = false;
    }

    @Override
    public void run() {

        try {
            lobbyspace.put("STATUS", isOpen);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loop: while(true){
            try {
                Object[] command = lobbyspace.get(new FormalField(PlayerInfo.class), new FormalField(LOBBYCOMMANDS.class));
                PlayerInfo playerInfo = (PlayerInfo) command[0];
                LOBBYCOMMANDS com = (LOBBYCOMMANDS) command[1];

                switch(com){
                    case JOIN:
                        if(numberOfActualPlayers<numberOfMaxPlayers && isOpen) {
                            info.put(playerInfo.getName(), playerInfo);
                            for (PlayerInfo player : info.values()) {
                                lobbyspace.put(player.getName(), info, LOBBYCOMMANDS.REFRESH);
                            }
                            ++numberOfActualPlayers;
                        }
                        break;

                    case OPEN:
                        isOpen = true;
                        lobbyspace.get(new ActualField("STATUS"), new FormalField(Boolean.class));
                        lobbyspace.put("STATUS", isOpen);

                        System.out.println("The lobby has been opened.");
                        break;

                    case LEAVE:
                        info.remove(playerInfo.getName());
                        for (PlayerInfo player : info.values()) {
                            lobbyspace.put(player.getName(), info, LOBBYCOMMANDS.REFRESH);
                        }
                        --numberOfActualPlayers;
                        break;

                    case CHANGERULES:
                        break;

                    case DELETELOBBY:
                        isOpen = false;

                        lobbyspace.put("STATUS", isOpen);

                        for (PlayerInfo player : info.values()) {
                            lobbyspace.put(player.getName(), info, LOBBYCOMMANDS.DELETELOBBY);
                        }
                        info = new HashMap<>();
                        System.out.println("The lobby has been closed.");

                        break;

                    case STARTGAME:
                        if(numberOfActualPlayers==numberOfMaxPlayers && isOpen){
                            for (PlayerInfo player : info.values()) {
                                lobbyspace.put(player.getName(), info, LOBBYCOMMANDS.STARTGAME);
                            }

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
