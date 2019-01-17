package battletank.world;

import battletank.world.events.go.CreateProjectile;
import battletank.world.events.go.DestroyGameObject;
import battletank.world.events.go.UpdateGameObject;
import battletank.world.events.rotations.StartRotation;
import battletank.world.events.rotations.StopRotation;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.GameObject;

public interface EventVisitor {


    void handle(GameObject gameObject, StartTransition transition);
    void handle(GameObject gameObject, StopTransition transition);
    void handle(GameObject gameObject, StartRotation rotation);
    void handle(GameObject gameObject, StopRotation rotation);

    void handle(GameObject gameObject, UpdateGameObject updateGameObject);

    void handle(GameObject gameObject, DestroyGameObject destroyGameObject);

    void handle(GameObject gameObject, CreateProjectile createProjectile);
}
