package battletank.world.gameobjects;

public abstract class GameObject {

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
    PlayerColors.PLAYERCOLOR color;

    boolean hidden;

    public GameObject(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints, PlayerColors.PLAYERCOLOR color) {
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
