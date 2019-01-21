package battletank.world.gameobjects;

public class Projectile extends GameObject {


    private boolean damageApplied=false;
    private int damage;

    private int bounceCounter;

    public Projectile(){}

    public Projectile(int number,int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints,int damage, int bounceCounter, PlayerColor color) {
        super("Projectile"+number, positionX, positionY, width, height, rotation, speed, rotationSpeed, healthpoints, color);
        this.damage = damage;
        this.bounceCounter=bounceCounter;
    }

    public int getDamage() {
        return damage;
    }

    public boolean damageApplied() {
        return damageApplied;
    }

    public void setDamageApplied(boolean damageApplied) {
        this.damageApplied = damageApplied;
    }

    public int getBounceCounter() {
        return bounceCounter;
    }

    public void setBounceCounter(int bounceCounter) {
        this.bounceCounter = bounceCounter;
    }
}
