package battletank.world.controllers;

import battletank.world.IGame;
import battletank.controls.ActionInfo;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.Player;
import spaces.game.hosting.WorldGateway;

public class MovementController extends ActionController {

    //TODO: Update for rotation

    public MovementController(IGame game, ActionInfo action) {
        Player player = game.getPlayer(action.getUserIdentifier());

        int direction = 0;
        switch (action.getAction()) {
            case MOVE_FORWARD:
                direction = 1;
                break;
            case MOVE_BACKWARD:
                direction = -1;
                break;
            case MOVE_STOP:
                game.addPlayerEvent(player,new StopTransition());
                return;
        }
        StartTransition transition = new StartTransition(player.getSpeed() * direction);
        game.addPlayerEvent(player,transition);




    }


}
