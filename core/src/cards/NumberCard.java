package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 07.06.2016.
 */
public class NumberCard extends Card {

    public NumberCard(Sprite card, int numberOfPresident, int positionFromBottom) {
        super(card, numberOfPresident, positionFromBottom);
        x = -400;
        finalPositionOfCard_X = -355;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (draw(batch)) {
            isCardDone = true;
            GameManager.counterOfPushedCards++; //increase counter of pushed to the end
        }
        bitmapFont.draw(batch, Integer.toString(number+1), x + 360, y + 35);
    }

    @Override
    public boolean draw(Batch batch) {
        batch.draw(card, x, y);
        return (x >= finalPositionOfCard_X) ;
    }

    @Override
    public void act(float delta) {
        if (x <= finalPositionOfCard_X) x = x + GameInfo.STEP_FOR_TETRIS_X;
    }
}
