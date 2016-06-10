package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import helpers.GameInfo;

/**
 * Created by Антон on 07.06.2016.
 */
public class Card extends Button{
    int number;
    float x,y;
    Sprite card;
    BitmapFont bitmapFont;
    int positionFromBottom;

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
        return false;
    }

}
