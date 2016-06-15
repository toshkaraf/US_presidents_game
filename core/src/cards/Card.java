package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import helpers.GameManager;

/**
 * Created by Антон on 07.06.2016.
 */
public class Card extends Button{
    int number;
    float x,y;
    Sprite card;
    BitmapFont bitmapFont;
    int positionFromBottom, finalPositionOfCard_X;
    boolean isCardPushed = false;
    boolean isCardPulled = false;
    boolean isPush = true;

    public Card (Sprite card){
        this.card = card;
        bitmapFont = new BitmapFont();
    }

    public Card(Sprite card, int numberOfPresident) {
        this.number = numberOfPresident;
        this.card = card;
        this.positionFromBottom = numberOfPresident-GameManager.firstPresidentInRange;
        bitmapFont = new BitmapFont();
        y = positionFromBottom * card.getHeight();
        setBounds(x, this.y, card.getWidth(), card.getHeight());
    }

    public boolean draw(Batch batch) {
        batch.draw(card, x, y);
        return (x <= finalPositionOfCard_X) ;
    }

    public boolean isCardPushed() {
        return isCardPushed;
    }

    public void setIsPush(boolean push) {
        this.isPush = push;
    }

    public boolean isCardPulled() {
        return isCardPulled;
    }

    public void setCardPulled(boolean cardPulled) {
        isCardPulled = cardPulled;
    }

    public void setCardPushed(boolean cardPushed) {
        isCardPushed = cardPushed;
    }

}
