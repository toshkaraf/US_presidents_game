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
import huds.MainMenuButtons;
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

        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT);
        mainCamera.position.set(GameInfo.WORLD_WIDTH / 2f, GameInfo.WORLD_HEIGHT / 2f, 0);

        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, mainCamera);

        background = new Texture("Backgrounds/USAPresidentsBackground.png");

        btns = menuButtons;

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getBatch().draw(background, 0, 0);

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(btns.getStage().getCamera().combined);
        btns.getStage().draw();
        btns.getStage().act();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
    }


    @Override
    public void dispose() {
        background.dispose();
        btns.getStage().dispose();
    }

}