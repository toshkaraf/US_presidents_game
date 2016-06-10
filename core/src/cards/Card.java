package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * Created by Антон on 07.06.2016.
 */
public class Card extends Button{
    int number;
    float x,y;
    Sprite card;
    BitmapFont bitmapFont;
    int positionFromBottom, finalPositionOfCard_X;
    boolean isCardDone = false;

    public Card (Sprite card){
        this.card = card;
        bitmapFont = new BitmapFont();
    }

    public Card(Sprite card, int numberOfPresident, int positionFromBottom) {
        this.number = numberOfPresident;
        this.card = card;
        this.positionFromBottom = positionFromBottom;
        bitmapFont = new BitmapFont();
        y = positionFromBottom * card.getHeight();
    }

    public boolean draw(Batch batch) {
        batch.draw(card, x, y);
        return (x <= finalPositionOfCard_X) ;
    }

    public boolean isCardDone() {
        return isCardDone;
    }

}
