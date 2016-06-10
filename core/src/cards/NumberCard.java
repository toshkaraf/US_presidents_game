package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;
import helpers.GameManager;
import huds.DecoratorFieldWIthCards;

/**
 * Created by Антон on 07.06.2016.
 */
public class NumberCard extends Card {

    final static int finalPositionOfNumberCard_X = -355;

    public NumberCard(Sprite card, int numberOfPresident, int positionFromBottom) {
        super(card, numberOfPresident, positionFromBottom);
        x = -370;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (super.draw(batch)) GameManager.counterOfDoneNumberCards++; //increase counter of NumberCard pushed to the end
        bitmapFont.draw(batch, Integer.toString(number), x + 360, y + 35);
    }

    @Override
    public void act(float delta) {
        if (x <= finalPositionOfNumberCard_X) x = x + GameInfo.STEP_FOR_TETRIS_X;
    }
}
