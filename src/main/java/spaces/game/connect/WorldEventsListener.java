package spaces.game.connect;

import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;

public class WorldEventsListener {

    private RemoteSpace world;

    public WorldEventsListener(){
        String uri = "tcp://127.0.0.1:9001/world?keep";
        try {
            world = new RemoteSpace(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WorldObserver implements Runnable{

    RemoteSpace world;

    String username;

    public WorldObserver(RemoteSpace world, String username){
        this.world = world;
        this.username = username;
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

                System.out.println(target.getName()+" "+event.getClass());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
