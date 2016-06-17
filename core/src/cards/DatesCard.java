package cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import helpers.GameInfo;
import helpers.GameManager;
import helpers.MyFontGenerator;

/**
 * Created by Антон on 09.06.2016.
 */
public class DatesCard extends Card {

    public DatesCard (Sprite card, int numberOfPresident) {
        super(card,numberOfPresident);
        finalPositionOfCard_X = GameInfo.WORLD_WIDTH - (45 * (GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate()-
                GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate())+1);
        x = GameInfo.WORLD_WIDTH;
        setPosition(getX(),getY());
        setBounds(getX(),getY(),getWidth(),getHeight());
        add(new Label(GameManager.PRESIDENTS_ARRAY[GameManager.currentWrightPresident].getInitialDate() + " " +
                GameManager.PRESIDENTS_ARRAY[GameManager.currentWrightPresident].getFinalDate(),
                new Label.LabelStyle(MyFontGenerator.getFont("fonts/arial.ttf", 20), Color.WHITE))).left();
        addAction(Actions.moveTo(finalPositionOfCard_X,getY(),5f));
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        batch.draw(card, getX(), getY(), getWidth(), getHeight());
        bitmapFont.draw(batch, GameManager.PRESIDENTS_ARRAY[number].getInitialDate() +
                " - " + GameManager.PRESIDENTS_ARRAY[number].getFinalDate(),x+35, y+35);
    }

//    public void act(float delta) {
//        if (isPush) {
//            if (x >= finalPositionOfCard_X) x = x - GameInfo.STEP_FOR_TETRIS_X;
//            setBounds(x, this.y, card.getWidth(), card.getHeight());
//        } else {
//            x = x + GameInfo.STEP_FOR_TETRIS_X;
//            setBounds(x, this.y, card.getWidth(), card.getHeight());
//            if (x ==  GameInfo.WORLD_WIDTH) isCardPulled = true;
//        }
//    }

//    public int getNumberPresident(){
//        return number;
//    }
}
