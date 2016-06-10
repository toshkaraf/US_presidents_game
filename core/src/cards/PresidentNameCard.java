package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 09.06.2016.
 */
public class PresidentNameCard extends Card {

    public PresidentNameCard(Sprite card, int numberOfPresident, int positionFromBottom) {
        super(card,numberOfPresident,positionFromBottom);
        x = 800;
    }

    @Override
    public boolean draw(Batch batch) {
        super.draw(batch);
        bitmapFont.draw(batch, GameManager.PRESIDENTS_ARRAY.get(number).getFirstName() +
                " " + GameManager.PRESIDENTS_ARRAY.get(number).getLastName(),x+35, y+35);
        updatePushingCard();
        return (x == 440);
    }

    private void updatePushingCard() {
        if (x>=440) x = x-GameInfo.STEP_FOR_TETRIS_X;
    }
}