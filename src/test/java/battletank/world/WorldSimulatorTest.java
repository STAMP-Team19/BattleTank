package battletank.world;

import battletank.world.events.transitions.StartTransition;
import battletank.world.gameobjects.Player;
import battletank.world.gameobjects.PlayerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorldSimulatorTest {

    @Test
    public void testThatPositionIsUpdatedCorrectly(){

        Player p = new Player("ho",0,0,10,10,45,10,1,1, PlayerColor.Blue);
        DeltaTime dt = new DeltaTime();
        WorldSimulator es = new WorldSimulator(dt,0);
        dt.setTime(100);

        es.handle(p,new StartTransition(10));

        assertTrue(p.getPositionX()>0.7&&p.getPositionX()<0.71);
        assertTrue(p.getPositionY()>0.7&&p.getPositionY()<0.71);


    }
}
