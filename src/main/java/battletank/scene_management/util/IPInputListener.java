package battletank.scene_management.util;

import battletank.scene_management.screen.GameScreen;
import battletank.scene_management.screen.JoinScreen;
import battletank.scene_management.screen.MainMenuScreen;
import battletank.scene_management.screen.MyGame;
import com.badlogic.gdx.Input;

public class IPInputListener implements Input.TextInputListener {

    String lastoutput = "";

    @Override
    public void input (String text) {
        System.out.println("input: " + text);
        lastoutput = text;
    }

    @Override
    public void canceled () {
        MyGame.getInstance().setScreen(new MainMenuScreen(MyGame.getInstance()));
    }

    public String getLastoutput() {
        return lastoutput;
    }
}