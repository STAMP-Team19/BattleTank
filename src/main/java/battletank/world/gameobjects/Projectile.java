package battletank.world.gameobjects;

public class Projectile extends GameObject {


    public Projectile(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints, PlayerColor color) {
        super(name, positionX, positionY, width, height, rotation, speed, rotationSpeed, healthpoints, color);
    }
}
