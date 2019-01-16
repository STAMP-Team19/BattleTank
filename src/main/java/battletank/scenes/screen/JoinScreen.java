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

    private String msg = "";

    private String IP = "0.0.0.0";

    private Boolean playgame = false;

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

        // all input for screen
        createInputListener = new CreateInputListener();
        joinInputListener = new JoinInputListener();

        Gdx.input.getTextInput(createInputListener, "Write player name", "", "Name of player");

        Drawable newurl = new TextureRegionDrawable(new TextureRegion(serverbtnTexture));
        joinbtn = new ImageButton(newurl);

        joinbtn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Enter new server");
                Gdx.input.getTextInput(joinInputListener, "Write IP of server", "", "IP");
            }
        });

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playbtn));
        playButton = new ImageButton(drawable);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("play!" + name);

                controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.STARTGAME);
            }
        });

        Drawable drawcreateserver = new TextureRegionDrawable(new TextureRegion(createserverbtnTexture));
        createButton = new ImageButton(drawcreateserver);


        createButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("create server");
                //Gdx.input.getTextInput(createInputListener, "Enter name of server", "", "server name");
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
        controller = new LobbyCommandsListenerSender(name,IP,this);
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

        if(playgame){
            game.setScreen(new GameScreen(0, IP, player.getName()));
        }

        name = createInputListener.getLastoutput();


        if(joinInputListener.getInputgiven()){
            IP = joinInputListener.getLastoutput();
            try {
            lobby();
            System.out.println("Lobby Open: "+controller.isLobbyOpen());
            if (controller.isLobbyOpen()) {

                    controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.JOIN);

            }else {
                msg ="There is no server running to join";
            }
            joinInputListener.setInputgiven(false);
            }
            catch (Exception e){
                msg ="Host unreachable";
            }
        }


        // activate and deaktivate buttons.

        if(name == ""){
            joinbtn.setDisabled(true);
            createButton.setDisabled(true);
        }
        else {
            joinbtn.setDisabled(false);
            createButton.setDisabled(false);
            LobbyProvider provider = new LobbyProvider();
            provider.createLobby(name, 4, new GameRules());
        }

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
        game.font.draw(game.batch, msg, 800/2, 470);

        if(joinedPlayersList != null) {
            for (int i = 0; i < joinedPlayersList.size(); i++) {
                game.font.draw(game.batch, joinedPlayersList.get(i).getName(), 800 / 2 - (joinedPlayersList.get(i).getName().length() * 3), i * 20 + 330);
            }
        }

        game.batch.end();

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
        music.pause();
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
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
        System.out.println("hej nu spiller du");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playgame = true;
    }

    @Override
    public void deleteLobby() {

    }
}