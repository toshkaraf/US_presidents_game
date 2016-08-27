package cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 09.06.2016.
 */
public class DatesCard extends Table {

    int finalPositionOfCard_X;

    public DatesCard(Sprite card, GameManager.TypeOfCard typeOfCard, int numberOfPresident) {
        super();

        card.setSize(GameInfo.CARD_WIDTH,GameInfo.CARD_HEIGHT);

        if (typeOfCard == GameManager.TypeOfCard.BlueDate)
            setName("blue_date_of_" + numberOfPresident);
        else setName("red_date_of_" + numberOfPresident);

        if (GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate() - GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate() > 1) {
            finalPositionOfCard_X = GameInfo.WORLD_WIDTH - (26 * (GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate() -
                    GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate() + 1));
            setBackground(new SpriteDrawable(card));
            this.left();
            add(new Label(String.valueOf(GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate()),
                    new Label.LabelStyle(GameInfo.DATE_FONT, Color.WHITE))).padLeft(25);
        } else {
            Label label = new Label(String.valueOf(GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate()),
                    new Label.LabelStyle(GameInfo.DATE_FONT, Color.BLACK));
            finalPositionOfCard_X = (int) (GameInfo.WORLD_WIDTH - (26 * (GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate() -
                                GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate() + 1))- label.getWidth())-10;
            add(label).padRight(5);
            add(new Image(new SpriteDrawable(card)));
        }
        setBounds(getX(), getY(), card.getWidth(), card.getHeight());
        setPosition(GameInfo.WORLD_WIDTH, (numberOfPresident - GameManager.firstPresidentInRange) * card.getHeight());
    }

    public void push() {
        addAction(Actions.moveTo(finalPositionOfCard_X, getY(), 1f));
    }

    public int getFinalPositionOfCard_X() {
        return finalPositionOfCard_X;
    }
}
