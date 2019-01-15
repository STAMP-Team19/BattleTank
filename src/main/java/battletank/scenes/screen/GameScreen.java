package battletank.scenes.screen;

import battletank.controls.ActionListener;
import battletank.controls.ActionSenderOffline;
import battletank.world.DeltaTime;
import battletank.world.Game;
import battletank.world.WorldSimulator;
import battletank.world.gameobjects.GameObject;
import battletank.world.gameobjects.Player;
import battletank.world.gameobjects.PlayerColors;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import spaces.game.connect.ActionSender;
import spaces.game.connect.WorldEventsListener;
import spaces.game.hosting.GameHost;

import java.util.HashMap;
import java.util.List;

public class GameScreen implements Screen {

	Texture texture;
	SpriteBatch batch;
	float elapsed;
    MapObjects objects;

    WorldSimulator worldSimulator;

	static Player player = new Player("Troels", 0,0, 134/4,249/4, 0,0,0,100, PlayerColors.PLAYERCOLOR.Blue);
    private DeltaTime deltaTime;

    public static Player getPlayer() {
        return player;
    }

    private static ActionListener input = null;

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
		txtrBg   = new Texture( Gdx.files.internal("src/main/resources/assets/img/playbtn.png") );
		txtrBack = new Texture( Gdx.files.internal("src/main/resources/assets/img/playbtn.png") );

        texture = new Texture(Gdx.files.internal("src/main/resources/assets/img/Tank.png"));
        batch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        setupOnlineGame();
        Gdx.input.setInputProcessor(input);



        loadMap(level);
	}

	private void setupOnlineGame(){
        HashMap<String, Player> players = new HashMap<>();
        Player player =new Player("Troels", 100,100, 134/4,249/4, 90,50,90,100, PlayerColors.PLAYERCOLOR.Blue);
        players.put(player.getName(), player);
        GameHost host = new GameHost(new Game(players) {
        });
        deltaTime=new DeltaTime();
        worldSimulator = new WorldSimulator(deltaTime);
        new WorldEventsListener(player.getName(),worldSimulator);
        input=new ActionListener(player.getName(), new ActionSender(player.getName()));
    }

    @Override
    public void show() {

    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void render (float v) {
	    deltaTime.setTime((long) ((1000)*Gdx.graphics.getDeltaTime()));
	    worldSimulator.handleTick();
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
        List<GameObject> gameObjects =worldSimulator.getGameObjects();
        for(GameObject go : gameObjects) {
            Player player = (Player) go;
            //batch.draw(texture, (int) player.getPositionX(), (int) player.getPositionY(), (int) player.getWidth(), (int) player.getHeight());

            batch.draw(new TextureRegion(texture),
                    (float) player.getPositionX(),
                    (float) player.getPositionY(),
                    (float) player.getOriginX(),
                    (float) player.getOriginY(),
                    (float) player.getWidth(),
                    (float) player.getHeight(),
                    1,
                    1,
                    (float)player.getRotation()-90);
            Rectangle playerbody = player.getBody();

            // there are several other types, Rectangle is probably the most common one
            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

                Rectangle rectangle = rectangleObject.getRectangle();
                if (Intersector.overlaps(rectangle, playerbody)) {
                    // collision happened
                    //System.out.println("Col with wall");

                }
            }
        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(playerbody.x, playerbody.y, playerbody.getWidth(), playerbody.getHeight());
        shapeRenderer.end();
        */
        }
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
                tiledMap = new TmxMapLoader().load("src/main/resources/assets/maps/desertmap2/desertmap1new.tmx");
                break;
            case 1:
                tiledMap = new TmxMapLoader().load("src/main/resources/assets/maps/maps/desertmap2new.tmx");
                break;
            default:
                tiledMap = new TmxMapLoader().load("src/main/resources/assets/maps/maps/desertmap1new.tmx");
                break;
        }

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        int objectLayerId = tiledMap.getLayers().getIndex("Collidables");

        MapLayer collisionObjectLayer = tiledMap.getLayers().get(objectLayerId);
        objects = collisionObjectLayer.getObjects();

        String name = collisionObjectLayer.getName();
        float opacity = collisionObjectLayer.getOpacity();
        boolean isVisible = collisionObjectLayer.isVisible();

        //System.out.println("obj til col: " + objects.getCount() + "  name: " + name + "op: " + opacity + "isvis: " + isVisible);

    }


	@Override
	public void dispose() {
		txtrBg.dispose();
		txtrBack.dispose();
		txtrLevelImage.dispose();
	}

}
