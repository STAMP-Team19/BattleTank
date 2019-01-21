package battletank.lobby;

import battletank.world.Game;
import battletank.world.GameRules;
import com.google.gson.Gson;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;
import spaces.game.hosting.GameHost;
import spaces.game.hosting.chat.ChatProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    public static boolean isReady=false;

    private int numberOfMaxPlayers;
    private String hostname;
    private Game game;

    private SequentialSpace lobbyspace;

    public Lobby(String hostname, int numberOfMaxPlayers, GameRules rules, SequentialSpace lobbyspace, SpaceRepository spaceRepository, int level){
        this.numberOfMaxPlayers = numberOfMaxPlayers;
        this.hostname = hostname;
        this.lobbyspace = lobbyspace;

        //Chat creation
        new ChatProvider().createChat();

        new Thread(new CommandsListener(lobbyspace, numberOfMaxPlayers, rules, spaceRepository, hostname, level)).start();
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

    public boolean isDone() {
        return isReady;
    }
}

class CommandsListener implements Runnable{

    String hostname;
    GameRules rules;
    SequentialSpace lobbyspace;
    ArrayList<PlayerInfo> info;
    int numberOfMaxPlayers;
    int numberOfActualPlayers;
    int level;

    boolean isOpen;

    SpaceRepository spaceRepository;

    CommandsListener(SequentialSpace lobbyspace, int numberOfMaxPlayers, GameRules rules, SpaceRepository spaceRepository, String hostname, int level){
        this.hostname = hostname;
        this.lobbyspace = lobbyspace;
        this.numberOfMaxPlayers = numberOfMaxPlayers;
        this.rules = rules;
        this.spaceRepository = spaceRepository;
        numberOfActualPlayers = 0;
        this.level = level;

        info = new ArrayList<>();
        isOpen = false;
    }

    @Override
    public void run() {

        try {
            lobbyspace.put("STATUS", isOpen);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Lobby.isReady = true;
        loop: while(true){
            Gson gson = new Gson();
            String playerinfodata;
            try {
                Object[] command = lobbyspace.get(new FormalField(PlayerInfo.class), new FormalField(LOBBYCOMMANDS.class));
                PlayerInfo playerInfo = (PlayerInfo) command[0];
                LOBBYCOMMANDS com = (LOBBYCOMMANDS) command[1];



                switch(com){
                    case JOIN:
                        if(numberOfActualPlayers<numberOfMaxPlayers
                                && isOpen
                                && !info.contains(playerInfo)) {
                            info.add(playerInfo);
                            playerinfodata = gson.toJson(info);
                            for (PlayerInfo player : info) {
                                lobbyspace.put(player.getName(), playerinfodata, LOBBYCOMMANDS.REFRESH);
                                lobbyspace.put(player.getName(), level+"", LOBBYCOMMANDS.SETMAP);
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
                        info.remove(playerInfo);
                        playerinfodata = gson.toJson(info);
                        for (PlayerInfo player : info) {
                            lobbyspace.put(player.getName(), playerinfodata, LOBBYCOMMANDS.REFRESH);
                        }
                        --numberOfActualPlayers;
                        break;

                    case CHANGERULES:
                        break;

                    case DELETELOBBY:
                        isOpen = false;

                        lobbyspace.put("STATUS", isOpen);
                        playerinfodata = gson.toJson(info);
                        for (PlayerInfo player : info) {
                            lobbyspace.put(player.getName(), playerinfodata, LOBBYCOMMANDS.DELETELOBBY);
                        }
                        info = new ArrayList<>();
                        System.out.println("The lobby has been closed.");

                        break;

                    case STARTGAME:
                        if(isOpen
                                && playerInfo.getName().equals(hostname)){
                            playerinfodata = gson.toJson(info);
                            for (PlayerInfo player : info) {
                                lobbyspace.put(player.getName(), playerinfodata, LOBBYCOMMANDS.STARTGAME);
                            }

                            GameHost gameHost = new GameHost(new Game(rules, info, level));
                        }
                        break;
                    case SETMAP:
                        if(isOpen && playerInfo.getName().equals(hostname)){
                            for (PlayerInfo player : info) {
                                lobbyspace.put(player.getName(),""+level, LOBBYCOMMANDS.SETMAP);
                            }
                        }
                        break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
