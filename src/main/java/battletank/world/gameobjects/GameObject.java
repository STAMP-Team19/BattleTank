package battletank.world.gameobjects;

import com.badlogic.gdx.math.Rectangle;

public class GameObject {

    String name;
    String[] tags;

    double positionX;
    double positionY;
    double width;
    double height;
    double speed;
    double rotation;
    double rotationSpeed;
    int healthpoints;
    PlayerColor color;

    boolean hidden;

    public GameObject(){}

    public GameObject(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints, PlayerColor color) {

        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.healthpoints = healthpoints;
        this.hidden = false;
        this.color = color;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTags() {
        return tags;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setSpeed(int speed) {
        this.speed = rotation;
    }

    public double getSpeed() {
        return speed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setHealthpoints(int healthpoints) {
        this.healthpoints = healthpoints;
    }

    public int getHealthpoints() {
        return healthpoints;
    }

    public Rectangle getBody() {
        //System.out.println("x: " + positionX + " + y: " + positionY);
        float collisionWidth = (float)Math.min(width,height);

        return new Rectangle((float)(positionX+getOriginX()-collisionWidth/2), (float)(positionY+getOriginY()-collisionWidth/2), collisionWidth,collisionWidth);
    }
    public double getOriginX(){
        return getWidth()/2;
    }

    public double getOriginY(){
        return (getHeight()*(1-(0.36/2.11)))/2;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other){
        if(other.getClass()!=this.getClass()){
            return false;
        }
        GameObject otherGO = (GameObject) other;
        return otherGO.getName().equals(this.getName());
    }

}
