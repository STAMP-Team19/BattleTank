package battletank.controls.controllers;

import battletank.world.gameobjects.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputProcessor;

public class Input extends ApplicationAdapter implements InputProcessor {

    Player player;

    public Input(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int i) {
        player.setPositionX(player.getPositionX() - 1);
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        //player.setPositionX(player.getPositionX() + 1);
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
