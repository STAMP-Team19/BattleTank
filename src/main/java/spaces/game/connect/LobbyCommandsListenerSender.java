package spaces.game.connect;

import battletank.lobby.LOBBYCOMMANDS;
import battletank.lobby.PlayerInfo;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import java.io.IOException;
import java.util.HashMap;

public class LobbyCommandsListenerSender implements ILobbyCommandsSender {

    private RemoteSpace lobbyspace;

    public LobbyCommandsListenerSender(String username, ILobbyListener listener){
        String uri = "tcp://0.0.0.0:9001/lobby?keep";
        try {
            lobbyspace = new RemoteSpace(uri);

            new Thread(new LobbyObserver(lobbyspace, username, listener)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCommand(PlayerInfo playerInfo, LOBBYCOMMANDS command) {
        try {
            lobbyspace.put(playerInfo, command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class LobbyObserver implements Runnable{

    RemoteSpace lobbyspace;
    String username;
    ILobbyListener listener;

    public LobbyObserver(RemoteSpace lobbyspace, String username, ILobbyListener listener) {
        this.lobbyspace = lobbyspace;
        this.username = username;
        this.listener = listener;
    }

    @Override
    public void run() {
        while(true){
            try {
                Object[] information = lobbyspace.get(new ActualField(username),
                        new FormalField(HashMap.class),
                        new FormalField(LOBBYCOMMANDS.class));

                HashMap<String, PlayerInfo> playerinfo = (HashMap<String, PlayerInfo>) information[1];
                LOBBYCOMMANDS command = (LOBBYCOMMANDS) information[2];

                System.out.println(playerinfo+" "+command.toString());

                switch(command){
                    case REFRESH:
                        listener.notifyLobby(playerinfo);
                        break;

                    case STARTGAME:
                        listener.startGame();
                        break;

                    case DELETELOBBY:
                        listener.deleteLobby();
                        break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
