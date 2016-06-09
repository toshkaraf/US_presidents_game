package com.toshkaraf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import scenes.UsaPresidentTetrisHorisontalMove;
import scenes.UsaPresidentTetrisVerticalDrop;

public class MainGame extends Game {
	SpriteBatch batch;


	@Override
	public void create () {
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
