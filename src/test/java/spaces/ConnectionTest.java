package spaces;

import battletank.controls.Action;
import battletank.world.Game;
import battletank.world.gameobjects.Player;
import spaces.game.connect.ActionSender;
import spaces.game.connect.WorldEventsListener;
import spaces.game.hosting.GameHost;

import java.util.HashMap;

public class ConnectionTest {

    public static void main(String[] args) {
        HashMap<String, Player> players = new HashMap<>();
        players.put("name", new Player("name", 1, 1, 0, 0, 1, 10, 10));
        GameHost host = new GameHost(new Game(players) {
        });
        new WorldEventsListener("name");
        ActionSender sender = new ActionSender();
        for (int i = 0; i < 100; i++) {
            System.out.println("Sending action " + (i + 1));
            sender.notifyAction(Action.MOVE_FORWARD);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sender.notifyAction(Action.MOVE_STOP);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
