package spaces.game.connect.commands;

import battletank.Game;
import spaces.game.hosting.GameHost;

public class ConnectionTest {

    public static void main(String[] args){
        GameHost host = new GameHost(new Game() {
        });
        CommandSender sender = new CommandSender();
        for(int i = 0; i<100; i++) {
            sender.sendCommand("hello"+i);
        }
    }
}
