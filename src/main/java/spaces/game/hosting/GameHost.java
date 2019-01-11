package spaces.game.hosting;

import battletank.IGame;

import battletank.controllers.ActionControllerFactory;
import battletank.world.WorldController;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class GameHost {

    private final String uri = "tcp://0.0.0.0:9001/?keep";
    private final SpaceRepository spaceRepository;

    private ActionRetriever actionRetriever;

    IGame game;

    public GameHost(IGame Game){
        this.game = Game;

        spaceRepository = new SpaceRepository();
        setupActionSpace();
        setupWorldSpace();

        openGates();
        initializeThreads();
    }

    public IGame getGame() {
        return game;
    }

    private void setupActionSpace(){
        SequentialSpace incomingCommands = new SequentialSpace();
        actionRetriever = new ActionRetriever(incomingCommands, new ActionControllerFactory());
        spaceRepository.add("actions", incomingCommands);
    }

    private void setupWorldSpace() {
        //TODO: Add world space setup here.

        SequentialSpace incomingWorldEvents = new SequentialSpace();
        //
        spaceRepository.add("world", incomingWorldEvents);

        game.setWorldController(new WorldController(incomingWorldEvents));
    }

    private void openGates(){
        spaceRepository.addGate(uri);
    }

    private void initializeThreads() {
        new Thread(actionRetriever).start();
    }
}
