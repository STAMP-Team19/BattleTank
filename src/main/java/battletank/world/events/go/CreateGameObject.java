package battletank.world.events.go;

import battletank.world.EventVisitor;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;

public class CreateGameObject extends Event {
    public CreateGameObject(int delay){
        super(delay);
    }


    @Override
    public void accept(GameObject gameObject, EventVisitor visitor) {
        visitor.handle(gameObject,this);
    }
}
