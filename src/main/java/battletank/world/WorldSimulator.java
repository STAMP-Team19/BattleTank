package battletank.world;

import battletank.world.events.Event;
import battletank.world.events.go.CreateProjectile;
import battletank.world.events.go.DestroyGameObject;
import battletank.world.events.go.UpdateGameObject;
import battletank.world.events.rotations.StartRotation;
import battletank.world.events.rotations.StopRotation;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.GameObject;
import battletank.world.gameobjects.Player;
import battletank.world.gameobjects.PlayerColor;
import battletank.world.gameobjects.Projectile;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import spaces.game.hosting.WorldGateway;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldSimulator  implements EventVisitor,Runnable{

    private Map<GameObject, Map<String, Event>> simulatedEvents;

    private Map<GameObject,Long> lastShot;

    private DeltaTime updateTime;
    private MapObjects objects;
    private WorldGateway gateway;
    private int projectileNum = 0;

    public WorldSimulator(DeltaTime dt, int level){
        updateTime=dt;
        simulatedEvents = new ConcurrentHashMap<>();
        lastShot=new ConcurrentHashMap<>();
        MapLoader maploader=new MapLoader();
        maploader.loadMapNoUI(level);
        objects=maploader.getObjects();
    }

    @Override
    public void run() {
        while(true){
            handleTick();
        }
    }

    public void handleTick(){
        for(GameObject currentObject : simulatedEvents.keySet()){

            Map<String,Event> map =simulatedEvents.get(currentObject);
            if(map!=null) {
                for (Event event : map.values()) {
                    event.accept(currentObject, this);
                }
            }
        }
        updateTime.update();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public synchronized void handle(GameObject gameObject, StartTransition transition){
        double oldX = gameObject.getPositionX();
        double oldY = gameObject.getPositionY();
        double timeSeconds = updateTime.last()/1000;
        double aRadians = gameObject.getRotation()*Math.PI/180;

        double newX = gameObject.getPositionX()+ timeSeconds *transition.getTransitionSpeed()*Math.cos(aRadians);
        double newY = gameObject.getPositionY()+ timeSeconds *transition.getTransitionSpeed()*Math.sin(aRadians);

        gameObject.setPositionX(newX);
        gameObject.setPositionY(newY);

        CollisionChecker collisionChecker= new CollisionChecker();

        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Collision collision=collisionChecker.checkCollision(gameObject,rectangleObject);
            if (collision!=null) {
                // collision happened
                if(collision.collisionHorisontal)
                {
                    gameObject.setPositionX(oldX);

                }
                if (collision.collisionVertical)
                {
                    gameObject.setPositionY(oldY);
                }


                if(gameObject instanceof Projectile){
                    Event colliderDestroyer= new DestroyGameObject(0);
                    colliderDestroyer.accept(gameObject,this);
                }
            }
        }
        for (GameObject subject : simulatedEvents.keySet()) {

            if(subject.equals(gameObject)){
                continue;
            }
            if (collisionChecker.checkCollision(gameObject,subject)!=null) {
                // collision happened
                gameObject.setPositionX(oldX);
                gameObject.setPositionY(oldY);

                if(gameObject instanceof Projectile){
                    Event subjectDestroyer = new DestroyGameObject(0);
                    Event colliderDestroyer= new DestroyGameObject(0);
                    subjectDestroyer.accept(subject,this);
                    colliderDestroyer.accept(gameObject,this);
                }
            }
        }

        /*
        double initialRotation = projectile.getRotation();
        int totRotation = 180;

        //Calculate sigma, since (sigma - projectileRotation = 90 degrees)
        double sigma = (totRotation / 2) - initialRotation;

        //Using the formula: initialRotation + 2*sigma + resultRotation = 180 degrees, we get:
        double resRotation = initialRotation + (2 * sigma);

        projectile.setRotation(resRotation);*/


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
        simulatedEvents.remove(gameObject);
    }

    @Override
    public synchronized void handle(GameObject gameObject, CreateProjectile createProjectile) {
        Player player = (Player)gameObject;

        Long last = lastShot.get(player);
        if (last != null) {
            if (last + 500L > System.currentTimeMillis()) {
                simulatedEvents.get(gameObject).remove(createProjectile.getClass().getSimpleName());
                return;
            }
        }

        double startingDistanceFromOri = player.getHeight();
        double aRadians = player.getRotation() * Math.PI / 180;
        double projectileX = player.getPositionX() + player.getOriginX() + startingDistanceFromOri * Math.cos(aRadians);
        double projectileY = player.getPositionY() + player.getOriginY() + startingDistanceFromOri * Math.sin(aRadians);

        Projectile projectile = new Projectile(projectileNum++,(int) projectileX, (int) projectileY, 4, 4, (int) player.getRotation(), 150, 0, 10, 100, PlayerColor.purple);
        Event event = new StartTransition(projectile.getSpeed());

        if (gateway != null) {
            gateway.update(projectile, event);
        }
        try {
            event.accept(projectile, this);
            simulatedEvents.get(gameObject).remove(createProjectile.getClass().getSimpleName());
        }catch(NullPointerException e){

        }
        lastShot.put(player, System.currentTimeMillis());
    }


    public void addGameObject(GameObject go) {
        simulatedEvents.put(go,new ConcurrentHashMap<>());
        if(gateway!=null) {
            gateway.update(go, new StopTransition());
        }
    }

    public void addEvent(GameObject go, Event event) {
        Map<String,Event> events = simulatedEvents.get(go);
        if(events==null){
            events=new ConcurrentHashMap<>();

        }
        events.put(event.getClass().getSimpleName(),event);
        simulatedEvents.put(go,events);

        if(gateway!=null) {
            gateway.update(go, event);
        }
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

    public void setGateway(WorldGateway gateway){
        this.gateway=gateway;
        for(GameObject go: simulatedEvents.keySet()){
            gateway.update(go,new StopTransition());
        }
    }
}
