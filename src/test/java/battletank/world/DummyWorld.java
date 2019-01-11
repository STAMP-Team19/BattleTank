package battletank.world;

import battletank.world.events.Event;
import battletank.world.events.go.CreateGameObject;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.DummyGameObject;
import spaces.game.hosting.GameHost;

public class DummyWorld {

    public void load(GameHost gameHost) throws InterruptedException {
        WorldController controller = gameHost.getGame().getWorldController();

        System.out.println("Generating the DummyWorld.");

        Thread.sleep(1000);

        System.out.println("Creation of Mads.");

        controller.update(
                new DummyGameObject("Mads", 0, 0, 10, 10, -3),
                new CreateGameObject(0));

        System.out.println("Mads has been created.");

        Thread.sleep(1000);

        System.out.println("Moving Mads.");

        controller.update(
                new DummyGameObject("Mads", 0, 0, 10, 10, -3),
                new StartTransition(4));

        Thread.sleep(3000);

        System.out.println("Stopping.");

        controller.update(
                new DummyGameObject("Mads", 0, 0, 10, 10, -3),
                new StopTransition());

    }
}
