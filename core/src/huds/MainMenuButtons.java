package huds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
import helpers.GameManager;
import scenes.Menu;
import scenes.TetrisLearnMode;
import scenes.TetrisTrainingMode;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 20.06.2016.
 */
public class MainMenuButtons extends MenuButtons {

    public MainMenuButtons(MainGame game) {
        super(game);
    }

    @Override
    void createAndPositionButtons() {
        super.createAndPositionButtons();
        button_1 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 + 90, "REVIEW PRESIDENTS", 20);
        button_2 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "LEARN PRESIDENTS", 20);
        button_3 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "TRAINING MODE", 20);
        button_4 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "GAME MODE", 20);
        button_5 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 150, "HIGH SCORE", 20);
    }

    @Override
    void addAllListeners() {
        super.addAllListeners();

        button_1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.initNewGame(2,2,0);
                hideMenu_startNewScreen(new TetrisLearnMode(game));
                return true;
            }
        });

        button_2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
//                game.setScreen(new Menu(game, new LearnModeButtons(game)));
                addHideActions();
                RunnableAction run = new RunnableAction();
                run.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        MainGame.getInstance().setNewScreen(MainGame.KindsOfMenu.learnMenu);
                        game.setScreen(new Menu(game, new LearnModeButtons(game)));

//                        stage.dispose();
                    }
                });
                stage.addAction(sequence(delay(1f), run));
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
    }
}