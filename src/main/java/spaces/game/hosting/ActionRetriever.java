package spaces.game.hosting;

import battletank.controllers.ActionControllerFactory;
import battletank.controls.ActionInfo;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
public class ActionRetriever implements Runnable{

    private SequentialSpace incomingCommands;
    private ActionControllerFactory factory;
    private boolean runInLoop;

    public ActionRetriever(SequentialSpace incomingCommands, ActionControllerFactory factory){
        this(incomingCommands,factory,true);
    }

    public ActionRetriever(SequentialSpace incomingCommands,ActionControllerFactory factory, boolean runInLoop){
        this.incomingCommands=incomingCommands;
        this.factory = factory;
        this.runInLoop = runInLoop;
    }

    @Override
    public void run() {
        try {
            while (runInLoop){
                Object[] objects = incomingCommands.get(new FormalField(ActionInfo.class));
                ActionInfo action = (ActionInfo)objects[0];
                factory.constructAction(action);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
