package spaces.game.connect;

import battletank.world.WorldSimulator;
import battletank.world.events.Event;
import battletank.world.events.go.DestroyGameObject;
import battletank.world.gameobjects.GameObject;
import battletank.world.gameobjects.Player;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;

public class WorldEventsListener {

    private RemoteSpace world;

    public WorldEventsListener(String username, WorldSimulator worldSimulator){
        String uri = "tcp://127.0.0.1:9001/world?keep";
        try {
            world = new RemoteSpace(uri);

            new Thread(new WorldObserver(world, username,worldSimulator)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public WorldEventsListener(String username, WorldSimulator worldSimulator, String ip){
        String uri = "tcp://" + ip + ":9001/world?keep";
        try {
            world = new RemoteSpace(uri);

            new Thread(new WorldObserver(world, username,worldSimulator)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WorldObserver implements Runnable{

    RemoteSpace world;

    String username;
    private WorldSimulator worldSimulator;

    public WorldObserver(RemoteSpace world, String username, WorldSimulator worldSimulator){
        this.world = world;
        this.username = username;
        this.worldSimulator = worldSimulator;
    }

    @Override
    public void run() {
        while(true){
            try {
                Object[] event = world.get(new ActualField(username),
                        new FormalField(GameObject.class),
                        new FormalField(Event.class));

                GameObject target = (GameObject) event[1];
                Event targetEvent = (Event) event[2];


                worldSimulator.setGameObject(target);
                worldSimulator.addLocalEvent(target,targetEvent);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
