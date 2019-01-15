package battletank.world.events.transitions;

import battletank.world.EventVisitor;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;

public class StartTransition extends Event {

    double transitionSpeed;

    public double getTransitionSpeed() {
        return transitionSpeed;
    }

    public void setTransitionSpeed(double transitionSpeed) {
        this.transitionSpeed = transitionSpeed;
    }

    public StartTransition(double transitionSpeed) {
        this.transitionSpeed = transitionSpeed;
    }


    public StartTransition(int transitionSpeed, int delay){
        super(delay);
        this.transitionSpeed = transitionSpeed;
    }

    @Override
    public void accept(GameObject gameObject, EventVisitor visitor) {
        visitor.handle(gameObject,this);
    }


}
