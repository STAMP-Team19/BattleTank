package battletank.world.gameobjects;

public class Player extends GameObject {

    private int playerSpeed = 10;

    public Player(String name, int positionX, int positionY, int width, int height, int rotation) {
        super(name, positionX, positionY, width, height, rotation);
    }

    public int getSpeed() {
        return playerSpeed;
    }
}
