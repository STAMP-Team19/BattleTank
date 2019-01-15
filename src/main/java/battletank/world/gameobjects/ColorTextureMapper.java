package battletank.world.gameobjects;

import java.util.EnumMap;

public class ColorTextureMapper {

    public static EnumMap<PlayerColor, String> ColorMap;

    static {
        ColorMap = new EnumMap<PlayerColor, String>(PlayerColor.class);

        ColorMap.put(PlayerColor.purple, "src/main/resources/assets/img/TankPurple.png");
        ColorMap.put(PlayerColor.Blue, "src/main/resources/assets/img/TankBlue.png");
        ColorMap.put(PlayerColor.Green, "src/main/resources/assets/img/TankGreen.png");
        ColorMap.put(PlayerColor.Yellow, "src/main/resources/assets/img/TankYellow.png");
    }

    public String getTexstureFromEnum(PlayerColor color){
        return ColorMap.get(color);
    }




}