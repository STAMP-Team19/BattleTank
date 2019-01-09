package commands;

import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class CommandRetriever implements Runnable{

    private SpaceRepository spaceRepository;
    private SequentialSpace incomingCommands;
    boolean runInLoop = true;

    public CommandRetriever(){
        spaceRepository = new SpaceRepository();
        incomingCommands = new SequentialSpace();
        spaceRepository.add("commands", incomingCommands);
        spaceRepository.addGate("tcp://127.0.0.1:9001/?keep");
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
