package spaces.game.hosting;

import battletank.Game;
import battletank.world.DummyGame;
import battletank.world.WorldController;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class GameHost {

    private final String uri = "tcp://127.0.0.1:9001/?keep";
    private final SpaceRepository spaceRepository;

    private ActionRetriever actionRetriever;

    Game game;

    public GameHost(Game game){
        spaceRepository = new SpaceRepository();
        setupActionSpace();

        game.setWorldController(new WorldController(setupWorldSpace()));

        this.game = game;

        openGates();
        initializeThreads();
    }


    private void setupActionSpace(){
        SequentialSpace incomingCommands = new SequentialSpace();
        actionRetriever = new ActionRetriever(incomingCommands);
        spaceRepository.add("actions", incomingCommands);
    }

    private SequentialSpace setupWorldSpace() {
        //TODO: Add world space setup here.

        SequentialSpace incomingWorldEvents = new SequentialSpace();
        //
        spaceRepository.add("world", incomingWorldEvents);

        return incomingWorldEvents;
    }

    private void openGates(){
        spaceRepository.addGate(uri);
    }

    private void initializeThreads() {
        new Thread(actionRetriever).start();
    }
}
