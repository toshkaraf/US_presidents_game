package huds;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
import helpers.GameManager;
import scenes.HorisontalTetris;
import scenes.Menu;

/**
 * Created by Антон on 20.06.2016.
 */
public class LearnModeButtons extends MenuButtons {

    public LearnModeButtons(MainGame game) {
        super(game);
    }

    @Override
    void createAndPositionButtons() {
        button_1 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 + 90, "Presidents before 1850", 20);
        button_2 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "Presidents from 1850 to 1900", 20);
        button_3 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "Presidents from 1900 to 1950", 20);
        button_4 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "Presidents after 1950", 20);
        button_5 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 150, "Main menu", 20);
    }

    @Override
    void addAllListeners() {

        super.addAllListeners();

        button_1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new DifficultyLearnButtons(game)));
                GameManager.setFirstPresidentInRange(1);
                GameManager.setLastPresidentInRange(12);
                return true;
            }
        });

        button_2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new DifficultyLearnButtons(game)));
                GameManager.setFirstPresidentInRange(13);
                GameManager.setLastPresidentInRange(25);
                return true;
            }
        });

        button_3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new DifficultyLearnButtons(game)));
                GameManager.setFirstPresidentInRange(26);
                GameManager.setLastPresidentInRange(33);
                return true;
            }
        });

        button_4.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new DifficultyLearnButtons(game)));
                GameManager.setFirstPresidentInRange(34);
                GameManager.setLastPresidentInRange(44);
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
