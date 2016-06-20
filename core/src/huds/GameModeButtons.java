package huds;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
import helpers.GameManager;
import scenes.Menu;
import scenes.TetrisLearnMode;

/**
 * Created by Антон on 20.06.2016.
 */
public class GameModeButtons extends MenuButtons {

    public GameModeButtons(MainGame game) {
        super(game);
    }

    @Override
    void createAndPositionButtons() {
        super.createAndPositionButtons();
        button_1 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 + 90, "Choose from 4 dates", 20);
        button_2 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "Choose from 8 dates", 20);
        button_3 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "Choose from 16 dates", 20);
        button_4 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "Choose from all dates", 20);
        button_5 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 150, "Back", 20);
    }

    @Override
    void addAllListeners() {

        super.addAllListeners();

        button_1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.initNewGame(1, 44, 3);
                hideMenu_startNewScreen(new TetrisLearnMode(game));
                return true;
            }
        });

        button_2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.initNewGame(1, 44, 7);
                hideMenu_startNewScreen(new TetrisLearnMode(game));
                return true;
            }
        });

        button_3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.initNewGame(1, 44, 15);
                hideMenu_startNewScreen(new TetrisLearnMode(game));
                return true;
            }
        });

        button_4.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.initNewGame(1, 44, 44);
                hideMenu_startNewScreen(new TetrisLearnMode(game));
                return true;
            }
        });

        button_5.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new MainMenuButtons(game)));
                return true;
            }
        });
    }
}
