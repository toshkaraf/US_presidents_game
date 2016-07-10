package huds;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
import helpers.GameManager;
import scenes.HorisontalTetris;
import scenes.Menu;
import scenes.TetrisReview;

/**
 * Created by Антон on 20.06.2016.
 */
public class MainMenuButtons extends MenuButtons {

    public MainMenuButtons(MainGame game) {
        super(game);
    }

    @Override
    void createAndPositionButtons() {
        button_1 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 + 90, "REVIEW PRESIDENTS", 20);
        button_2 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "LEARN PRESIDENTS", 20);
        button_3 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "FULL GAME", 20);
        button_4 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "HIGH SCORE", 20);
        button_5 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 150, "SOUND SETTINGS", 20);

    }

    @Override
    void addAllListeners() {
        super.addAllListeners();

        button_1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new TetrisReview(game));
                return true;
            }
        });

        button_2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new LearnModeButtons(game)));
                return true;
            }
        });

        button_3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                GameManager.setFirstPresidentInRange(1);
                GameManager.setLastPresidentInRange(44);
                GameManager.quantityOfHints = 0;
                hideMenu_startNewScreen(new HorisontalTetris(game));
                return true;
            }
        });

        button_4.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
//                hideMenu_startNewScreen(new HorisontalTetris(game));
                return true;
            }
        });

        button_5.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new SoundsButtons(game)));
                return true;
            }
        });
    }

}