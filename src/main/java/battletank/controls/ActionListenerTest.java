package battletank.controls;

import battletank.scene_management.screen.MyGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


//TODO: Update this class later on, when ActionListener functions properly.
class ActionListenerTest {

    public static void main(String[] args) {
        //Create new game
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = false;
        config.foregroundFPS = 30;
        // config.backgroundFPS = 24;
        config.height = 800;
        config.width = 800;
        config.resizable = true;
        config.title = "BattleTank";
        MyGame game = MyGame.getInstance();
        new LwjglApplication(game, config);

        ActionListener actionListener = new ActionListener("Test", null);



    }
}
