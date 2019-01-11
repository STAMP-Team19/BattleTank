package spaces.game.hosting;

import battletank.lobby.Lobby;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class LobbyProvider {

    private final String uri = "tcp://0.0.0.0:9001/?keep";
    private SpaceRepository spaceRepository;
    private Lobby lobby;

    public LobbyProvider(){
        this.spaceRepository = new SpaceRepository();

        setupLobbySpace();
        openGates();
    }

    public Lobby getLobby() {
        return lobby;
    }

    private void setupLobbySpace(){
        SequentialSpace lobbyspace = new SequentialSpace();
        spaceRepository.add("lobby", lobbyspace);
    }

    private void openGates(){
        spaceRepository.addGate(uri);
    }
}
