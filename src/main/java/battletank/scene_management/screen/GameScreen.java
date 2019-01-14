package battletank.scene_management.screen;

import battletank.controls.ActionListener;
import battletank.controls.ActionSenderOffline;
import battletank.world.gameobjects.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

	static Player player = new Player("Troels", 100,100, 64,64, 0,0,0);

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
        //System.out.println("render game");

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
        batch.draw(texture, player.getPositionX(), player.getPositionY(), 100,100);
        batch.end();

        int objectLayerId = 3;
        TiledMapTileLayer collisionObjectLayer = (TiledMapTileLayer) tiledMap.getLayers().get(objectLayerId);
        MapObjects objects = collisionObjectLayer.getObjects();

        System.out.println(objects.getByType(MapObject.class));


// there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {

            Rectangle rectangle = rectangleObject.getRectangle();
            if (Intersector.overlaps(rectangle, player.getBody())) {
                // collision happened
                System.out.println("Col with wall");
            }
        }

    }
    /*
    private void update(int deltatime){
        // perform collision detection & response, on each axis, separately
        // if the koala is moving right, check the tiles to the right of it's
        // right bounding box edge, otherwise check the ones to the left
        Rectangle koalaRect = rectPool.obtain();
        koalaRect.set(player.getPositionX(), player.getPositionY(), player.getWidth(), player.getHeight());
        int startX, startY, endX, endY;
        if (player.getSpeed() > 0) {
            startX = endX = (int)(player.getPositionX() + player.getWidth() + player.getPositionX());
        } else {
            startX = endX = (int)(player.getPositionX() + player.getSpeed());
        }
        startY = (int)(player.position.y);
        endY = (int)(player.position.y + player.HEIGHT);
        getTiles(startX, startY, endX, endY, tiles);
        koalaRect.x += player.velocity.x;
        for (Rectangle tile : tiles) {
            if (koalaRect.overlaps(tile)) {
                player.velocity.x = 0;
                break;
            }
        }
        koalaRect.x = player.position.x;

        // if the koala is moving upwards, check the tiles to the top of its
        // top bounding box edge, otherwise check the ones to the bottom
        if (player.velocity.y > 0) {
            startY = endY = (int)(player.position.y + player.HEIGHT + player.velocity.y);
        } else {
            startY = endY = (int)(player.position.y + player.velocity.y);
        }
        startX = (int)(player.position.x);
        endX = (int)(player.position.x + player.WIDTH);
        getTiles(startX, startY, endX, endY, tiles);
        koalaRect.y += player.velocity.y;
        for (Rectangle tile : tiles) {
            if (koalaRect.overlaps(tile)) {
                // we actually reset the koala y-position here
                // so it is just below/above the tile we collided with
                // this removes bouncing :)
                if (player.velocity.y > 0) {
                    player.position.y = tile.y - player.HEIGHT;
                    // we hit a block jumping upwards, let's destroy it!
                    TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("walls");
                    layer.setCell((int)tile.x, (int)tile.y, null);
                } else {
                    player.position.y = tile.y + tile.height;
                    // if we hit the ground, mark us as grounded so we can jump
                    player.grounded = true;
                }
                player.velocity.y = 0;
                break;
            }
        }
        rectPool.free(koalaRect);

        // unscale the velocity by the inverse delta time and set
        // the latest position
        player.position.add(player.velocity);
        player.velocity.scl(1 / deltaTime);

        // Apply damping to the velocity on the x-axis so we don't
        // walk infinitely once a key was pressed
        player.velocity.x *= player.DAMPING;
    }
*/

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
        tiledMap = new TmxMapLoader().load("src/main/java/battletank/Assets/maps/desertmap2/desertmap1.tmx");
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
