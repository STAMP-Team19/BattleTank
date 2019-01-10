package spaces.game.hosting;

import battletank.Game;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;
import spaces.game.hosting.actions.ActionRetriever;

public class GameHost {

    private final String uri = "tcp://127.0.0.1:9001/?keep";
    private final SpaceRepository spaceRepository;

    private ActionRetriever actionRetriever;

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
        actionRetriever = new ActionRetriever(incomingCommands);
        spaceRepository.add("actions", incomingCommands);
    }

    private void setupWorldBoundary() {
        //TODO: Add world space setup here.
    }

    private void openGates(){
        spaceRepository.addGate(uri);
    }

    private void initializeHosting() {
        new Thread(actionRetriever).start();
    }
}
