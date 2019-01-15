package battletank.world.gameobjects;


import battletank.scene_management.screen.MyGame;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {


    public Player(String name, int positionX, int positionY, int width, int height, int rotation, int speed, int rotationSpeed, int healthpoints) {
        super(name, positionX, positionY, width, height, rotation, speed, rotationSpeed, healthpoints);
    }

    public Rectangle getBody() {
        System.out.println("x: " + positionX + " + y: " + positionY);
        return new Rectangle((float)positionX, (float)positionY, (float)width,(float) height);
    }


        }
