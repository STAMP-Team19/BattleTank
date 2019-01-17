package battletank.scenes.screen;

import battletank.controls.ActionListener;
import battletank.world.DeltaTime;
import battletank.world.Game;
import battletank.world.MapLoader;
import battletank.world.WorldSimulator;
import battletank.world.gameobjects.ColorTextureMapper;
import battletank.world.gameobjects.GameObject;
import battletank.world.gameobjects.Player;
import battletank.world.gameobjects.PlayerColor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import spaces.game.connect.ActionSender;
import spaces.game.connect.WorldEventsListener;
import spaces.game.hosting.GameHost;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameScreen implements Screen {

	Texture texture;
	SpriteBatch batch;
	float elapsed;
    MapObjects objects;

    private Music music;

    WorldSimulator worldSimulator;

    private DeltaTime deltaTime;


    private static ActionListener input = null;

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	private Texture txtrBg;
	private Texture txtrBack;
	private Texture txtrLevelImage;
	private ShapeRenderer shapeRenderer;
	private String Ip;

	private TextureRegion textureRegion = new TextureRegion();

	private ColorTextureMapper colorTextureMapper = new ColorTextureMapper();

	Map<PlayerColor, Texture> textureMap = new EnumMap<PlayerColor, Texture>(PlayerColor.class);

	// Current level
	private int level;

	public GameScreen(Integer level, String IP, String playerName) {
		super();
        this.level = level.intValue();
        camera = new OrthographicCamera();
		txtrBg   = new Texture( Gdx.files.internal("src/main/resources/assets/img/playbtn.png") );
		txtrBack = new Texture( Gdx.files.internal("src/main/resources/assets/img/playbtn.png") );

        batch = new SpriteBatch();

        // load music
        music = Gdx.audio.newMusic(Gdx.files.internal("src/main/resources/assets/music/music.mp3"));
        music.setLooping(true);

        shapeRenderer = new ShapeRenderer();
        setupOnlineGame();

        this.Ip = IP;

        loadMap(level);

        deltaTime=new DeltaTime();
        worldSimulator = new WorldSimulator(deltaTime, level);
        new WorldEventsListener(playerName,worldSimulator,IP);
        input=new ActionListener(playerName, new ActionSender(playerName, IP));

        Gdx.input.setInputProcessor(input);
	}

	private void setupOnlineGame(){

    }

    @Override
    public void show() {
        music.play();
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

            if(textureMap.containsKey(player.getColor())){
                texture = textureMap.get(player.getColor());
            }
            else{
                texture = new Texture(Gdx.files.internal(colorTextureMapper.getTexstureFromEnum(player.getColor())));
                textureMap.put(player.getColor(), texture);
            }

            textureRegion.setRegion(texture);

            batch.draw(textureRegion,
                    (float) player.getPositionX(),
                    (float) player.getPositionY(),
                    (float) player.getOriginX(),
                    (float) player.getOriginY(),
                    (float) player.getWidth(),
                    (float) player.getHeight(),
                    1,
                    1,
                    (float)player.getRotation()-90
            );

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
	    music.pause();
    }

    public void resume () {
	    music.play();
    }

    @Override
    public void hide() {
        music.pause();
    }


    private void loadMap(int level){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();


        MapLoader mapLoader = new MapLoader();
        mapLoader.loadMap(level);
        tiledMap = mapLoader.getTiledMap();
        tiledMapRenderer = mapLoader.getTiledMapRenderer();
        objects = mapLoader.getObjects();


        //System.out.println("obj til col: " + objects.getCount() + "  name: " + name + "op: " + opacity + "isvis: " + isVisible);

    }


	@Override
	public void dispose() {
		txtrBg.dispose();
		txtrBack.dispose();
		txtrLevelImage.dispose();
		music.dispose();
	}

}
