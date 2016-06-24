package cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import helpers.GameInfo;
import helpers.GameManager;
import helpers.MyFontGenerator;

/**
 * Created by Антон on 09.06.2016.
 */
public class DatesCard extends Table {

    int finalPositionOfCard_X;

    public DatesCard(Sprite card, GameManager.TypeOfCard typeOfCard, int numberOfPresident) {
        super();
        if (typeOfCard == GameManager.TypeOfCard.BlueDate)
            setName("blue_date_of_" + numberOfPresident);
        else setName("red_date_of_"+numberOfPresident);

        finalPositionOfCard_X = GameInfo.WORLD_WIDTH - (45 * (GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate() -
                GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate()) + 1);

        setBackground(new SpriteDrawable(card));
        setBounds(getX(), getY(), card.getWidth(), card.getHeight());
        setPosition(GameInfo.WORLD_WIDTH,(numberOfPresident-GameManager.firstPresidentInRange) * card.getHeight());
        this.left();
        add(new Label(GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate() + "-" +
                GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate(),
                new Label.LabelStyle(MyFontGenerator.getFont("fonts/arial.ttf", 20), Color.WHITE))).padLeft(25);
        push();
    }

    public void push(){
        addAction(Actions.moveTo(finalPositionOfCard_X, getY(), 1f));
    }

    public int getFinalPositionOfCard_X (){
        return  finalPositionOfCard_X;
    }
}
