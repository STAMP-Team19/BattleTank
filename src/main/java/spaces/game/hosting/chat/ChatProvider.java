package spaces.game.hosting.chat;

import battletank.chat.Chat;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;

public class ChatProvider {
    private final String uri = "tcp://0.0.0.0:9003/?keep";
    private SpaceRepository spaceRepository;
    private Chat chat;

    public void createChat(){
        spaceRepository = new SpaceRepository();
        SequentialSpace chatspace = new SequentialSpace();
        spaceRepository.add("chat", chatspace);

        chat = new Chat(chatspace);
        openGates();
    }

    private void openGates(){
        spaceRepository.addGate(uri);
    }
}
