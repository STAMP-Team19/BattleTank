package battletank.scene_management.screen;

import battletank.controls.ActionListener;
import battletank.world.gameobjects.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {

	Texture texture;
	SpriteBatch batch;
	float elapsed;

	static Player player = new Player("Troels", 100,100, 64,64, 0,10,2);

	private static ActionListener input = new ActionListener("hej", null);

	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	private Texture txtrBg;
	private Texture txtrBack;
	private Texture txtrLevelImage;

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
        System.out.println("render game");

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
        //batch.draw(texture, player.getPositionX(), player.getPositionY(), 100,100);
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
        tiledMap = new TmxMapLoader().load("src/main/java/battletank/Assets/maps/desertmap2/desertmap2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(input);
    }


	@Override
	public void dispose() {
		txtrBg.dispose();
		txtrBack.dispose();
		txtrLevelImage.dispose();
	}

}
