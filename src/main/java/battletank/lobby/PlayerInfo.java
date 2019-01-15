package battletank.lobby;

public class PlayerInfo {

    private String name;

    public PlayerInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "name='" + name + '\'' +
                '}';
    }
}
