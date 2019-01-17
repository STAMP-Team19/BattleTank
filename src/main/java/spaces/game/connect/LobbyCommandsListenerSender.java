package spaces.game.connect;

import battletank.lobby.LOBBYCOMMANDS;
import battletank.lobby.PlayerInfo;
import battletank.world.gameobjects.Player;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;

import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyCommandsListenerSender implements ILobbyCommandsSender {

    private RemoteSpace lobbyspace;


    public LobbyCommandsListenerSender(String username, String ip, ILobbyListener listener){
        String uri = "tcp://"+ip+":9002/lobby?keep";
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
        catch (IllegalStateException e){
            sendCommand(playerInfo,command);
        }
    }

    @Override
    public boolean isLobbyOpen() {
        try {
            Object[] informations = lobbyspace.queryp(
                    new ActualField("STATUS"), new FormalField(Boolean.class));

            return (Boolean) informations[1];
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
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
        Gson gson = new Gson();
        loop: while(true){
            try {
                Object[] information = lobbyspace.get(new ActualField(username),
                        new FormalField(String.class),
                        new FormalField(LOBBYCOMMANDS.class));

                ArrayList<PlayerInfo> playerinfo = gson.fromJson((String)information[1], new TypeToken<ArrayList<PlayerInfo>>(){}.getType());
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
                        break loop;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e){

            }
            catch (IllegalStateException e){

            }
        }
    }
}
