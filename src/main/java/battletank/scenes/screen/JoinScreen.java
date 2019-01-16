package battletank.scenes.screen;

import battletank.lobby.LOBBYCOMMANDS;
import battletank.lobby.PlayerInfo;
import battletank.scenes.util.CreateInputListener;
import battletank.scenes.util.JoinInputListener;
import battletank.world.GameRules;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import spaces.game.connect.ILobbyListener;
import spaces.game.connect.LobbyCommandsListenerSender;
import spaces.game.hosting.LobbyProvider;

import static battletank.scenes.screen.GameScreen.player;

public class JoinScreen implements Screen, ILobbyListener {

    final MyGame game;

    Texture tankImage;
    Texture playbtn;
    Texture serverbtnTexture;
    Texture createserverbtnTexture;
    Sound dropSound;
    Music music;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;
    CreateInputListener createInputListener;
    JoinInputListener joinInputListener;
    ArrayList<PlayerInfo> joinedPlayersList;
    Stage stage;
    private String name = "";

    ImageButton joinbtn;
    ImageButton playButton;
    ImageButton createButton;

    static LobbyCommandsListenerSender controller;


    public JoinScreen(final MyGame game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        tankImage = new Texture(Gdx.files.internal("src/main/resources/assets/img/Tank.png"));
        playbtn = new Texture(Gdx.files.internal("src/main/resources/assets/img/playbtn.png"));
        serverbtnTexture = new Texture(Gdx.files.internal("src/main/resources/assets/img/editserverbtn.png"));
        createserverbtnTexture = new Texture(Gdx.files.internal("src/main/resources/assets/img/createserverbtn.png"));

        // load the drop sound effect and the rain background "music"
        //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("src/main/resources/assets/music/music.mp3"));
        music.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;



        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();


        // all input for screen
        createInputListener = new CreateInputListener();

        Gdx.input.getTextInput(createInputListener, "Write player name", "", "Name of player");

        Drawable newurl = new TextureRegionDrawable(new TextureRegion(serverbtnTexture));
        joinbtn = new ImageButton(newurl);

        joinbtn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Enter new server");
                //Gdx.input.getTextInput(joinInputListener, "Write IP of server", "", "IP");
                lobby();
                System.out.println("Lobby Open: "+controller.isLobbyOpen());
                controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.JOIN);
            }
        });

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playbtn));
        playButton = new ImageButton(drawable);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("play!");
                game.setScreen(new GameScreen(0));
            }
        });

        Drawable drawcreateserver = new TextureRegionDrawable(new TextureRegion(createserverbtnTexture));
        createButton = new ImageButton(drawcreateserver);


        createButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("create server");
                //Gdx.input.getTextInput(createInputListener, "Enter name of server", "", "server name");

                LobbyProvider provider = new LobbyProvider();

                provider.createLobby(name, 4, new GameRules());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lobby();
                controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.OPEN);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.JOIN);
            }
        });


        joinbtn.setPosition(400-(joinbtn.getWidth()/2), 50);
        playButton.setPosition(400-(playButton.getWidth()/2), 100);
        createButton.setPosition(400-(createButton.getWidth()/2), 150);


        // add to stage
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(joinbtn);
        stage.addActor(createButton);
        stage.addActor(playButton); //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui


    }

    public void lobby(){
        controller = new LobbyCommandsListenerSender(name,this);
    }

    private void spawnRaindrop() {
        Rectangle drivingTanks = new Rectangle();
        drivingTanks.x = MathUtils.random(0, 800 - 64);
        drivingTanks.y = 480;
        drivingTanks.width = 64;
        drivingTanks.height = 64;
        raindrops.add(drivingTanks);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {

        name = createInputListener.getLastoutput();

        // activate and deaktivate buttons.

        if(name == ""){
            joinbtn.setDisabled(true);
            createButton.setDisabled(true);
        }
        else {
            joinbtn.setDisabled(false);
            createButton.setDisabled(false);
        }

        /*
        if(joinedPlayersList.size() < 2){

        }
        else {
            joinbtn.setDisabled(false);
        }
        */


        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, name, 800/2, 450);

        //game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        //game.batch.draw(playbtn, bucket.x, bucket.y, bucket.width*2, bucket.height);

        for (Rectangle raindrop : raindrops) {
            game.batch.draw(tankImage, raindrop.x, raindrop.y);
        }

        if(joinedPlayersList != null) {
            for (int i = 0; i < joinedPlayersList.size(); i++) {
                game.font.draw(game.batch, joinedPlayersList.get(i).getName(), 800 / 2 - (joinedPlayersList.get(i).getName().length() * 3), i * 20 + 330);
            }
        }


        /*
        int i = 0;
        for (PlayerInfo info : joinedPlayersList) {
            game.font.draw(game.batch, info.toString(), 800/2-(info.toString().length()*3), i*20+330);
            i++;
        }
        */
        /*
        if (joinedPlayersList != null) {
            for (Map.Entry<String, PlayerInfo> entry : joinedPlayersList.entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue().getName());
                game.font.draw(game.batch, entry.getValue().getName(), 800 / 2 - (entry.getValue().getName().length() * 3), i * 20 + 330);
                i++;
            }
        }
        */

        game.batch.end();

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
              //  dropSound.play();
                iter.remove();
            }
        }

        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        music.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        tankImage.dispose();
        playbtn.dispose();
        dropSound.dispose();
        music.dispose();
    }

    @Override
    public void notifyLobby(ArrayList<PlayerInfo> playersList) {
        joinedPlayersList = playersList;
        System.out.println("###########  players joined ############");
        System.out.println(playersList);
        System.out.println("########################################");
    }

    @Override
    public void startGame() {

    }

    @Override
    public void deleteLobby() {

    }
}