package battletank.world;

import battletank.lobby.Lobby;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;
import org.jspace.SequentialSpace;

public class WorldController {

    private SequentialSpace worldEvents;

    public WorldController(SequentialSpace worldEvents){
        this.worldEvents = worldEvents;
    }

    public void update(GameObject gameObject, Event event){
        for(String player : Lobby.players){
            try {
                worldEvents.put(player, gameObject, event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
