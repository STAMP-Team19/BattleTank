package spaces.game.connect;

import battletank.controls.Action;
import battletank.controls.ActionInfo;
import org.jspace.RemoteSpace;

import java.io.IOException;

public class ActionSender implements IActionSender {

    private final String uri = "tcp://127.0.0.1:9001/actions?keep";
    private RemoteSpace commands;

    public ActionSender() {
        try {
            commands = new RemoteSpace(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void notifyAction(Action action) {
        try {
            //TODO: Make this variable:
            commands.put(new ActionInfo(action, "name"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
