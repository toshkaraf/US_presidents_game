package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;

/**
 * Created by Антон on 07.06.2016.
 */
public class NumberCard extends Card {

    public NumberCard(Sprite card, int numberOfPresident, int positionFromBottom) {
        super(card,numberOfPresident,positionFromBottom);
        x = -370;
    }

    @Override
    public boolean draw(Batch batch) {
        super.draw(batch);
        bitmapFont.draw(batch, Integer.toString(number), x + 360, y + 35);
        updatePushingCard();
        return (x == -355);
    }

//    @Override
    private void updatePushingCard() {
        if (x <= -355) x = x + GameInfo.STEP_FOR_TETRIS_X;
    }
}
