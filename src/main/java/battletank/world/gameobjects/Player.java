package battletank.world.gameobjects;


import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {


    public Player(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints) {
        super(name, positionX, positionY, width, height, rotation, speed, rotationSpeed, healthpoints);
    }

    public Rectangle getBody() {
        System.out.println(positionX);
        return new Rectangle(positionX, positionY, width, height);
    }
}
