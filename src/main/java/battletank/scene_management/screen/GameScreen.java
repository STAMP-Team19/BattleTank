package battletank.scene_management.screen;

import battletank.controls.ActionListener;
import battletank.controls.ActionSenderOffline;
import battletank.world.gameobjects.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

	Texture texture;
	SpriteBatch batch;
	float elapsed;
    MapObjects objects;

	static Player player = new Player("Troels", 0,0, 134/4,249/4, 0,0,0,100);

    public static Player getPlayer() {
        return player;
    }

    private static ActionListener input = new ActionListener("hej", new ActionSenderOffline());

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	private Texture txtrBg;
	private Texture txtrBack;
	private Texture txtrLevelImage;
	private ShapeRenderer shapeRenderer;

	// Current level
	private int level;

	public GameScreen(Integer level) {
		super();
        this.level = level.intValue();
        camera = new OrthographicCamera();
		txtrBg   = new Texture( Gdx.files.internal("/Users/troels/IdeaProjects/BattleTank/src/main/java/battletank/Assets/img/playbtn.png") );
		txtrBack = new Texture( Gdx.files.internal("/Users/troels/IdeaProjects/BattleTank/src/main/java/battletank/Assets/img/playbtn.png") );

        texture = new Texture(Gdx.files.internal("src/main/java/battletank/Assets/img/Tank.png"));
        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(input);



        loadMap(level);
	}


    @Override
    public void show() {

    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void render (float v) {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        batch.draw(texture, player.getPositionX(), player.getPositionY(), player.getWidth(),player.getHeight());

        Rectangle playerbody = player.getBody();

// there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, playerbody)) {
                // collision happened
                System.out.println("Col with wall");

            }
        }
        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(playerbody.x, playerbody.y, playerbody.getWidth(), playerbody.getHeight());
        shapeRenderer.end();
        */

        batch.end();

    }

    public void pause () {
    }

    public void resume () {
    }

    @Override
    public void hide() {

    }


    private void loadMap(int Level){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        switch (level){
            case 0:
                tiledMap = new TmxMapLoader().load("src/main/java/battletank/Assets/maps/desertmap2/desertmap1new.tmx");
                break;
            case 1:
                tiledMap = new TmxMapLoader().load("src/main/java/battletank/Assets/maps/maps/desertmap2new.tmx");
                break;
            default:
                tiledMap = new TmxMapLoader().load("src/main/java/battletank/Assets/maps/maps/desertmap1new.tmx");
                break;
        }

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        int objectLayerId = tiledMap.getLayers().getIndex("Collidables");

        MapLayer collisionObjectLayer = tiledMap.getLayers().get(objectLayerId);
        objects = collisionObjectLayer.getObjects();

        String name = collisionObjectLayer.getName();
        float opacity = collisionObjectLayer.getOpacity();
        boolean isVisible = collisionObjectLayer.isVisible();

        System.out.println("obj til col: " + objects.getCount() + "  name: " + name + "op: " + opacity + "isvis: " + isVisible);

    }


	@Override
	public void dispose() {
		txtrBg.dispose();
		txtrBack.dispose();
		txtrLevelImage.dispose();
	}

}
