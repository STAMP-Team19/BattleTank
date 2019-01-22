package battletank.world;

public class GameRules {

    private int numberOfLifes;
    private boolean allowBulletBounce=true;

    public void setBounce(boolean b) {
        allowBulletBounce=b;
    }

    public boolean bulletBounceAllowed(){
        return allowBulletBounce;
    }
}
