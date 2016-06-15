package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 09.06.2016.
 */
public class DatesCard extends Card {

    public DatesCard (Sprite card, int numberOfPresident) {
        super(card,numberOfPresident);
        finalPositionOfCard_X = GameInfo.WORLD_WIDTH - (45 * (GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate()-
                GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate())+1);
        x = GameInfo.WORLD_WIDTH;
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        if (super.draw(batch)) isCardPushed = true;
        bitmapFont.draw(batch, GameManager.PRESIDENTS_ARRAY[number].getInitialDate() +
                " - " + GameManager.PRESIDENTS_ARRAY[number].getFinalDate(),x+35, y+35);
    }

    public void act(float delta) {
        if (isPush) {
            if (x >= finalPositionOfCard_X) x = x - GameInfo.STEP_FOR_TETRIS_X;
            setBounds(x, this.y, card.getWidth(), card.getHeight());
        } else {
            x = x + GameInfo.STEP_FOR_TETRIS_X;
            setBounds(x, this.y, card.getWidth(), card.getHeight());
            if (x ==  GameInfo.WORLD_WIDTH) isCardPulled = true;
        }
    }

    public int getNumberPresident(){
        return number;
    }
}
