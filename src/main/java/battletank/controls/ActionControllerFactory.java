package battletank.controls;

import battletank.controls.controllers.ActionController;
import battletank.controls.controllers.MovementController;

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