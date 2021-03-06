package spaces.game.hosting;

import battletank.lobby.Lobby;
import battletank.world.GameRules;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class LobbyProvider {

    private final String uri = "tcp://0.0.0.0:9002/?keep";
    private SpaceRepository spaceRepository;
    private Lobby lobby;

    public LobbyProvider(){
        this.spaceRepository = new SpaceRepository();
    }

    public void createLobby(String hostname, int numberOfMaxPlayers, GameRules rules, int level){
        SequentialSpace lobbyspace = new SequentialSpace();
        spaceRepository.add("lobby", lobbyspace);

        lobby = new Lobby(hostname, numberOfMaxPlayers, rules, lobbyspace, spaceRepository, level);

        openGates();
    }

    public Lobby getLobby() {
        return lobby;
    }


    private void openGates(){
        spaceRepository.addGate(uri);
    }

    public boolean isDone() {
        return lobby.isDone();
    }
}
