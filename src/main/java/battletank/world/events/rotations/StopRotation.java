package battletank.world.events.rotations;

import battletank.world.EventVisitor;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;

public class StopRotation extends Event {
    public StopRotation(){}


    @Override
    public void accept(GameObject gameObject, EventVisitor visitor) {
        visitor.handle(gameObject,this);
    }
}
