package battletank.world;

import battletank.world.gameobjects.GameObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.HashMap;
import java.util.Map;

import static battletank.world.Collision.CollisionDirection.*;



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
            HashMap<Float,Collision.CollisionDirection> collisionMap = new HashMap();

            float temp=(subject.x+subject.width)-collider.x;
            collisionMap.put(temp,left);
            collisionMap.put(subject.x-(collider.x+collider.width),right);
            collisionMap.put((subject.y+subject.height)-collider.y,down);
            collisionMap.put(subject.y-(collider.y+collider.height),up);

            float absNum = Math.abs(temp);
            for(Map.Entry<Float, Collision.CollisionDirection> entry : collisionMap.entrySet()) {
                float newAbs = Math.abs(entry.getKey());
                if(newAbs < absNum) {
                    absNum = newAbs;
                    temp = entry.getKey();
                }
            }

            return new Collision(
                    collisionMap.get(temp)==up||collisionMap.get(temp)==down,
                    collisionMap.get(temp)==left||collisionMap.get(temp)==right
                    ,collisionMap.get(temp));

        }
        return null;
    }
}
