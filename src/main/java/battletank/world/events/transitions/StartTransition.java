package battletank.world.events.transitions;

import battletank.world.events.Event;

public class StartTransition extends Event {

    int transitionSpeed;

    public int getTransitionSpeed() {
        return transitionSpeed;
    }

    public void setTransitionSpeed(int transitionSpeed) {
        this.transitionSpeed = transitionSpeed;
    }

    public StartTransition(int transitionSpeed) {
        this.transitionSpeed = transitionSpeed;
    }

    public StartTransition(int transitionSpeed, int delay){
        super(delay);
        this.transitionSpeed = transitionSpeed;
    }
}
