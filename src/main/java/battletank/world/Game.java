package battletank.world;

import battletank.IGame;
import battletank.world.gameobjects.Player;
import spaces.game.hosting.WorldGateway;

import java.util.HashMap;
import java.util.Set;

public class Game implements IGame {
    private WorldGateway worldGateway;

    private HashMap<String, Player> players;

    public Game(HashMap<String,Player> players){
        this.players=players;
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
