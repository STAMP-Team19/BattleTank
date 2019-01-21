package battletank.world.events.go;

import battletank.world.EventVisitor;
import battletank.world.Game;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;

public class DeadPlayerEvent extends Event {

    @Override
    public void accept(GameObject gameObject, EventVisitor visitor) {
        visitor.handle(gameObject,this);
    }
}
