package battletank.scenes.screen;

import battletank.lobby.LOBBYCOMMANDS;
import battletank.lobby.PlayerInfo;
import battletank.scenes.screen.chat.chatwindow;
import battletank.scenes.util.CreateInputListener;
import battletank.scenes.util.JoinInputListener;
import battletank.world.GameRules;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import spaces.game.connect.ILobbyListener;
import spaces.game.connect.LobbyCommandsListenerSender;
import spaces.game.hosting.LobbyProvider;


public class JoinScreen implements Screen, ILobbyListener {

    boolean startupDone= false;
    final MyGame game;

    private Texture tankImage;

    private Texture playbtn;
    private Texture serverbtnTexture;
    private Texture createserverbtnTexture;
    private Texture leavebtnTexture;
    private Texture stopbtnTexture;
    private Texture launchbtnTexture;

    private Texture playbtnDown;
    private Texture serverbtnTextureDown;
    private Texture createserverbtnTextureDown;
    private Texture leavebtnTextureDown;
    private Texture stopbtnTextureDown;
    private Texture launchbtnTextureDown;

    private Texture checkboxON;
    private Texture checkboxOFF;

    private Music music;
    private Music ready;
    private OrthographicCamera camera;
    private long lastDropTime;
    private CreateInputListener createInputListener;
    private JoinInputListener joinInputListener;
    private ArrayList<PlayerInfo> joinedPlayersList;
    private Stage stage;
    private String name = "";
    private String msg = "";
    private static String IP = "0.0.0.0";
    private Boolean playgame = false;
    private String ShowIp = "";
    private LobbyProvider provider;
    private Boolean joined = false;
    private Boolean creater = false;

    private ImageButton joinbtn;
    private ImageButton playButton;
    private ImageButton createButton;
    private ImageButton leaveBtn;
    private ImageButton launchBtn;
    private ImageButton checkBtn;

    static LobbyCommandsListenerSender controller;

    private Texture map1;
    private Texture map2;
    private Texture map3;

    private ArrayList<ImageButton> maplist;

    private int chosenMap = -1;

    private Boolean serverClosed_ENDGAME = false;

    private static Texture backgroundTexture;
    private static Sprite backgroundSprite;

    private String[] randName = {"Player 1", "Player 2", "Player 3", "Player 4"};

    private Boolean bounce = true;

    private Drawable ON;
    private Drawable OFF;
    private GameRules gameRules;

    public JoinScreen(final MyGame game) {
        this.game = game;
        gameRules=new GameRules();
        loadTextures();
        setupButtons();
        setupSound();

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);


        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
    }

    private void setupSound(){
        // load music
        music = Gdx.audio.newMusic(Gdx.files.internal("src/main/resources/assets/music/music.mp3"));
        music.setLooping(true);
        ready = Gdx.audio.newMusic(Gdx.files.internal("src/main/resources/assets/music/ready.mp3"));
    }

    private void setupButtons(){
        // all input for screen
        createInputListener = new CreateInputListener();
        joinInputListener = new JoinInputListener();

        Gdx.input.getTextInput(createInputListener, "Write player name", "", "Name of player");

        Drawable map1Draw = new TextureRegionDrawable(new TextureRegion(map1));
        Drawable map2Draw = new TextureRegionDrawable(new TextureRegion(map2));
        Drawable map3Draw = new TextureRegionDrawable(new TextureRegion(map3));

        maplist = new ArrayList<>();

        maplist.add(new ImageButton(map1Draw));
        maplist.add(new ImageButton(map2Draw));
        maplist.add(new ImageButton(map3Draw));


        Drawable newurl = new TextureRegionDrawable(new TextureRegion(serverbtnTexture));
        Drawable newurlDown = new TextureRegionDrawable(new TextureRegion(serverbtnTextureDown));
        joinbtn = new ImageButton(newurl, newurlDown);


        joinbtn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Enter new server");
                Gdx.input.getTextInput(joinInputListener, "Write IP of server", "", "IP");
                joined = true;
                playButton.setVisible(false);
                createButton.setVisible(false);
            }
        });

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playbtn));
        Drawable drawableDown = new TextureRegionDrawable(new TextureRegion(playbtnDown));
        playButton = new ImageButton(drawable, drawableDown);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(chosenMap != -1) {
                    controller.sendMAPCommand(chosenMap, LOBBYCOMMANDS.SETMAP);
                    msg="Loading Map...";
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("play!" + name);

                    controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.STARTGAME);
                }else {
                    msg = "You need to choose a map.";
                }
            }
        });

        Drawable Launchserver = new TextureRegionDrawable(new TextureRegion(launchbtnTexture));
        Drawable LaunchserverDown = new TextureRegionDrawable(new TextureRegion(launchbtnTextureDown));
        launchBtn = new ImageButton(Launchserver, LaunchserverDown);
        launchBtn.setVisible(false);

        launchBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {

                if(provider == null){
                    provider = new LobbyProvider();

                    provider.createLobby(name, 4, gameRules, chosenMap);
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    lobby();
                }

                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("create server");
                controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.OPEN);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.JOIN);

                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ShowIp = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                controller.sendMAPCommand(chosenMap, LOBBYCOMMANDS.SETMAP);

                playButton.setVisible(true);
                createButton.setVisible(true);
                playButton.setVisible(true);
            }
        });


        Drawable drawcreateserver = new TextureRegionDrawable(new TextureRegion(createserverbtnTexture));
        Drawable drawcreateserverDown = new TextureRegionDrawable(new TextureRegion(createserverbtnTextureDown));
        createButton = new ImageButton(drawcreateserver, drawcreateserverDown);

        Drawable stopdrawable = new TextureRegionDrawable(new TextureRegion(stopbtnTexture));

        createButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                joined = true;
                if(!creater) {
                    msg = "Pick a map!";
                    //Gdx.input.getTextInput(createInputListener, "Enter name of server", "", "server name");
                    //lobby();
                    creater = true;
                    createButton.setBackground(stopdrawable);
                    for (ImageButton btn: maplist) {
                        btn.setVisible(creater);
                    }
                    launchBtn.setVisible(creater);

                    joinbtn.setDisabled(true);
                    joinbtn.setVisible(!creater);
                    createButton.setDisabled(true);
                    createButton.setVisible(!creater);
                    leaveBtn.setDisabled(true);
                    leaveBtn.setVisible(!creater);
                    playButton.setVisible(!creater);

                } else {
                    creater = false;
                    createButton.setBackground(drawcreateserver);
                    for (ImageButton btn: maplist) {
                        btn.setVisible(creater);
                    }
                    launchBtn.setVisible(creater);

                    joinbtn.setDisabled(false);
                    createButton.setDisabled(false);
                    leaveBtn.setDisabled(false);
                    joinbtn.setVisible(creater);
                    createButton.setVisible(creater);
                    leaveBtn.setVisible(creater);
                    playButton.setVisible(creater);
                }
            }
        });


        Drawable leaveDrawable = new TextureRegionDrawable(new TextureRegion(leavebtnTexture));
        Drawable leaveDrawableDown = new TextureRegionDrawable(new TextureRegion(leavebtnTextureDown));
        leaveBtn = new ImageButton(leaveDrawable, leaveDrawableDown);

        leaveBtn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(joined) {
                    System.out.println("Leave!");
                    controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.LEAVE);
                }
                joined = false;
            }
        });

        launchBtn.setPosition(400-(leaveBtn.getWidth()/2), 200);
        joinbtn.setPosition(400-(joinbtn.getWidth()/2), 50);
        playButton.setPosition(400-(playButton.getWidth()/2), 100);
        createButton.setPosition(400-(createButton.getWidth()/2), 150);
        leaveBtn.setPosition(400-(leaveBtn.getWidth()/2), 0);

        Drawable ON = new TextureRegionDrawable(new TextureRegion(checkboxOFF));
        Drawable OFF = new TextureRegionDrawable(new TextureRegion(checkboxON));
        checkBtn = new ImageButton(OFF,OFF,ON);

        checkBtn.setPosition(100, 100);
        checkBtn.setWidth(100);

        checkBtn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //Gdx.graphics.setContinuousRendering(checkBtn.isChecked());
                System.out.println(bounce);
                if(bounce) {
                    Gdx.graphics.setContinuousRendering(checkBtn.isChecked());
                    bounce = false;
                    gameRules.setBounce(bounce);
                }
                else {
                    Gdx.graphics.setContinuousRendering(checkBtn.isChecked());
                    bounce = true;
                    gameRules.setBounce(bounce);
                }
            }
        });

        // add to stage
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(joinbtn);
        stage.addActor(createButton);
        stage.addActor(playButton); //Add the button to the stage to perform rendering and take input.
        stage.addActor(leaveBtn);
        stage.addActor(launchBtn);
        stage.addActor(checkBtn);


            int x = 163;
            int y = 240;
            int f = 1;
            int normalSize = 90;

            for (ImageButton btn : maplist) {
                btn.setHeight(normalSize);
                btn.setWidth(normalSize + 60);
                btn.setPosition(x * f, y);
                stage.addActor(btn);
                f++;
                btn.setVisible(false);

                btn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        for (ImageButton btn : maplist) {
                            btn.setHeight(normalSize);
                            btn.setWidth(normalSize + 60);
                            chosenMap = -1;
                        }
                        if (btn.isChecked()) {
                            btn.setHeight(160);
                            btn.setWidth(160);
                            chosenMap = maplist.indexOf(btn);
                        }
                        System.out.println("map: " + chosenMap);
                    }
                });
            }
    }

    private void loadTextures() {
        // load the images for the droplet and the bucket, 64x64 pixels each
        //tankImage = new Texture(Gdx.files.internal("src/main/resources/assets/img/Tank.png"));
        playbtn = new Texture(Gdx.files.internal("src/main/resources/assets/img/playbtn.png"));
        serverbtnTexture = new Texture(Gdx.files.internal("src/main/resources/assets/img/editserverbtn.png"));
        createserverbtnTexture = new Texture(Gdx.files.internal("src/main/resources/assets/img/createserverbtn.png"));
        leavebtnTexture = new Texture(Gdx.files.internal("src/main/resources/assets/img/Leave.png"));
        stopbtnTexture = new Texture(Gdx.files.internal("src/main/resources/assets/img/Stopserverbtn.png"));
        launchbtnTexture = new Texture(Gdx.files.internal("src/main/resources/assets/img/launch.png"));

        playbtnDown = new Texture(Gdx.files.internal("src/main/resources/assets/img/playbtnDown.png"));
        serverbtnTextureDown = new Texture(Gdx.files.internal("src/main/resources/assets/img/editserverbtnDown.png"));
        createserverbtnTextureDown = new Texture(Gdx.files.internal("src/main/resources/assets/img/createserverbtnDown.png"));
        leavebtnTextureDown = new Texture(Gdx.files.internal("src/main/resources/assets/img/LeaveDOwn.png"));
        stopbtnTextureDown = new Texture(Gdx.files.internal("src/main/resources/assets/img/StopserverbtnDown.png"));
        launchbtnTextureDown = new Texture(Gdx.files.internal("src/main/resources/assets/img/launchDown.png"));

        checkboxON = new Texture(Gdx.files.internal("src/main/resources/assets/img/on.png"));
        checkboxOFF = new Texture(Gdx.files.internal("src/main/resources/assets/img/off.png"));

        map1 = new Texture(Gdx.files.internal("src/main/resources/assets/maps/maps/desertmap1new.png"));
        map2 = new Texture(Gdx.files.internal("src/main/resources/assets/maps/maps/desertmap2new.png"));
        map3 = new Texture(Gdx.files.internal("src/main/resources/assets/maps/maps/desertmap3new.png"));

        backgroundTexture = new Texture("src/main/resources/assets/img/bg.png");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(800, 480);

    }

    public void lobby(){
        if(controller==null) {
            controller = new LobbyCommandsListenerSender(name, IP, this);

            // Open Chat Window
            chatwindow window = new chatwindow(name, IP);
            new Thread(window).start();
        }
/*
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                LobbyCommandsListenerSender controller = new LobbyCommandsListenerSender("shutdown", IP, new JoinScreen(MyGame.getInstance()));
                controller.sendCommand(new PlayerInfo("shutdown"), LOBBYCOMMANDS.ENDGAME);
            }
        }, "Shutdown-thread"));
        */
    }

    @Override
    public void render(float delta) {

        if(chosenMap == -1){
            launchBtn.setDisabled(true);
        }else{
            launchBtn.setDisabled(false);
        }

        if(playgame){
            game.setScreen(new GameScreen(chosenMap, IP, name,gameRules));
        }

        name = createInputListener.getLastoutput();


        if(joined && joinInputListener.getLastoutput() != ""){
            IP = joinInputListener.getLastoutput();

            lobby();

            //controller.sendCommand(new PlayerInfo(name), LOBBYCOMMANDS.OPEN);

            try {
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
        backgroundSprite.draw(game.batch);
        game.font.draw(game.batch, name, 800/2-(name.length()*3), 450);
        game.font.draw(game.batch, msg, 800/2-(msg.length()*3), 400);
        game.font.draw(game.batch, ShowIp, 800/2-(ShowIp.length()*3), 475);
        game.font.draw(game.batch, "Bullet bounce:", 100, 115);

        if(joinedPlayersList != null) {
            for (int i = 0; i < joinedPlayersList.size(); i++) {
                game.font.draw(game.batch, joinedPlayersList.get(i).getName(), 800 / 2 - (joinedPlayersList.get(i).getName().length() * 3), i * 20 + 263);
            }
        }


        servercheck();
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui

    }

    private void servercheck(){
        if(serverClosed_ENDGAME){
            game.font.draw(game.batch, "The server has shut down!", 800/2-(name.length()*3), 400);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.exit(0);
        }
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
        music.dispose();
    }

    @Override
    public void notifyLobby(ArrayList<PlayerInfo> playersList) {
        joinedPlayersList = playersList;
        /*
        System.out.println("###########  players joined ############");
        System.out.println(playersList);
        System.out.println("########################################");
        */
    }

    @Override
    public void startGame() {
        //System.out.println("hej nu spiller du");
        ready.play();
        msg = "Starting game....";
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

    @Override
    public void notifyLobbymap(int level) {
        chosenMap = level;
    }

    @Override
    public void endGame() {
        serverClosed_ENDGAME = true;
    }
}