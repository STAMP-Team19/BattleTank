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
        setupActionSpace();
        setupWorldSpace();

        openGates();
        initializeHosting();
    }


    private void setupActionSpace(){
        SequentialSpace incomingCommands = new SequentialSpace();
        actionRetriever = new ActionRetriever(incomingCommands);
        spaceRepository.add("actions", incomingCommands);
    }

    private void setupWorldSpace() {
        //TODO: Add world space setup here.
    }

    private void openGates(){
        spaceRepository.addGate(uri);
    }

    private void initializeHosting() {
        new Thread(actionRetriever).start();
    }
}
