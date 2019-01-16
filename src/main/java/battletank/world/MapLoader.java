package battletank.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.Arrays;

public class MapLoader {

    private Boolean loaded = false;

    private OrthographicCamera camera;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private MapObjects objects;

    public void loadMapNoUI(int level){
        if(Thread.currentThread().getName().equals("LWJGL Application")){
            loadMap(level);
            return;
        }
        Gdx.app.postRunnable(()-> loadMap(level));
        while(!loaded){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMap(int level){

        switch (level){
            case 0:
                tiledMap = new TmxMapLoader().load("src/main/resources/assets/maps/desertmap2/desertmap1new.tmx");
                break;
            case 1:
                tiledMap = new TmxMapLoader().load("src/main/resources/assets/maps/desertmap2/desertmap2new.tmx");
                break;
            case 2:
                tiledMap = new TmxMapLoader().load("src/main/resources/assets/maps/desertmap2/desertmap3new.tmx");
                break;
            default:
                tiledMap = new TmxMapLoader().load("src/main/resources/assets/maps/maps/desertmap1new.tmx");
                break;
        }

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        int objectLayerId = tiledMap.getLayers().getIndex("Collidables");

        MapLayer collisionObjectLayer = tiledMap.getLayers().get(objectLayerId);
        objects = collisionObjectLayer.getObjects();

        loaded = true;
    }
    public OrthographicCamera getCamera() {
        return camera;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public MapObjects getObjects() {
        return objects;
    }
}
