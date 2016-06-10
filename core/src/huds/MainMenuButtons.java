package huds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
import scenes.TetrisGameMode;
import scenes.TetrisLearnMode;
import scenes.TetrisReview;
import scenes.TetrisTrainingMode;


/**
 * Created by Fahir on 4/28/16.
 */
public class MainMenuButtons {

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

        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionButtons();
        addAllListeners();

        stage.addActor(reviewButton);
        stage.addActor(learnButton);
        stage.addActor(trainingButton);
        stage.addActor(playButton);
        stage.addActor(highscoreButton);

//        stage.addActor(musicBtn);

//        checkMusic();

    }

    void createAndPositionButtons() {

        reviewButton = new MenuCard(new Sprite(new Texture("cards/card_of_president_red.png")),
                -400, GameInfo.WORLD_WIDTH / 2 - 198 - 100, GameInfo.WORLD_HEIGHT / 2 + 90, "REVIEW PRESIDENT");

        learnButton = new MenuCard(new Sprite(new Texture("cards/card_of_president.png")),
                1200, GameInfo.WORLD_WIDTH / 2 - 198 - 50, GameInfo.WORLD_HEIGHT / 2 + 30, "REVIEW PRESIDENT");

        trainingButton = new MenuCard(new Sprite(new Texture("cards/card_of_president_red.png")),
                -400, GameInfo.WORLD_WIDTH / 2 - 198, GameInfo.WORLD_HEIGHT / 2 - 30, "REVIEW PRESIDENT");

        playButton = new MenuCard(new Sprite(new Texture("cards/card_of_president.png")),
                1200, GameInfo.WORLD_WIDTH / 2 - 198 + 50, GameInfo.WORLD_HEIGHT / 2 - 90, "REVIEW PRESIDENT");

        highscoreButton = new MenuCard(new Sprite(new Texture("cards/card_of_president_red.png")),
                -400, GameInfo.WORLD_WIDTH / 2 - 198 + 100, GameInfo.WORLD_HEIGHT / 2 - 150 , "REVIEW PRESIDENT");


//        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(
//                new Texture("cards/card_of_president_red.png"))));
//
//        musicBtn.setPosition(GameInfo.WORLD_WIDTH - 13, 13, Align.bottomRight);

    }


        void addAllListeners() {
        reviewButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RunnableAction run = new RunnableAction();
                run.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new TetrisReview(game, 1, 30));
                    }
                });

                SequenceAction sa = new SequenceAction();
                sa.addAction(Actions.fadeOut(1f));
                sa.addAction(run);

                stage.addAction(sa);

            }
        });

            learnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TetrisLearnMode(game, 1, 44));
            }
        });

        trainingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TetrisTrainingMode(game, 1, 44));
            }
        });

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TetrisTrainingMode(game, 1, 44));
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






































