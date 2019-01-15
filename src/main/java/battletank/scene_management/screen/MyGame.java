package battletank.scene_management.screen;


import battletank.controls.ActionListener;
import battletank.scene_management.util.ScreenEnum;
import battletank.scene_management.util.ScreenManager;
import battletank.world.gameobjects.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import spaces.game.connect.ActionSender;

import java.awt.*;

public class MyGame extends Game {

    Texture texture;
    public SpriteBatch batch;
    float elapsed;

    private static MyGame single_instance = null;

    private MyGame() {

    }

    public static MyGame getInstance()
    {
        if (single_instance == null)
            single_instance = new MyGame();

        return single_instance;
    }

   // public static Player player = new Player("Troels", 100,100, 64,64, 0,0,0);

  //  private static ActionListener input = new ActionListener(player, null);

    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    public BitmapFont font;

    @Override
    public void create () {

        batch = new SpriteBatch();
        font = new BitmapFont();

        //ScreenManager.getInstance().initialize(this);
        //ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU );

        this.setScreen(new JoinScreen(this));
        //this.setScreen(new GameScreen(0));

        texture = new Texture(Gdx.files.internal("src/main/java/battletank/assets/img/Tank.png"));
        batch = new SpriteBatch();
        loadMap();


    }


    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void render () {
        super.render();

        /*

        int objectLayerId = 5;
        TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer)map.getLayers().get(objectLayerId);
        MapObjects objects = collisionObjectLayer.getObjects();

// there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, player.getRectangle()) {
                // collision happened
            }
        }

        */

    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }


    @Override
    public void dispose () {
    }


    private void loadMap(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        tiledMap = new TmxMapLoader().load("src/main/java/battletank/assets/maps/desertmap2/desertmap2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
       // Gdx.input.setInputProcessor(input);
    }

}