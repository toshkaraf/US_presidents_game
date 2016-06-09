package com.toshkaraf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helpers.GameManager;
import scenes.UsaPresidentTetrisHorisontalMove;
import scenes.UsaPresidentTetrisVerticalDrop;

public class MainGame extends Game {
	SpriteBatch batch;


	@Override
	public void create () {
		new GameManager(); //initialize PRESIDENTS_ARRAY
		batch = new SpriteBatch();
	    setScreen(new UsaPresidentTetrisHorisontalMove(this,1,44));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}
}
