package battletank.controllers;

import battletank.IGame;
import battletank.controls.ActionInfo;

public class ActionControllerFactory {

    private final IGame game;

    public ActionControllerFactory(IGame game){
        this.game=game;
    }

    public ActionController constructAction(ActionInfo action) {
        switch (action.getAction()) {
            case Move_Backward:
            case Move_Forward:
            case Move_Stop:
                return new MovementController(game,action);
        }
        return null;
    }
}