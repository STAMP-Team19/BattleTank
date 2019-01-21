package battletank.world.events.go;

import battletank.world.EventVisitor;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;
import battletank.world.gameobjects.Projectile;

import java.util.List;

public class CreateProjectile extends Event {

    private static int count=0;
    String tankName;

    public CreateProjectile(String tankName, int delay){
        super(delay);
        this.tankName=tankName;
    }


    @Override
    public void accept(GameObject gameObject, EventVisitor visitor) {
        visitor.handle(gameObject,this);
    }

    public String getTankName(){
        return tankName;
    }
}
