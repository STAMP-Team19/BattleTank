package spaces.game.connect.actions;

import org.jspace.RemoteSpace;

import java.io.IOException;

public class ActionSender {

    private RemoteSpace commands;

    public ActionSender() {
        String uri = "tcp://127.0.0.1:9001/actions?keep";
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
