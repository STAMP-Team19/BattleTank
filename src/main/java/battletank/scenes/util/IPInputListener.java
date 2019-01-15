package battletank.scenes.util;

import battletank.scenes.screen.MainMenuScreen;
import battletank.scenes.screen.MyGame;
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