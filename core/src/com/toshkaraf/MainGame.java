package com.toshkaraf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import helpers.GameInfo;
import helpers.GameManager;
import huds.MainMenuButtons;
import scenes.Menu;

public class MainGame extends Game {

    SpriteBatch batch;
    private final AssetManager assetManager = new AssetManager();

    @Override
    public void create() {
        initAssetManager();
        new GameInfo(this);
        GameManager.getInstance().initializeGameData();
        batch = new SpriteBatch();
        setScreen(new Menu(this, new MainMenuButtons(this)));

    }

    @Override
    public void render() {
        super.render();
    }

    private void initAssetManager() {
//        BitmapFontLoader.BitmapFontParameter bitmapFontParameter =
//                new BitmapFontLoader.BitmapFontParameter();
//        bitmapFontParameter.atlasName = "fonts/CardArialFont.atlas";
//        assetManager.load("fonts/CardArialFont.fnt", BitmapFont.class, bitmapFontParameter);
        assetManager.load("ready_texture/president_assets.atlas", TextureAtlas.class);
        assetManager.finishLoading();
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
