package battletank.world.gameobjects;

import java.util.EnumMap;

public class PlayerColors {

    public enum PLAYERCOLOR {
        Blue, Green, Yellow, purple
    }

    public static EnumMap<PLAYERCOLOR, String> ColorMap;

    static {
        ColorMap = new EnumMap<PLAYERCOLOR, String>(PLAYERCOLOR.class);

        ColorMap.put(PLAYERCOLOR.purple, "src/main/resources/assets/img/TankPurple.png");
        ColorMap.put(PLAYERCOLOR.Blue, "src/main/resources/assets/img/TankBlue.png");
        ColorMap.put(PLAYERCOLOR.Green, "src/main/resources/assets/img/TankGreen.png");
        ColorMap.put(PLAYERCOLOR.Yellow, "src/main/resources/assets/img/TankYellow.png");
    }

    public String getTexstureFromEnum(PLAYERCOLOR color){
        return ColorMap.get(color);
    }




}