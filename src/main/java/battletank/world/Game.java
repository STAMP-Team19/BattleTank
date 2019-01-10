package battletank.world;

import battletank.IGame;

public class DummyIGame implements IGame {
    private WorldController worldController;

    public void setWorldController(WorldController worldController){
        this.worldController = worldController;
    }


}
