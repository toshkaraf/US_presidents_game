package huds;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
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
        super.createAndPositionButtons();

        button_1 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 + 90, "Presidents from 1 to 11", 20);
        button_2 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "Presidents from 12 to 22", 20);
        button_3 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "Presidents from 23 to 33", 20);
        button_4 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "Presidents from 34 to 44", 20);
        button_5 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 150, "Back", 20);
    }

    @Override
    void addAllListeners() {

        super.addAllListeners();

        button_1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new HorisontalTetris(game),1,12,3);
                return true;
            }
        });

        button_2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new HorisontalTetris(game),13,25,3);
                return true;
            }
        });

        button_3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new HorisontalTetris(game),26,33,3);
                return true;
            }
        });

        button_4.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new HorisontalTetris(game),34,44,3);
                return true;
            }
        });

        button_5.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new MainMenuButtons(game)),0,0,0);
                return true;
            }
        });
    }
}
