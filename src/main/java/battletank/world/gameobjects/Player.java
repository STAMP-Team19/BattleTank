package battletank.world.gameobjects;


import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {


    public Player(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints) {
        super(name, positionX, positionY, width, height, rotation, speed, rotationSpeed, healthpoints);
    }

    public Rectangle getBody() {
        //System.out.println("x: " + positionX + " + y: " + positionY);
        return new Rectangle((float)positionX, (float)positionY, (float)width,(float) height);
    }
    public double getOriginX(){
        return super.getWidth()/2;
    }

    public double getOriginY(){
        return (super.getHeight()*(1-(0.36/2.11)))/2;
    }


}
