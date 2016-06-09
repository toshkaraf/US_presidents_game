package com.toshkaraf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.toshkaraf.MainGame;

import helpers.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","seconduser");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

//		config.width = GameInfo.WORLD_WIDTH;
//		config.height = GameInfo.WORLD_HEIGHT;

//		config.width = 480;
//		config.height = 800;

		config.width = 800;
		config.height = 480;

		new LwjglApplication(new MainGame(), config);
	}
}
