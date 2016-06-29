package cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import helpers.GameInfo;
import helpers.GameManager;
import helpers.MyFontGenerator;

/**
 * Created by Антон on 09.06.2016.
 */
public class NameCard extends Table {

    public NameCard(Sprite card, int numberOfPresident) {
        super();
        setName("name_of_" + numberOfPresident);
        setBackground(new SpriteDrawable(card));
        setBounds(getX(), getY(), card.getWidth(), card.getHeight());
        setPosition(GameInfo.WORLD_WIDTH, (numberOfPresident - GameManager.firstPresidentInRange) * card.getHeight());
        this.left();
        add(new Label(GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFirstName() +
                " " + GameManager.PRESIDENTS_ARRAY[numberOfPresident].getLastName(),
                new Label.LabelStyle(GameInfo.CARD_FONT, Color.WHITE))).padLeft(22);
    }

}