package com.toshkaraf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GameManager;
import huds.LearnModeButtons;
import huds.MainMenuButtons;
import scenes.MainMenu1;
import scenes.Menu;
import scenes.NewMainMenu;
import scenes.SecondMenu2;

public class MainGame extends Game {

    SpriteBatch batch;
    private static MainGame ourInstance = new MainGame();
    public enum KindsOfMenu {mainMenu, learnMenu}

    private static Menu mainMenu;
    private static Menu learnMenu;


    @Override
    public void create() {
        new GameManager(); //initialize PRESIDENTS_ARRAY
        batch = new SpriteBatch();
        learnMenu = new Menu(this, new LearnModeButtons(this));
//        learnMenu = new SecondMenu2(this);

        mainMenu = new Menu(this, new MainMenuButtons(this));
//        mainMenu = new NewMainMenu(this);

        setScreen(mainMenu);
    }

    public void setNewScreen(KindsOfMenu menu) {
        switch (menu) {
            case mainMenu:
                setScreen(mainMenu);
                break;
            case learnMenu:
                setScreen(learnMenu);
                break;
        }
    }

    public static MainGame getInstance() {
        return ourInstance;
    }

    @Override
    public void render() {
        super.render();
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }
}
