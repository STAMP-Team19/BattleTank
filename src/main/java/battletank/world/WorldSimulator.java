package battletank.world;

import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorldSimulator {

    //TODO: Handle world time. Should be dependent on client and server update rate. Use delta time for rendering
    // , system time on server.

    //TODO: Update player position based on time, speed, rotation.

    private EventSimulator eventSimulator;

    private Map<GameObject, Set<Event>> simulatedEvents;

    private Set<GameObject> gameObjects;


    public WorldSimulator(){
        simulatedEvents = new ConcurrentHashMap<>();
        gameObjects = ConcurrentHashMap.newKeySet();
        eventSimulator = new EventSimulator();
    }


    public void addGameObject(GameObject gObject){
        gameObjects.add(gObject);
    }

    public void handleTick(){

        for(GameObject currentObject : simulatedEvents.keySet()){
            for(Event event : simulatedEvents.get(currentObject)) {
                event.accept(currentObject,eventSimulator);
            }
        }
    }



}
