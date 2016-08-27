package cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 09.06.2016.
 */
public class NameCard extends Table {

    int finalPositionOfCard_X;
    int numberOfPresident;

    public NameCard(Sprite card, int numberOfPresident) {
        super();
        this.numberOfPresident = numberOfPresident;

        setName("name_of_" + numberOfPresident);
        this.left();
        Label label = new Label(GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFirstName() +
                " " + GameManager.PRESIDENTS_ARRAY[numberOfPresident].getLastName(),
                new Label.LabelStyle(GameInfo.NAME_FONT, Color.BLUE));
        add(label).padRight(10);
        DatesCard datesCard = new DatesCard(card, GameManager.TypeOfCard.BlueDate, numberOfPresident);
        add(datesCard);
        finalPositionOfCard_X = (int) (datesCard.getFinalPositionOfCard_X() - label.getWidth() - 10);
        setPosition(GameInfo.WORLD_WIDTH, (numberOfPresident - GameManager.firstPresidentInRange) * card.getHeight());
        setBounds(getX(), getY(), 1000, card.getHeight());
        setTouchable(Touchable.enabled);
    }

    public int getFinalPositionOfCard_X() {
        return finalPositionOfCard_X;
    }

    public int getNumberOfPresident() {
        return numberOfPresident;
    }
}