package spaces;

import battletank.controls.Action;
import battletank.world.DeltaTime;
import battletank.world.Game;
import battletank.world.WorldSimulator;
import battletank.world.gameobjects.Player;
import battletank.world.gameobjects.PlayerColors;
import spaces.game.connect.ActionSender;
import spaces.game.connect.WorldEventsListener;
import spaces.game.hosting.GameHost;

import java.util.HashMap;

public class ConnectionTest {

    public static void main(String[] args) {
        HashMap<String, Player> players = new HashMap<>();
        players.put("name", new Player("name", 1, 1, 0, 0, 1, 10, 10,1, PlayerColors.PLAYERCOLOR.Blue));
        GameHost host = new GameHost(new Game(players) {
        });
        new WorldEventsListener("name",new WorldSimulator(new DeltaTime()));
        ActionSender sender = new ActionSender("name");
        for (int i = 0; i < 100; i++) {
            System.out.println("Sending action " + (i + 1));
            sender.notifyAction(Action.MOVE_FORWARD);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sender.notifyAction(Action.MOVE_STOP);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sender.notifyAction(Action.MOVE_BACKWARD);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sender.notifyAction(Action.MOVE_STOP);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
