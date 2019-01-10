package spaces;

import battletank.controls.Action;
import battletank.world.Game;
import spaces.game.connect.ActionSender;
import spaces.game.hosting.GameHost;

public class ConnectionTest {

    public static void main(String[] args){
        GameHost host = new GameHost(new Game() {
        });
        ActionSender sender = new ActionSender();
        for(int i = 0; i<100; i++) {
            System.out.println("Sending action "+(i+1));
            sender.notifyAction(Action.Move_Forward);
        }
    }
}
