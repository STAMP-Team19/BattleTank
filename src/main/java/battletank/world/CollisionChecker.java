package battletank.world;

import battletank.world.gameobjects.GameObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


public class CollisionChecker {

    public Collision checkCollision(GameObject collider, RectangleMapObject subject){
        Rectangle subjectBody = subject.getRectangle();
        Rectangle colliderBody = collider.getBody();


        return checkCollision(colliderBody, subjectBody);
    }
    public Collision checkCollision(GameObject collider, GameObject subject){
        Rectangle subjectBody = subject.getBody();
        Rectangle colliderBody = collider.getBody();

        return checkCollision(colliderBody,subjectBody);
    }

    private Collision checkCollision(Rectangle collider, Rectangle subject){
        if (Intersector.overlaps(collider, subject)) {
            boolean collisionRight = collider.x<subject.x+subject.width;
            boolean collisionLeft  = collider.x+collider.width> subject.x;

            System.out.println("VALUE"+collider.x);

            return new Collision();
        }
        return null;
    }
}
