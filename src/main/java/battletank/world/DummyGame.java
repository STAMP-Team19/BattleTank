package battletank.world;

import battletank.Game;
import org.jspace.SequentialSpace;

public class DummyGame implements Game {
    WorldController worldController;

    public void setWorldController(WorldController worldController){
        this.worldController = worldController;
    }


}
