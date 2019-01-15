package battletank.controls;

import spaces.game.connect.IActionSender;

import static battletank.scenes.screen.GameScreen.getPlayer;

public class ActionSenderOffline implements IActionSender {

        public ActionSenderOffline() {

        }

        public void notifyAction(Action action) {
            switch (action){
                case MOVE_FORWARD:
                    getPlayer().setPositionY(getPlayer().getPositionY()+10);
                    break;
                case MOVE_BACKWARD:
                    getPlayer().setPositionY(getPlayer().getPositionY()-10);
                    break;
                case ROTATE_LEFT:
                    getPlayer().setPositionX(getPlayer().getPositionX()-10);
                    break;
                case ROTATE_RIGHT:
                    getPlayer().setPositionX(getPlayer().getPositionX()+10);
                    break;
            }
        }


}

