package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;
import huds.MenuButtons;

/**
 * Created by Антон on 09.06.2016.
 */
public class LoadingScreen extends ScreenAdapter {

    private MainGame game;
    private OrthographicCamera camera;
    private Viewport gameViewport;
    private Texture background;
    private Sprite player;
    private boolean isInit = false;
    private int firstPresidentInRange, lastPresidentInRange, quantityOfHints;

    public LoadingScreen(MainGame game, int firstPresidentInRange, int lastPresidentInRange, int quantityOfHints) {
        this.game = game;
        this.firstPresidentInRange = firstPresidentInRange;
        this.lastPresidentInRange = lastPresidentInRange;
        this.quantityOfHints = quantityOfHints;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT);
        camera.position.set(GameInfo.WORLD_WIDTH / 2f, GameInfo.WORLD_HEIGHT / 2f, 0);
        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);
        background = new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground.png"));
        player = new Sprite(new Texture(Gdx.files.internal("players/arrowUSA.png")));
        player.setPosition(GameInfo.WORLD_WIDTH / 2 - player.getWidth() / 2, GameInfo.WORLD_HEIGHT / 2 - player.getHeight() / 2);
        player.setBounds(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        player.setOrigin(player.getWidth() / 2, player.getHeight() / 2);
        Runnable initiatorNewGame = new Runnable() {
            @Override
            public void run() {
                if (GameManager.initNewGame(firstPresidentInRange, lastPresidentInRange, quantityOfHints));
                    isInit = true;
            }
        };
        (new Thread(initiatorNewGame)).start();
    }

    @Override
    public void render(float delta) {
        if (isInit) game.setScreen(new HorisontalTetrisField(game));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(camera.projection);
        game.getBatch().setTransformMatrix(camera.view);
        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        player.draw(game.getBatch());
        game.getBatch().end();
        updatePlayer();

    }

    private void updatePlayer() {
        player.rotate(1f);
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
    }


    @Override
    public void dispose() {
        background.dispose();
        player.getTexture().dispose();
    }

}