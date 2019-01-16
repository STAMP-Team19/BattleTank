package battletank.scenes.util;

import com.badlogic.gdx.Input;

public class JoinInputListener implements Input.TextInputListener {

    private String lastoutput = "";
    private String type = "";

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
        setType("Join");
    }

    @Override
    public void canceled () {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
