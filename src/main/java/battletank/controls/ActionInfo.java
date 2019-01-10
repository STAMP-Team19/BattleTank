package battletank.controls;

public class ActionInfo {

    private final Action action;
    private final String userIdentifier;


    public ActionInfo(Action action, String userIdentifier){
        this.action = action;
        this.userIdentifier = userIdentifier;
    }

    public Action getAction() {
        return action;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }
}
