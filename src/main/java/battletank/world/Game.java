package battletank.world;

import battletank.lobby.PlayerInfo;
import battletank.world.events.Event;
import battletank.world.events.rotations.StopRotation;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.Player;
import battletank.world.gameobjects.PlayerColor;
import spaces.game.hosting.WorldGateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Game implements IGame {
    private WorldGateway worldGateway;
    private WorldSimulator worldSimulator;

    private HashMap<String, Player> players=new HashMap<>();
    private ArrayList<PlayerInfo> playersinfo;

    public Game(HashMap<String,Player> players, int level){
        this.players=players;
        worldSimulator = new WorldSimulator(new DeltaTime(), level, new GameRules());

        for(Player p :players.values()) {
            worldSimulator.addGameObject(p);
        }


        new Thread(worldSimulator).start();
    }

    public Game(GameRules rules, ArrayList<PlayerInfo> playersinfo, int level){
        this.playersinfo = playersinfo;
        worldSimulator = new WorldSimulator(new DeltaTime(), level,rules);

        int index = 0;
        double[] xCoord = {80,700,416,416};
        double[] yCoord = {416,416,700,40};
        int[] rotation = {0,180,270,90};
        PlayerColor[] colors = PlayerColor.values();

        for(PlayerInfo playerInfo : playersinfo){
            Player player = new Player(playerInfo.getName(),
                    (int)xCoord[index],
                    (int)yCoord[index],
                    134/4,
                    249/4,
                    rotation[index],
                    50,
                    90,
                    100,
                    colors[index]);
            players.put(player.getName(),player);

            worldSimulator.addGameObject(player);
            index++;
        }


        new Thread(worldSimulator).start();
    }
    @Override
    public void addPlayerEvent(Player player, Event event){
        worldSimulator.addEvent(player,event);
    }



    public void setWorldGateway(WorldGateway worldGateway){
        this.worldGateway = worldGateway;
        worldSimulator.setGateway(worldGateway);
    }

    @Override
    public WorldGateway getWorldGateway() {
        return worldGateway;
    }


    public Player getPlayer(String name) {
        return players.get(name);
    }

    @Override
    public Set<String> getPlayerNames() {
        return players.keySet();
    }

}
