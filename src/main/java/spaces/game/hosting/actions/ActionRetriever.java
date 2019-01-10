package spaces.game.hosting.actions;

import controls.ActionInfo;
import org.jspace.FormalField;
import org.jspace.SequentialSpace;
public class ActionRetriever implements Runnable{

    private SequentialSpace incomingCommands;
    private boolean runInLoop;

    public ActionRetriever(SequentialSpace incomingCommands){
        this(incomingCommands,true);
    }

    public ActionRetriever(SequentialSpace incomingCommands, boolean runInLoop){
        this.incomingCommands=incomingCommands;
        this.runInLoop = runInLoop;
    }

    @Override
    public void run() {
        try {
            while (runInLoop){
                Object[] objects = incomingCommands.get(new FormalField(ActionInfo.class));
                ActionInfo action = (ActionInfo)objects[0];
                System.out.println(action.getUserIdentifier()+": "+action.getAction());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
