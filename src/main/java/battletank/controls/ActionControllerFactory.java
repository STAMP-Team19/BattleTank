package battletank.controls;

import battletank.controls.Action;
import battletank.controls.ActionInfo;
import battletank.controls.controllers.ActionController;
import battletank.controls.controllers.MovementController;


public class ActionControllerFactory {


    public ActionController constructAction(ActionInfo action){

        return new MovementController(action);
    }
}