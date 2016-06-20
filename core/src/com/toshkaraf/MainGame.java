package com.toshkaraf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GameManager;
import huds.MainMenuButtons;
import scenes.Menu;

public class MainGame extends Game {
	SpriteBatch batch;


	@Override
	public void create () {
		new GameManager(); //initialize PRESIDENTS_ARRAY
		batch = new SpriteBatch();
		setScreen(new Menu(this, new MainMenuButtons(this)));
//	    setScreen(new HorisontalTetrisField(this,1,11));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}
}
