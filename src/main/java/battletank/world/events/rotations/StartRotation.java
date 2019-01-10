package battletank.world.events.rotations;

import battletank.world.events.Event;

public class StartRotation extends Event {

    int rotationSpeed;

    public int getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public StartRotation(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public StartRotation(int rotationSpeed, int delay){
        super(delay);
        this.rotationSpeed = rotationSpeed;
    }
}
