package battletank.world.gameobjects;

public class Bullet extends GameObject {

    private int damage = 10;

    public Bullet(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints, PlayerColor color, int damage) {
        super(name, positionX, positionY, width, height, rotation, speed, rotationSpeed, healthpoints, color);
        this.damage = damage;
    }



}
