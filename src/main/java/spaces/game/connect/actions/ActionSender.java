package spaces.game.connect.actions;

import controls.ActionInfo;
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

    public void sendAction(ActionInfo action){
        try {
            commands.put(action);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
