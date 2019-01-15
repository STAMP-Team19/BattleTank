package battletank.world.events.go;

import battletank.world.EventVisitor;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;

public class UpdateGameObject extends Event {

    GameObject newGameObject;

    public UpdateGameObject(GameObject newGameObject){
        this.newGameObject = newGameObject;
    }


    @Override
    public void accept(GameObject gameObject, EventVisitor visitor) {
        visitor.handle(gameObject,this);
    }
}
