package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import helpers.GameInfo;

/**
 * Created by Антон on 09.06.2016.
 */
public class MenuCard extends Card {

    int finishX;
    String text;

    public MenuCard(Sprite card, int startX, int finishX, int y, String text) {
        super(card);
        x = startX;
        this.y = y;
        this.finishX = finishX;
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch);
        bitmapFont.draw(batch, text, x + 35, y + 35);
    }

    @Override
    public void act(float delta) {
        if (x > finishX) x = x - GameInfo.STEP_FOR_MENU;
        if (x < finishX) x = x + GameInfo.STEP_FOR_MENU;
        setBounds(x,this.y,card.getWidth(),card.getHeight());
    }
}
