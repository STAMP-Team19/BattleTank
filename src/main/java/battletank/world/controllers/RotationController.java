package battletank.world.controllers;

import battletank.controls.ActionInfo;
import battletank.world.IGame;
import battletank.world.events.rotations.StartRotation;
import battletank.world.events.rotations.StopRotation;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.Player;
import spaces.game.hosting.WorldGateway;

public class RotationController extends ActionController {

    public RotationController(IGame game, ActionInfo action) {
        super();
        WorldGateway gateway = game.getWorldGateway();
        Player player = game.getPlayer(action.getUserIdentifier());

        int direction = 0;
        switch (action.getAction()) {
            case ROTATE_RIGHT:
                direction = -1;
                break;
            case ROTATE_LEFT:
                direction = 1;
                break;
            case MOVE_STOP:
                gateway.update(player, new StopRotation());
                game.addPlayerEvent(player,new StopRotation());
                return;
        }
        StartRotation transition = new StartRotation(player.getRotationSpeed() * direction);
        gateway.update(player, transition);
        game.addPlayerEvent(player,transition);
    }
}
