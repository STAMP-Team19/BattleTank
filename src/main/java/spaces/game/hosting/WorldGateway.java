package spaces.game.hosting;

import battletank.world.IGame;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;
import com.google.gson.Gson;
import org.jspace.SequentialSpace;

public class WorldGateway {

    private final IGame game;
    private SequentialSpace worldEvents;

    public WorldGateway(SequentialSpace worldEvents, IGame game){
        this.worldEvents = worldEvents;
        this.game=game;
    }

    public void update(GameObject gameObject, Event event){
        for(String player : game.getPlayerNames()){
            try {
                worldEvents.put(player, gameObject, event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
