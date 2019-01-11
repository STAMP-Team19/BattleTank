package battletank;

import battletank.world.gameobjects.Player;
import spaces.game.hosting.WorldGateway;

import java.util.Set;

public interface IGame {
    void setWorldGateway(WorldGateway worldGateway);

    WorldGateway getWorldGateway();

    Player getPlayer(String userIdentifier);

    Set<String> getPlayerNames();
}
