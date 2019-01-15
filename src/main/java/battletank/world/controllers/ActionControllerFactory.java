package battletank.world.controllers;

import battletank.IGame;
import battletank.controls.ActionInfo;

public class ActionControllerFactory {

    private final IGame game;

    public ActionControllerFactory(IGame game) {
        this.game = game;
    }

    public ActionController constructAction(ActionInfo action) {
        switch (action.getAction()) {
            case MOVE_BACKWARD:
            case MOVE_FORWARD:
            case MOVE_STOP:
                return new MovementController(game, action);
        }
        return null;
    }
}