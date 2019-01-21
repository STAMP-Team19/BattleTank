package battletank.world;

public class Collision {
    boolean collisionHorisontal;
    boolean collisionVertical;

    enum CollisionDirection{up,down,left,right}
    CollisionDirection collisionDirection;
    Collision(boolean collisionHorisontal,boolean collisionVertical,CollisionDirection collisionDirection)
    {

        this.collisionHorisontal=collisionHorisontal;
        this.collisionVertical=collisionVertical;
        this.collisionDirection=collisionDirection;
    }
}
