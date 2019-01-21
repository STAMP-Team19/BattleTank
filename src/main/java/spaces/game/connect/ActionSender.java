package spaces.game.connect;

import battletank.controls.Action;
import battletank.controls.ActionInfo;
import org.jspace.RemoteSpace;

import java.io.IOException;

public class ActionSender implements IActionSender {



    //TODO: Make this variable
    private String uri = "tcp://127.0.0.1:9001/actions?keep";


    private String playerName;
    private RemoteSpace commands;

    public ActionSender(String playerName) {
        this.playerName = playerName;
        try {
            commands = new RemoteSpace(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ActionSender(String playerName, String ip){
        uri ="tcp://" + ip + ":9001/actions?keep";

        this.playerName = playerName;
        try {
            commands = new RemoteSpace(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyAction(Action action) {
        try {

            commands.put(new ActionInfo(action, playerName));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
