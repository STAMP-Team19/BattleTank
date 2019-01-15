package battletank.world;

import battletank.world.events.go.CreateGameObject;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.DummyGameObject;
import spaces.game.hosting.GameHost;
import spaces.game.hosting.WorldGateway;

public class DummyWorld {

    public void load(GameHost gameHost) throws InterruptedException {
        WorldGateway controller = gameHost.getGame().getWorldGateway();

        System.out.println("Generating the DummyWorld.");

        Thread.sleep(1000);

        System.out.println("Creation of Mads.");

        controller.update(
                new DummyGameObject("Mads", 0, 0, 10, 10, -3,10,5,1),
                new CreateGameObject(0));

        System.out.println("Mads has been created.");

        Thread.sleep(1000);

        System.out.println("Moving Mads.");

        controller.update(
                new DummyGameObject("Mads", 0, 0, 10, 10, -3,10,5,1),
                new StartTransition(4));

        Thread.sleep(3000);

        System.out.println("Stopping.");

        controller.update(
                new DummyGameObject("Mads", 0, 0, 10, 10, -3,10,5,1),
                new StopTransition());

    }
}
