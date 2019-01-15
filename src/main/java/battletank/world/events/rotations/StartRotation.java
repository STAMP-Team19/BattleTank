package battletank.world.events.rotations;

import battletank.world.EventVisitor;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;

public class StartRotation extends Event {

    double rotationSpeed;

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public StartRotation(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public StartRotation(int rotationSpeed, int delay){
        super(delay);
        this.rotationSpeed = rotationSpeed;
    }


    @Override
    public void accept(GameObject gameObject, EventVisitor visitor) {
        visitor.handle(gameObject,this);
    }
}
