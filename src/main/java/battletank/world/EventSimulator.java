package battletank.world;


import battletank.world.events.go.CreateGameObject;
import battletank.world.events.go.DestroyGameObject;
import battletank.world.events.go.UpdateGameObject;
import battletank.world.events.rotations.StartRotation;
import battletank.world.events.rotations.StopRotation;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.GameObject;

public class EventSimulator implements EventVisitor{

    int time = 10;

    @Override
    public void handle(GameObject gameObject, StartTransition transition){
        double aRadians = gameObject.getRotation()*Math.PI/180;

        double newX = gameObject.getPositionX()+time*gameObject.getSpeed()*Math.cos(aRadians);
    }

    @Override
    public void handle(GameObject gameObject, StopTransition transition){

    }

    @Override
    public void handle(GameObject gameObject, StartRotation rotation){

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


}
