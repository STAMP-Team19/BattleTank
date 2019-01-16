package battletank.scenes.util;

import battletank.scenes.screen.MainMenuScreen;
import battletank.scenes.screen.MyGame;
import com.badlogic.gdx.Input;

public class CreateInputListener implements Input.TextInputListener {

    private String lastoutput = "";
    private String type = "";

    public CreateInputListener() {
    }

    @Override
    public void input (String text) {
        System.out.println("input: " + text);
        lastoutput = text;
    }

    @Override
    public void canceled () {
    }

    public String getLastoutput() {
        return lastoutput;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}