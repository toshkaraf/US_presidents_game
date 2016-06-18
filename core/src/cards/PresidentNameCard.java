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
public class PresidentNameCard extends Table {

    public PresidentNameCard(Sprite card, int numberOfPresident) {
        super();
        setBackground(new SpriteDrawable(card));
        setBounds(getX(), getY(), card.getWidth(), card.getHeight());
        setPosition(GameInfo.WORLD_WIDTH,(numberOfPresident - GameManager.firstPresidentInRange) * card.getHeight());
        this.left();
        add(new Label(GameManager.PRESIDENTS_ARRAY[numberOfPresident].getFirstName() +
                        " " + GameManager.PRESIDENTS_ARRAY[numberOfPresident].getLastName(),
                new Label.LabelStyle(MyFontGenerator.getFont("fonts/arial.ttf", 20), Color.WHITE))).padLeft(22);
    }

}