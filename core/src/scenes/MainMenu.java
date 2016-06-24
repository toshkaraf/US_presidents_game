package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import huds.GameModeButtons;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 09.06.2016.
 */
public class MainMenu implements Screen {

    private MainGame game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private Texture background;
    private MenuButtons btns;

    public MainMenu(MainGame game) {
        this.game = game;
        btns = new MenuButtons(game);
    }

    @Override
    public void show(){
        mainCamera = new OrthographicCamera();
        mainCamera.setToOrtho(false, GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT);
        mainCamera.position.set(GameInfo.WORLD_WIDTH / 2f, GameInfo.WORLD_HEIGHT / 2f, 0);
        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, mainCamera);
        background = new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground.png"));
        Gdx.input.setInputProcessor(btns.getStage());
        btns.addShowActions();
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
        gameViewport.update(width, height,true);
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
//        background.dispose();
//        btns.getStage().dispose();

    }

    public static class MenuButtons {

        final Sprite redCard, blueCard;
        MainGame game;
        Stage stage;
        Viewport gameViewport;
        MenuCard button_1, button_2, button_3, button_4, button_5;
        ImageButton musicBtn;

        public MenuButtons(MainGame game) {
            this.game = game;

            redCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president_red.png")));
            blueCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president.png")));

            gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                    new OrthographicCamera());

            stage = new Stage(gameViewport, new SpriteBatch());

            Gdx.input.setInputProcessor(stage);

            createAndPositionButtons();
            addAllListeners();


            stage.addActor(button_1);
            stage.addActor(button_2);
            stage.addActor(button_3);
            stage.addActor(button_4);
            stage.addActor(button_5);

    //        stage.addActor(musicBtn);
    //        checkMusic();
        }


        void createAndPositionButtons() {

            button_1 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 + 90, "REVIEW PRESIDENTS", 20);
            button_2 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "LEARN PRESIDENTS", 20);
            button_3 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "TRAINING MODE", 20);
            button_4 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "GAME MODE", 20);
            button_5 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 150, "HIGH SCORE", 20);
    //        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(
    //                new Texture("cards/card_of_president_red.png"))));
    //
    //        musicBtn.setPosition(GameInfo.WORLD_WIDTH - 13, 13, Align.bottomRight);

        }

        void addShowActions() {
            button_1.move(GameInfo.WORLD_WIDTH / 2 - 198 - 100);
            button_2.move(GameInfo.WORLD_WIDTH / 2 - 198 - 50);
            button_3.move(GameInfo.WORLD_WIDTH / 2 - 198);
            button_4.move(GameInfo.WORLD_WIDTH / 2 - 198 + 50);
            button_5.move(GameInfo.WORLD_WIDTH / 2 - 198 + 100);
        }

        void addHideActions() {
            button_1.move(GameInfo.WORLD_WIDTH);
            button_2.move(-400);
            button_3.move(GameInfo.WORLD_WIDTH);
            button_4.move(-400);
            button_5.move(GameInfo.WORLD_WIDTH);
        }

        void addAllListeners() {
            button_1.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                    GameManager.initNewGame(1,1,1);
                    hideMenu_startNewScreen(new TetrisLearnMode(game));
                    return true;
                }
            });

            button_2.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                    //                game.setScreen(new Menu(game, new LearnModeButtons(game)));
                    addHideActions();
                    MainGame.getInstance().setNewScreen(MainGame.KindsOfMenu.learnMenu);//                    RunnableAction run = new RunnableAction();
//                    run.setRunnable(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //                        stage.dispose();
//                        }
//                    });
//                    stage.addAction(sequence(delay(1f), run));
                    return true;
                }
            });

            button_3.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                    addHideActions();
                    game.setScreen(new TetrisTrainingMode(game));
                    return true;
                }
            });

            button_4.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                    addHideActions();
                    game.setScreen(new Menu(game, new GameModeButtons(game)));
                    return true;
                }
            });

            button_5.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                    addHideActions();
                    game.setScreen(new TetrisTrainingMode(game));
                    return true;
                }
            });


    //        musicBtn.addListener(new ChangeListener() {
    //            @Override
    //            public void changed(ChangeEvent event, Actor actor) {
    //                if(GameManager.getInstance().gameData.isMusicOn()) {
    //                    GameManager.getInstance().gameData.setMusicOn(false);
    //                    GameManager.getInstance().stopMusic();
    //                } else {
    //                    GameManager.getInstance().gameData.setMusicOn(true);
    //                    GameManager.getInstance().playMusic();
    //                }
    //                GameManager.getInstance().saveData();
    //            }
    //        });
        }

        void hideMenu_startNewScreen(final Screen screen) {
            addHideActions();
            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(screen);
//                    stage.dispose();
                }
            });
            stage.addAction(sequence(delay(1f), run));
        }

        //
    //    void checkMusic() {
    //        if(GameManager.getInstance().gameData.isMusicOn()) {
    //            GameManager.getInstance().playMusic();
    //        }
    //    }
    //
        public Stage getStage() {
            return this.stage;
        }

    } // main menu bnts
}