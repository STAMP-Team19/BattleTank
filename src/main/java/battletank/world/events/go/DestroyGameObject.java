package battletank.world.events.go;

import battletank.world.EventVisitor;
import battletank.world.events.Event;
import battletank.world.gameobjects.GameObject;

public class DestroyGameObject extends Event {

    private int damage;
    private boolean damageApplied = false;

    public DestroyGameObject(int delay, int damage){
        super(delay);
        this.damage = damage;
    }


    @Override
    public void accept(GameObject gameObject, EventVisitor visitor) {
        visitor.handle(gameObject,this);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDamageApplied(boolean b) {
    }

    public boolean damageApplied() {
        return damageApplied;
    }
}
