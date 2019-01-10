package battletank.world.events;

public abstract class Event {

    int delay = 0;
    int duration = -1;

    public Event(){}

    public Event(int delay, int duration) {
        this.delay = delay;
        this.duration = duration;
    }

    public Event(int delay) {
        this.delay = delay;
    }
}
