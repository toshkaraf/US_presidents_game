package huds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 20.06.2016.
 */

public abstract class MenuButtons {

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

    public void show(){
        Gdx.input.setInputProcessor(stage);
        addShowActions();
    }

    void createAndPositionButtons() {
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
                stage.dispose();
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






































