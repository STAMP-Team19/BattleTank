package battletank.controls;

import battletank.controls.Action;
import battletank.controls.ActionKeyParser;
import battletank.world.gameobjects.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import spaces.game.connect.ActionSender;
import spaces.game.connect.IActionSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionListener extends ApplicationAdapter implements InputProcessor {

    Player player;
    private IActionSender actionSender;
    ActionKeyParser actionKeyParser = new ActionKeyParser();
    Map<String, Action> controlMapping;


    public ActionListener(Player player, IActionSender actionSender) {
        this.player = player;
        this.actionSender = actionSender;
        controlMapping = actionKeyParser.getControlMapping();
    }


    @Override
    public boolean keyDown(int i) {

        String userInput = Input.Keys.toString(i) + "_p";
        Action userAction = controlMapping.get(userInput);

        actionSender.notifyAction(userAction);

        return false;
    }

    @Override
    public boolean keyUp(int i) {

        String userInput = Input.Keys.toString(i) + "_r";
        Action userAction = controlMapping.get(userInput);

        actionSender.notifyAction(userAction);

        return false;
    }


    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
