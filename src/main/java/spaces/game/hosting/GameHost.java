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

        //Create Command Space
        SequentialSpace incomingCommands = new SequentialSpace();
        commandRetriever = new CommandRetriever(incomingCommands);

        //Create World Space
        //TODO: Add world space here.

        //Add spaces to repo.
        spaceRepository.add("commands", incomingCommands);
        spaceRepository.addGate("tcp://127.0.0.1:9001/?keep");

        //Start Threads
        new Thread(commandRetriever).start();

    }
}
