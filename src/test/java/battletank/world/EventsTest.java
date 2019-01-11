package battletank.world;

import battletank.world.gameobjects.Player;
import spaces.game.connect.WorldEventsListener;
import spaces.game.hosting.GameHost;

import java.util.HashMap;

public class EventsTest {

    public static void main(String[] args){
        HashMap<String, Player> players = new HashMap<>();
        players.put("name",new Player("name",0,0,0,0,0));
        GameHost gameHost = new GameHost(new Game(players));

        System.out.println("Init connection and starting to listen...");
        WorldEventsListener eventsListener = new WorldEventsListener("name");
        System.out.println("Connected to server. Listening...");

        try {
            DummyWorld world = new DummyWorld();
            world.load(gameHost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
