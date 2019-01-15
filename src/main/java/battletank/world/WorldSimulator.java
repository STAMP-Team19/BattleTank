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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WorldSimulator  implements EventVisitor,Runnable{

    private Map<GameObject, Map<String, Event>> simulatedEvents;

    private DeltaTime updateTime;

    public WorldSimulator(DeltaTime dt){
        updateTime=dt;
        simulatedEvents = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        while(true){
            handleTick();
        }
    }

    public void handleTick(){

        for(GameObject currentObject : simulatedEvents.keySet()){
            for(Event event : simulatedEvents.get(currentObject).values()) {
                event.accept(currentObject,this);
            }
        }
        updateTime.update();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void handle(GameObject gameObject, StartTransition transition){
        double timeSeconds = updateTime.last()/1000;
        double aRadians = gameObject.getRotation()*Math.PI/180;

        double newX = gameObject.getPositionX()+ timeSeconds *transition.getTransitionSpeed()*Math.cos(aRadians);
        double newY = gameObject.getPositionY()+ timeSeconds *transition.getTransitionSpeed()*Math.sin(aRadians);
        //TODO: Check collision

        gameObject.setPositionX(newX);
        gameObject.setPositionY(newY);

    }

    @Override
    public void handle(GameObject gameObject, StopTransition transition){
        simulatedEvents.get(gameObject).remove(StartTransition.class.getSimpleName());
        simulatedEvents.get(gameObject).remove(transition.getClass().getSimpleName());
    }

    @Override
    public void handle(GameObject gameObject, StartRotation rotation){
        double timeSeconds = updateTime.last()/1000;
        gameObject.setRotation(gameObject.getRotation()+rotation.getRotationSpeed()*timeSeconds);
    }

    @Override
    public void handle(GameObject gameObject, StopRotation rotation){
        simulatedEvents.get(gameObject).remove(StartRotation.class.getSimpleName());
        simulatedEvents.get(gameObject).remove(rotation.getClass().getSimpleName());
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
        simulatedEvents.put(go,new ConcurrentHashMap<>());
    }

    public void addEvent(GameObject go, Event event) {
        Map<String,Event> events = simulatedEvents.get(go);
        if(events==null){
            events=new ConcurrentHashMap<>();

        }
        events.put(event.getClass().getSimpleName(),event);
        simulatedEvents.put(go,events);

    }

    public List<GameObject> getGameObjects(){
        return new ArrayList<>(simulatedEvents.keySet());
    }

    public void setGameObject(GameObject target) {
        Map<String, Event> events =simulatedEvents.remove(target);
        if(events==null){
            events=new ConcurrentHashMap<>();

        }
        simulatedEvents.put(target,events);
    }
}
