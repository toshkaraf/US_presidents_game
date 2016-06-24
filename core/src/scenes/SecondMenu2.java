package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
import helpers.GameManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 22.06.2016.
 */
public class SecondMenu2 extends ScreenAdapter {

    private MainGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private Texture background;
    private MenuButtons btns;

    public SecondMenu2(MainGame game) {
        this.game = game;
        btns = new MenuButtons(game);
    }

    @Override
    public void show() {
        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT);
        mainCamera.position.set(GameInfo.WORLD_WIDTH / 2f, GameInfo.WORLD_HEIGHT / 2f, 0);
        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, mainCamera);

        background = new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground.png"));
        Gdx.input.setInputProcessor(btns.getStage());
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(btns.getStage().getCamera().combined);
        btns.getStage().getBatch().begin();
        btns.getStage().getBatch().draw(background, 0, 0);
        btns.getStage().getBatch().end();
        btns.getStage().draw();
        btns.getStage().act();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
    }


    private class MenuButtons {

        final Sprite redCard, blueCard;
        Game game;
        Stage stage;
        Viewport gameViewport;
        MenuCard button_2;
        ImageButton musicBtn;

        public MenuButtons(final MainGame game) {
            this.game = game;

            redCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president_red.png")));
            blueCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president.png")));

            gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                    new OrthographicCamera());

            stage = new Stage(gameViewport, new SpriteBatch());

            Gdx.input.setInputProcessor(stage);

            button_2 = new MenuCard(blueCard, -400, GameInfo.WORLD_HEIGHT / 2 + 30, "LEARN PRESIDENTS", 20);

            addShowActions();

            button_2.addListener(new InputListener() {

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                    addHideActions();
                    GameManager.initNewGame(1,1,0);
                    RunnableAction run = new RunnableAction();
                    run.setRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new TetrisLearnMode(game));
                        }
                    });
                    stage.addAction(sequence(delay(1f), run));
                    return true;
                }
            });

            stage.addActor(button_2);
        }

        void addShowActions() {
            button_2.move(GameInfo.WORLD_WIDTH / 2 - 198 - 50);
        }

        void addHideActions() {
            button_2.move(-400);
        }

        public Stage getStage() {
            return this.stage;
        }

    }
}

