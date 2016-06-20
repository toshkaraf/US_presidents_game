package huds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
import helpers.GameManager;
import scenes.TetrisLearnMode;
import scenes.TetrisReview;
import scenes.TetrisTrainingMode;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


/**
 * Created by Fahir on 4/28/16.
 */
public class MainMenuButtons {

    private final Sprite redCard, blueCard;
    private MainGame game;
    private Stage stage;
    private Viewport gameViewport;


    private MenuCard reviewButton;
    private MenuCard highscoreButton;
    private MenuCard playButton;
    private MenuCard trainingButton;
    private MenuCard learnButton;
    private ImageButton musicBtn;

    public MainMenuButtons(MainGame game) {
        this.game = game;

        redCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president_red.png")));
        blueCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president.png")));

        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionButtons();
        addAllListeners();
        addShowActions();

        stage.addActor(reviewButton);
        stage.addActor(learnButton);
        stage.addActor(trainingButton);
        stage.addActor(playButton);
        stage.addActor(highscoreButton);

//        stage.addActor(musicBtn);
//        checkMusic();
    }


    void createAndPositionButtons() {

        reviewButton = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 + 90, "REVIEW PRESIDENTS", 20);
        learnButton = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "LEARN PRESIDENTS", 20);
        trainingButton = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "TRAINING MODE", 20);
        playButton = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "GAME MODE", 20);
        highscoreButton = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 150, "HIGH SCORE", 20);

//        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(
//                new Texture("cards/card_of_president_red.png"))));
//
//        musicBtn.setPosition(GameInfo.WORLD_WIDTH - 13, 13, Align.bottomRight);

    }

    private void addShowActions() {
        reviewButton.move(GameInfo.WORLD_WIDTH / 2 - 198 - 100);
        learnButton.move(GameInfo.WORLD_WIDTH / 2 - 198 - 50);
        trainingButton.move(GameInfo.WORLD_WIDTH / 2 - 198);
        playButton.move(GameInfo.WORLD_WIDTH / 2 - 198 + 50);
        highscoreButton.move(GameInfo.WORLD_WIDTH / 2 - 198 + 100);
    }

    private void addHideActions() {
        reviewButton.move(GameInfo.WORLD_WIDTH);
        learnButton.move(-400);
        trainingButton.move(GameInfo.WORLD_WIDTH);
        playButton.move(-400);
        highscoreButton.move(GameInfo.WORLD_WIDTH);
    }

    void addAllListeners() {
        reviewButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {

                addHideActions();
                GameManager.initNewGame(1, 44, 10);

                RunnableAction run = new RunnableAction();
                run.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new TetrisReview(game));
                    }
                });

                stage.addAction(sequence(delay(1f), run));
                return true;
            }
        });

        learnButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                addHideActions();
                game.setScreen(new TetrisLearnMode(game));
                return true;
            }
        });

        trainingButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                addHideActions();
                game.setScreen(new TetrisTrainingMode(game));
                return true;
            }
        });

        playButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                addHideActions();
                game.setScreen(new TetrisTrainingMode(game));
                return true;
            }
        });

//            highscoreButton.addListener(new ChangeListener() {
//                @Override
//                public void changed(ChangeEvent event, Actor actor) {
//                    game.setScreen(new (game, 1, 44));
//                }
//            });

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






































