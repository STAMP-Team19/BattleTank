package battletank.controllers;

import battletank.IGame;
import battletank.controls.ActionInfo;
import battletank.world.events.transitions.StartTransition;
import battletank.world.events.transitions.StopTransition;
import battletank.world.gameobjects.Player;
import spaces.game.hosting.WorldGateway;

public class MovementController extends ActionController {



    public MovementController(IGame game, ActionInfo action) {
        WorldGateway gateway =game.getWorldGateway();
        Player player = game.getPlayer(action.getUserIdentifier());

        int direction=0;
        switch (action.getAction()){
            case Move_Forward:
                direction=1;
                break;
            case Move_Backward:
                direction=-1;
                break;
            case Move_Stop:
                gateway.update(player,new StopTransition());
                return;
        }
        StartTransition transition = new StartTransition(player.getSpeed()*direction);
        gateway.update(player,transition);

    }
}
