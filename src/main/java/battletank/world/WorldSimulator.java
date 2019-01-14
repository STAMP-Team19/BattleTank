package battletank.world;

import battletank.world.events.Event;
import battletank.world.events.go.CreateGameObject;
import battletank.world.events.go.DestroyGameObject;
import battletank.world.events.go.UpdateGameObject;
import battletank.world.events.rotations.StartRotation;
import battletank.world.events.rotations.StopRotation;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.GameObject;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorldSimulator  implements EventVisitor,Runnable{

    private Map<GameObject, Set<Event>> simulatedEvents;

    private Set<GameObject> gameObjects;

    private DeltaTime updateTime;


    public WorldSimulator(DeltaTime dt){
        updateTime=dt;
        simulatedEvents = new ConcurrentHashMap<>();
        gameObjects = ConcurrentHashMap.newKeySet();
    }

    @Override
    public void run() {
        while(true){
            handleTick();
        }
    }

    public void handleTick(){

        for(GameObject currentObject : simulatedEvents.keySet()){
            for(Event event : simulatedEvents.get(currentObject)) {
                event.accept(currentObject,this);
            }
        }
        updateTime.update();
    }


    @Override
    public void handle(GameObject gameObject, StartTransition transition){
        double timeMilliSeconds = updateTime.last()/1000;
        double aRadians = gameObject.getRotation()*Math.PI/180;

        double newX = gameObject.getPositionX()+ timeMilliSeconds *transition.getTransitionSpeed()*Math.cos(aRadians);
        double newY = gameObject.getPositionY()+ timeMilliSeconds *transition.getTransitionSpeed()*Math.sin(aRadians);

        //TODO: Check collision

        gameObject.setPositionX(newX);
        gameObject.setPositionY(newY);
    }

    @Override
    public void handle(GameObject gameObject, StopTransition transition){

    }

    @Override
    public void handle(GameObject gameObject, StartRotation rotation){
        //TODO: HandleRotation.
    }

    @Override
    public void handle(GameObject gameObject, StopRotation rotation){
    }

    @Override
    public void handle(GameObject gameObject, UpdateGameObject updateGameObject) {

    }

    @Override
    public void handle(GameObject gameObject, DestroyGameObject destroyGameObject) {

    }

    @Override
    public void handle(GameObject gameObject, CreateGameObject createGameObject) {

    }


    public void addGameObject(GameObject go) {
        gameObjects.add(go);
    }

    public void addEvent(GameObject go, Event event) {
        Set<Event> events = simulatedEvents.get(go);
        //TODO: Remove duplicate and incompatiple events. Possibly make it a map instead.
        events.add(event);
    }

}
