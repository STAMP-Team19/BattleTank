package battletank.world.gameobjects;

public class Projectile extends GameObject {



    public Projectile(){}

    public Projectile(int number,int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints,int damage, PlayerColor color) {
        super("Projectile"+number, positionX, positionY, width, height, rotation, speed, rotationSpeed, healthpoints, color);
    }

}
