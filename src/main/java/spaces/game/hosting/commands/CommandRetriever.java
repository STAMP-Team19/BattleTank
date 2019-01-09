package spaces.game.hosting.commands;

import org.jspace.FormalField;
import org.jspace.SequentialSpace;
public class CommandRetriever implements Runnable{

    private SequentialSpace incomingCommands;
    private boolean runInLoop;

    public CommandRetriever(SequentialSpace incomingCommands){
        this(incomingCommands,true);
    }

    public CommandRetriever(SequentialSpace incomingCommands, boolean runInLoop){
        this.incomingCommands=incomingCommands;
        this.runInLoop = runInLoop;
    }

    @Override
    public void run() {
        try {
            while (runInLoop){
                Object[] objects = incomingCommands.get(new FormalField(String.class));
                System.out.println(objects[0]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
