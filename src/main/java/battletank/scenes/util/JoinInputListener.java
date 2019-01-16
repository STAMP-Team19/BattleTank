package battletank.scenes.util;

import com.badlogic.gdx.Input;

public class JoinInputListener implements Input.TextInputListener {

    private String lastoutput = "";
    private String type = "";
    private Boolean inputgiven = false;

    public String getLastoutput() {
        return lastoutput;
    }

    public void setLastoutput(String lastoutput) {
        this.lastoutput = lastoutput;
    }

    public JoinInputListener() {
    }

    @Override
    public void input (String text) {
        System.out.println("input: " + text);
        lastoutput = text;
        inputgiven = true;
    }

    @Override
    public void canceled () {
        inputgiven = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getInputgiven() {
        return inputgiven;
    }

    public void setInputgiven(Boolean inputgiven) {
        this.inputgiven = inputgiven;
    }
}
