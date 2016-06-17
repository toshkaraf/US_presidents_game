package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 07.06.2016.
 */
public class NumberCard extends Card {

    public NumberCard(Sprite card, int numberOfPresident) {
        super(card, numberOfPresident);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(card, getX(), getY(), getWidth(), getHeight());
        bitmapFont.draw(batch, Integer.toString(number+1), getX() + 360, getY() + 35);
    }
}
