package battletank.scenes.screen;

import battletank.controls.ActionListener;
import battletank.lobby.PlayerInfo;
import battletank.world.*;
import battletank.world.gameobjects.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import spaces.game.connect.ActionSender;
import spaces.game.connect.ILobbyListener;
import spaces.game.connect.WorldEventsListener;

import java.util.*;

public class GameScreen implements Screen, ILobbyListener {
    Projectile pro=null;
	Texture texture;
	SpriteBatch batch;
	float elapsed;
    MapObjects objects;

    private Music music;
    //private Boolean serverClosed_ENDGAME = false;

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
    private Stage stage;

    private Texture bullet;

	private Texture healthCon;
    private Texture healthbar;
    private Texture healthbar90;
    private Texture healthbar50;
    private Texture healthbar10;

	private TextureRegion textureRegion = new TextureRegion();
    private TextureRegion textureRegionBullet = new TextureRegion();

	private ColorTextureMapper colorTextureMapper = new ColorTextureMapper();

	Map<PlayerColor, Texture> textureMap = new EnumMap<PlayerColor, Texture>(PlayerColor.class);

	private Pixmap pixmap = new Pixmap(50, 10, Pixmap.Format.RGBA8888);
	private TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));

	// healthbar
	private int totalBarWidth = 50;
	private NinePatch health;
    private NinePatch health90;
    private NinePatch health50;
    private NinePatch health10;
    private NinePatch container;

    // name of player text
    BitmapFont playerNamefont;
    BitmapFont WinnerNamefont;

    // Current level
	private int level;

	public GameScreen(Integer level, String IP, String playerName, GameRules rules) {
		super();
        this.level = level.intValue();
        camera = new OrthographicCamera();
		txtrBg   = new Texture( Gdx.files.internal("src/main/resources/assets/img/playbtn.png") );
		txtrBack = new Texture( Gdx.files.internal("src/main/resources/assets/img/playbtn.png") );

        healthCon = new Texture( Gdx.files.internal("src/main/resources/assets/img/container.png"));

        healthbar = new Texture( Gdx.files.internal("src/main/resources/assets/img/bar.png"));
        healthbar90 = new Texture( Gdx.files.internal("src/main/resources/assets/img/90bar.png"));
        healthbar50 = new Texture( Gdx.files.internal("src/main/resources/assets/img/50bar.png"));
        healthbar10 = new Texture( Gdx.files.internal("src/main/resources/assets/img/10bar.png"));

        health = new NinePatch(healthbar, 0, 0, 0, 0);
        health90 = new NinePatch(healthbar90, 0, 0, 0, 0);
        health50 = new NinePatch(healthbar50, 0, 0, 0, 0);
        health10 = new NinePatch(healthbar10, 0, 0, 0, 0);

        container = new NinePatch(healthCon, 5, 5, 2, 2);

        playerNamefont = new BitmapFont();

        bullet = new Texture( Gdx.files.internal("src/main/resources/assets/img/bullet.png"));

        WinnerNamefont = new BitmapFont();
        WinnerNamefont.setColor(Color.BLUE);

        textureRegionBullet.setTexture(bullet);

        batch = new SpriteBatch();

        // load music
        music = Gdx.audio.newMusic(Gdx.files.internal("src/main/resources/assets/music/music.mp3"));
        music.setLooping(true);

        shapeRenderer = new ShapeRenderer();
        setupOnlineGame();

        this.Ip = IP;

        loadMap(level);

        deltaTime=new DeltaTime();
        worldSimulator = new WorldSimulator(deltaTime, level,rules);
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
        deltaTime.setTime((long) ((1000) * Gdx.graphics.getDeltaTime()));
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

        List<GameObject> gameObjects = worldSimulator.getGameObjects();
        for (GameObject go : gameObjects) {
            GameObject player = go;
            //batch.draw(texture, (int) player.getPositionX(), (int) player.getPositionY(), (int) player.getWidth(), (int) player.getHeight());

        /* shape of colider box
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(playerbody.x, playerbody.y, playerbody.getWidth(), playerbody.getHeight());
        shapeRenderer.end();
        */

            // healthbar
            if (go instanceof Player) {

                if (textureMap.containsKey(player.getColor())) {
                    texture = textureMap.get(player.getColor());
                } else {
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
                        (float) player.getRotation() - 90
                );

                int width =(int)(player.getHealthpoints() / 100.0 * totalBarWidth);

                container.draw(batch, (float) player.getPositionX() - 10, (float) player.getPositionY() + 70, totalBarWidth + 4, 9);

                if(player.getHealthpoints() >= 100) {
                    health.draw(batch, (float) player.getPositionX() + 2 - 10, (float) player.getPositionY() + 70 + 2, width, 5);
                }
                else if(player.getHealthpoints() <= 99 && player.getHealthpoints() >= 60){
                    health90.draw(batch, (float) player.getPositionX() + 2 - 10, (float) player.getPositionY() + 70 + 2, width, 5);
                }
                else if(player.getHealthpoints() <= 59 && player.getHealthpoints() >= 30){
                    health50.draw(batch, (float) player.getPositionX() + 2 - 10, (float) player.getPositionY() + 70 + 2, width, 5);
                }
                else if(player.getHealthpoints() <= 29 && player.getHealthpoints() >= 0){
                    health10.draw(batch, (float) player.getPositionX() + 2 - 10, (float) player.getPositionY() + 70 + 2, width, 5);
                }
                else {
                    health.draw(batch, (float) player.getPositionX() + 2 - 10, (float) player.getPositionY() + 70 + 2, width, 5);
                }
                playerNamefont.draw(batch, player.getName(), (float) player.getPositionX() - 10, (float) player.getPositionY() + 100);
            }
            else {
                textureRegion.setTexture(bullet);
                batch.draw(textureRegion,
                        (float) player.getPositionX(),
                        (float) player.getPositionY(),
                        (float) player.getOriginX(),
                        (float) player.getOriginY(),
                        (float) player.getWidth(),
                        (float) player.getHeight(),
                        2,
                        2,
                        (float) player.getRotation() - 90
                );
            }

            }
            DrawWin();
            batch.end();
        }



    private void DrawWin(){
	    if(worldSimulator.getWinner() != null) {
            WinnerNamefont.getData().setScale(3);
            container.draw(batch, 0, 800/2-100, 800, 200);
            WinnerNamefont.draw(batch,  worldSimulator.getWinner().getName() + " is the winner!", 130, 400);
        }
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

    @Override
    public void notifyLobby(ArrayList<PlayerInfo> playersList) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void deleteLobby() {

    }

    @Override
    public void notifyLobbymap(int level) {

    }

    @Override
    public void endGame() {
        //serverClosed_ENDGAME = true;
    }

    /*
    private void servercheck(){
        if(serverClosed_ENDGAME){
            playerNamefont.draw(batch, "Server is down!", 400, 400);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.exit(0);
        }
    }
    */
}