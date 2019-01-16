package battletank.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapLoader {


    private OrthographicCamera camera;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private MapObjects objects;


    public void loadMap(int level){
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
