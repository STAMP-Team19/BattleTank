package battletank.world;

import battletank.lobby.PlayerInfo;
import battletank.world.events.Event;
import battletank.world.events.rotations.StopRotation;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.Player;
import battletank.world.gameobjects.PlayerColor;
import spaces.game.hosting.WorldGateway;

import java.util.HashMap;
import java.util.Set;

public class Game implements IGame {
    private WorldGateway worldGateway;
    private WorldSimulator worldSimulator;

    private HashMap<String, Player> players;
    private HashMap<String, PlayerInfo> playersinfo;

    public Game(HashMap<String,Player> players){
        this.players=players;
        worldSimulator = new WorldSimulator(new DeltaTime());

        for(Player p :players.values()) {
            worldSimulator.addGameObject(p);
            worldGateway.update(p,new StopTransition());
        }


        new Thread(worldSimulator).start();
    }

    public Game(GameRules rules, HashMap<String,PlayerInfo> playersinfo){
        this.playersinfo = playersinfo;

        int index = 0;
        double[] xCoord = {96,672,416,416};
        double[] yCoord = {416,416,672,96};
        PlayerColor[] colors = PlayerColor.values();

        for(String playerName : playersinfo.keySet()){
            Player player = new Player(playerName,
                    (int)xCoord[index],
                    (int)yCoord[index],
                    134/4,
                    249/4,
                    90,
                    50,
                    90,
                    100,
                    colors[index]);


            worldSimulator.addGameObject(player);
            worldGateway.update(player,new StopTransition());
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
