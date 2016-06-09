package huds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;


/**
 * Created by Fahir on 4/28/16.
 */
public class MainMenuButtons {

    private MainGame game;
    private Stage stage;
    private Viewport gameViewport;

    private Group reviewButton;
    private Group highscoreButton;
    private Group playButton;
    private Group trainingButton;
    private Group learnButton;

    private ImageButton reviewButtonImage;
    private ImageButton highscoreButtonImage;
    private ImageButton playButtonImage;
    private ImageButton trainingButtonImage;
    private ImageButton learnButtonImage;
    private ImageButton musicBtn;

    public MainMenuButtons(MainGame game) {
        this.game = game;

        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionButtons();
//        addAllListeners();

        stage.addActor(reviewButtonImage);
        stage.addActor(learnButtonImage);
        stage.addActor(trainingButtonImage);
        stage.addActor(playButtonImage);

        stage.addActor(highscoreButtonImage);

//        stage.addActor(musicBtn);

//        checkMusic();

    }

    void createAndPositionButtons() {

        reviewButtonImage = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("cards/card_of_president_red.png"))));

        learnButtonImage = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("cards/card_of_president.png"))));

        trainingButtonImage = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("cards/card_of_president_red.png"))));

        playButtonImage = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("cards/card_of_president.png"))));

        highscoreButtonImage = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("cards/card_of_president_red.png"))));


//        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(
//                new Texture("Buttons/Main Menu/Music On.png"))));


        reviewButtonImage.setPosition(GameInfo.WORLD_WIDTH / 2 - 80, GameInfo.WORLD_HEIGHT / 2 + 50,
                Align.center);

        learnButtonImage.setPosition(GameInfo.WORLD_WIDTH / 2 - 60, GameInfo.WORLD_HEIGHT / 2 - 20,
                Align.center);

        trainingButtonImage.setPosition(GameInfo.WORLD_WIDTH / 2 - 40, GameInfo.WORLD_HEIGHT / 2 - 90,
                Align.center);

        playButtonImage.setPosition(GameInfo.WORLD_WIDTH / 2 - 20, GameInfo.WORLD_HEIGHT / 2 - 160,
                Align.center);

//        musicBtn.setPosition(GameInfo.WORLD_WIDTH - 13, 13, Align.bottomRight);

    }


//    void addAllListeners() {
//        reviewButtonImage.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                // any code that we type will be executed when we press the play button
//                GameManager.getInstance().gameStartedFromMainMenu = true;
//
//                RunnableAction run = new RunnableAction();
//                run.setRunnable(new Runnable() {
//                    @Override
//                    public void run() {
//                        game.setScreen(new Gameplay(game));
//                    }
//                });
//
//                SequenceAction sa = new SequenceAction();
//                sa.addAction(Actions.fadeOut(1f));
//                sa.addAction(run);
//
//                stage.addAction(sa);
//
//            }
//        });
//
//        highscoreButtonImage.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new Highscore(game));
//            }
//        });
//
//        playButtonImage.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                game.setScreen(new Options(game));
//            }
//        });
//
//        learnButtonImage.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//
//            }
//        });
//
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
//    }
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






































