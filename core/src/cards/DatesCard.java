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

    public DatesCard(Sprite card, int numberOfPresident) {
        super();
        setName("date_of_"+numberOfPresident);
        int finalPositionOfCard_X = GameInfo.WORLD_WIDTH - (45 * (GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate() -
                GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate()) + 1);
        setBackground(new SpriteDrawable(card));
        setBounds(getX(), getY(), card.getWidth(), card.getHeight());
        setPosition(GameInfo.WORLD_WIDTH,(numberOfPresident-GameManager.firstPresidentInRange) * card.getHeight());
        this.left();
        add(new Label(GameManager.PRESIDENTS_ARRAY[numberOfPresident].getInitialDate() + "-" +
                GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFinalDate(),
                new Label.LabelStyle(MyFontGenerator.getFont("fonts/arial.ttf", 20), Color.WHITE))).padLeft(25);
        addAction(Actions.moveTo(finalPositionOfCard_X, getY(), 1f));
    }
}
