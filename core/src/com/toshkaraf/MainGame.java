package com.toshkaraf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GameManager;
import scenes.HorisontalTetrisField;
import scenes.MainMenu;

public class MainGame extends Game {
	SpriteBatch batch;


	@Override
	public void create () {
		new GameManager(); //initialize PRESIDENTS_ARRAY
		batch = new SpriteBatch();
//		setScreen(new MainMenu(this));
	    setScreen(new HorisontalTetrisField(this,1,11));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}
}
