package spaces.game.connect.actions;

import battletank.Game;
import spaces.game.hosting.GameHost;

public class ConnectionTest {

    public static void main(String[] args){
        GameHost host = new GameHost(new Game() {
        });
        ActionSender sender = new ActionSender();
        for(int i = 0; i<100; i++) {
            sender.sendCommand("hello"+i);
        }
    }
}
