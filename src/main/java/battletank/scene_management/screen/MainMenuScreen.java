package battletank.scene_management.screen;

import battletank.scene_management.util.ScreenEnum;
import battletank.scene_management.util.UIFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;


public class MainMenuScreen implements Screen {

    final MyGame game;
	
	private Texture txtrBg;
	private Texture txtrPlay;
	private Texture txtrExit;
	
	public MainMenuScreen(MyGame game) {
		super();
		this.game = game;
		txtrBg   = new Texture( Gdx.files.internal("/Users/troels/IdeaProjects/BattleTank/src/main/java/battletank/assets/img/playbtn.png") );
		txtrPlay = new Texture( Gdx.files.internal("/Users/troels/IdeaProjects/BattleTank/src/main/java/battletank/assets/img/playbtn.png") );
		txtrExit = new Texture( Gdx.files.internal("/Users/troels/IdeaProjects/BattleTank/src/main/java/battletank/assets/img/playbtn.png") );
	}

	/*
	@Override
	public void buildStage() {

		// Adding actors
		Image bg = new Image(txtrBg);
		addActor(bg);

		ImageButton btnPlay = UIFactory.createButton(txtrPlay);
		btnPlay.setPosition(getWidth() / 2, 120.f, Align.center);
		addActor(btnPlay);

		ImageButton btnExit = UIFactory.createButton(txtrExit);
		btnExit.setPosition(getWidth() / 2, 60.f, Align.center);
		addActor(btnExit);

		// Setting listeners
		btnPlay.addListener(UIFactory.createListener(ScreenEnum.GAME));

		btnExit.addListener(
				new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
						Gdx.app.exit();
						return false;
					}
				});
	}
*/
    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0,0,1f,1);
        game.batch.begin();
        game.font.draw(game.batch, "hej", 100, 100);
        game.batch.end();

        if(Gdx.input.isButtonPressed(Input.Keys.A)){
            game.setScreen(new GameScreen(0));
            System.out.println("click");
            dispose();
        }

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
	public void dispose() {
		txtrBg.dispose();
		txtrPlay.dispose();
		txtrExit.dispose();
	}
}
