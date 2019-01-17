package battletank.world.gameobjects;


import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {

    public Player(){}

    public Player(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints, PlayerColor color) {
        super(name, positionX, positionY, width, height, rotation, speed, rotationSpeed, healthpoints, color);
    }


}
