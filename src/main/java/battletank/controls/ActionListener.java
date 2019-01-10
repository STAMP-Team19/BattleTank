package battletank.controls;

import spaces.game.connect.ActionSender;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ActionListener implements KeyListener {

    private ActionSender sender;
    private final char key;
    private final Action pressedAction;
    private final Action releasedAction;

    public ActionListener(ActionSender sender,char key, Action pressedAction, Action releaseAction) {
        this.sender = sender;
        this.key = key;
        this.pressedAction = pressedAction;
        this.releasedAction = releaseAction;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar()==key){
            sender.notifyAction(pressedAction);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar()==key){
            sender.notifyAction(releasedAction);
        }
    }
}
