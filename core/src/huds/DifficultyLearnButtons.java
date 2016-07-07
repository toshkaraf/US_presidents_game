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
public class DifficultyLearnButtons extends MenuButtons {

    public DifficultyLearnButtons(MainGame game) {
        super(game);
    }

    @Override
    void createAndPositionButtons() {
        button_1 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 + 90, "Choose from 2 dates", 20);
        button_2 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "Choose from 4 dates", 20);
        button_3 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "Choose from 6 dates", 20);
        button_4 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "Choose from all dates", 20);
        button_5 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 150, "Back", 20);
    }

    @Override
    void addAllListeners() {

        super.addAllListeners();

        button_1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.quantityOfHints = 1;
                hideMenu_startNewScreen(new HorisontalTetris(game));
                return true;
            }
        });

        button_2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.quantityOfHints = 3;
                hideMenu_startNewScreen(new HorisontalTetris(game));
                return true;
            }
        });

        button_3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.quantityOfHints = 5;
                hideMenu_startNewScreen(new HorisontalTetris(game));
                return true;
            }
        });

        button_4.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.quantityOfHints = 0;
                hideMenu_startNewScreen(new HorisontalTetris(game));
                return true;
            }
        });

        button_5.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new LearnModeButtons(game)));
                return true;
            }
        });
    }
}
