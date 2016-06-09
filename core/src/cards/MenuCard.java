package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
    public boolean draw(Batch batch) {
        super.draw(batch);
        bitmapFont.draw(batch, text, x + 35, y + 35);
        updatePushingCard();
        return (x == finishX);
    }

    private void updatePushingCard() {
        if (x > finishX) x = x - GameInfo.STEP_FOR_TETRIS_X;
        if (x < finishX) x = x + GameInfo.STEP_FOR_TETRIS_X;
    }

}
