package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import huds.MenuButtons;

/**
 * Created by Антон on 09.06.2016.
 */
public class Menu extends ScreenAdapter {

    private MainGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private Texture background;
    private MenuButtons btns;

    public Menu(MainGame game, MenuButtons menuButtons) {
        this.game = game;
        btns = menuButtons;
        background = new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBgd.png"));

    }

    @Override
    public void show() {
        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT);
        mainCamera.position.set(GameInfo.WORLD_WIDTH / 2f, GameInfo.WORLD_HEIGHT / 2f, 0);
        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, mainCamera);
        btns.show();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(btns.getStage().getCamera().combined);
//        game.getBatch().setProjectionMatrix(mainCamera.combined);
//        game.getBatch().setTransformMatrix(mainCamera.view);
        btns.getStage().getBatch().begin();
        btns.getStage().getBatch().draw(background, 0, 0);
        btns.getStage().getBatch().end();


//        game.getBatch().setTransformMatrix(btns.getStage().getCamera().view);
        btns.getStage().draw();
        btns.getStage().act();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
    }


    @Override
    public void dispose() {
        background.dispose();
        btns.getStage().dispose();

    }

}