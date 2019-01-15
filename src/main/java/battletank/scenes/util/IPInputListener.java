package battletank.scenes.util;

import battletank.scenes.screen.MainMenuScreen;
import battletank.scenes.screen.MyGame;
import com.badlogic.gdx.Input;

public class IPInputListener implements Input.TextInputListener {

    private String lastoutput = "";
    private Boolean inputGiven = false;

    public IPInputListener() {
        inputGiven = false;
    }

    @Override
    public void input (String text) {
        System.out.println("input: " + text);
        lastoutput = text;
        inputGiven = true;
    }

    @Override
    public void canceled () {
        MyGame.getInstance().setScreen(new MainMenuScreen(MyGame.getInstance()));
    }

    public String getLastoutput() {
        return lastoutput;
    }

    public Boolean getInputGiven() {
        return inputGiven;
    }

    public void setInputGiven(Boolean inputGiven) {
        this.inputGiven = inputGiven;
    }
}