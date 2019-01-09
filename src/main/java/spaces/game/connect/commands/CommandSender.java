package spaces.game.connect.commands;

import org.jspace.RemoteSpace;

import java.io.IOException;

public class CommandSender {

    private RemoteSpace commands;

    public CommandSender() {
        String uri = "tcp://127.0.0.1:9001/commands?keep";
        try {
            commands = new RemoteSpace(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendCommand(String cmd){
        try {
            commands.put(cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
