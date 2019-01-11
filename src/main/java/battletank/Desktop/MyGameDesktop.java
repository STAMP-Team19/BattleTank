package battletank.Desktop;
import battletank.Core.MyGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;



public class MyGameDesktop {
    public static void main (String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = false;
        config.foregroundFPS = 30;
       // config.backgroundFPS = 24;
        config.height = 800;
        config.width = 800;
        config.resizable = true;
        config.title = "BattleTank";
        new LwjglApplication(new MyGame(), config);
    }
}