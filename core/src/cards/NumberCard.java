package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;

/**
 * Created by Антон on 07.06.2016.
 */
public class NumberCard implements Card {

    private int number;
    float x,y;
    Sprite card;
    BitmapFont bitmapFont;
    int positionFromBottom;

    public NumberCard(Sprite card, int numberOfPresident, int positionFromBottom) {
        this.number = numberOfPresident;
        this.card = card;
        this.positionFromBottom = positionFromBottom;
        card.flip(true,false);
        x = -300;
        y = positionFromBottom * card.getHeight();
        bitmapFont = new BitmapFont();
    }

    public boolean draw(Batch batch) {
        batch.draw(card,x,y);
        bitmapFont.draw(batch,Integer.toString(number),x+285, y+35);
        updatePushingCard();
        return (x == -280);
    }

    private void updatePushingCard() {
        if (x<=-280) x = x+GameInfo.STEP_FOR_TETRIS_X;
    }
}
