package battletank.controllers;

import battletank.controllers.ActionController;
import battletank.controllers.MovementController;
import battletank.controls.ActionInfo;

public class ActionControllerFactory {


    public ActionController constructAction(ActionInfo action){
        switch (action.getAction()){
            case Move_Backward:
            case Move_Forward:
            case Move_Stop:
                return new MovementController(action);
        }
        return null;
    }
}