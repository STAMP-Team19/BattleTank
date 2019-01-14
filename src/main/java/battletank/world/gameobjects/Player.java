package battletank.world.gameobjects;


import battletank.scene_management.screen.MyGame;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {


    public Player(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed) {
        super(name, positionX, positionY, width, height, rotation, speed, rotationSpeed);
    }

    public Rectangle getBody() {
        System.out.println(positionX);
        return new Rectangle(positionX, positionY, width, height);
    }
}
