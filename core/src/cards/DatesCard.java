package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 09.06.2016.
 */
public class DatesCard extends Card {

    public DatesCard (Sprite card, int numberOfPresident, int positionFromBottom) {
        super(card,numberOfPresident,positionFromBottom);
        finalPositionOfCard_X = GameInfo.WORLD_WIDTH - (45 * (GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate()-
                GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate())+1);
        x = GameInfo.WORLD_WIDTH;
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        if (super.draw(batch)) isCardDone = true;
        bitmapFont.draw(batch, GameManager.PRESIDENTS_ARRAY[number].getInitialDate() +
                " - " + GameManager.PRESIDENTS_ARRAY[number].getFinalDate(),x+35, y+35);
    }

    public void act(float delta) {
        if (x>=finalPositionOfCard_X) x = x - GameInfo.STEP_FOR_TETRIS_X;
    }
}
