package battletank.world;

import spaces.game.connect.WorldEventsListener;
import spaces.game.hosting.GameHost;

public class EventsTest {

    public static void main(String[] args){

        GameHost gameHost = new GameHost(new Game());

        System.out.println("Init connection and starting to listen...");
        WorldEventsListener eventsListener = new WorldEventsListener("Arvid");
        System.out.println("Connected to server. Listening...");

        try {
            DummyWorld world = new DummyWorld();
            world.load(gameHost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
