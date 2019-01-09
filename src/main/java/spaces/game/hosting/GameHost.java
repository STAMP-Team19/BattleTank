package spaces.game.hosting;

import battletank.Game;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;
import spaces.game.hosting.commands.CommandRetriever;

public class GameHost {

    private final SpaceRepository spaceRepository;

    CommandRetriever commandRetriever;

    public GameHost(Game game){
        spaceRepository = new SpaceRepository();
        setupCommandBoundary();
        setupWorldBoundary();

        openGates();
        initializeHosting();
    }


    private void setupCommandBoundary(){
        //Create Command Space
        SequentialSpace incomingCommands = new SequentialSpace();
        commandRetriever = new CommandRetriever(incomingCommands);
        spaceRepository.add("commands", incomingCommands);
    }

    private void setupWorldBoundary() {
        //TODO: Add world space here.
    }

    private void openGates(){
        spaceRepository.addGate("tcp://127.0.0.1:9001/?keep");
    }

    private void initializeHosting() {
        new Thread(commandRetriever).start();
    }
}
