package battletank.world;

import battletank.IGame;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;
import battletank.world.gameobjects.Player;
import spaces.game.hosting.WorldGateway;

import java.util.HashMap;
import java.util.Set;

public class Game implements IGame {
    private WorldGateway worldGateway;
    private WorldSimulator worldSimulator;

    private HashMap<String, Player> players;

    public Game(HashMap<String,Player> players){
        this.players=players;
        worldSimulator = new WorldSimulator(new DeltaTime());

        for(Player p :players.values())
            worldSimulator.addGameObject(p);

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
