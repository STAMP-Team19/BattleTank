package battletank.world;

import battletank.Game;
import org.jspace.SequentialSpace;

public class DummyGame implements Game {
    private WorldController worldController;

    public void setWorldController(WorldController worldController){
        this.worldController = worldController;
    }


}
