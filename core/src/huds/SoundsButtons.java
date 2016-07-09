package huds;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.toshkaraf.MainGame;

import cards.MenuCard;
import helpers.GameInfo;
import helpers.GameManager;
import scenes.Menu;

/**
 * Created by Антон on 09.07.2016.
 */
public class SoundsButtons extends MenuButtons {

    public SoundsButtons(MainGame game) {
        super(game);
    }

    @Override
    void addActorsToStage() {
        stage.addActor(button_2);
        stage.addActor(button_3);
        stage.addActor(button_4);
    }

    @Override
    void createAndPositionButtons() {
        button_2 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 + 30, "On / off music", 20);
        button_3 = new MenuCard(redCard, -400, GameInfo.WORLD_HEIGHT / 2 - 30, "On / off the other sounds", 20);
        button_4 = new MenuCard(blueCard, 800, GameInfo.WORLD_HEIGHT / 2 - 90, "Main menu", 20);
    }

    @Override
    void addAllListeners() {
        super.addAllListeners();

        button_2.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                if (GameManager.getInstance().gameData.isMusicOn()) {
                    GameManager.getInstance().gameData.setMusicOn(false);
                    GameManager.getInstance().stopMusic();
                } else {
                    GameManager.getInstance().gameData.setMusicOn(true);
                    GameManager.getInstance().playMusic();
                }
                GameManager.getInstance().saveData();
                return true;
            }
        });

        button_3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                if (GameManager.getInstance().gameData.isSounds()) {
                    GameManager.getInstance().gameData.setSounds(false);
                    GameManager.getInstance().disposeSounds();
                } else {
                    GameManager.getInstance().gameData.setSounds(true);
                    GameManager.getInstance().initSounds();
                }
                return true;

            }
        });

        button_4.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons) {
                hideMenu_startNewScreen(new Menu(game, new MainMenuButtons(game)));
                return true;
            }
        });
    }

    @Override
    void addShowActions() {
        button_2.move(GameInfo.WORLD_WIDTH / 2 - 198 - 50);
        button_3.move(GameInfo.WORLD_WIDTH / 2 - 198);
        button_4.move(GameInfo.WORLD_WIDTH / 2 - 198 + 50);
    }

    @Override
    void addHideActions() {
        button_2.move(-400);
        button_3.move(GameInfo.WORLD_WIDTH);
        button_4.move(-400);
    }
}
