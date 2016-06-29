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
 * Created by Антон on 07.06.2016.
 */
public class NumberCard extends Table {

    public NumberCard(Sprite card, int numberOfPresident) {
        super();
        setBackground(new SpriteDrawable(card));
        setBounds(getX(), getY(), card.getWidth(), card.getHeight());
        setY((numberOfPresident - GameManager.firstPresidentInRange) * card.getHeight());
        this.right();
        if (numberOfPresident >= 10)
            add(new Label(Integer.toString(numberOfPresident + 1),
                    new Label.LabelStyle(GameInfo.CARD_FONT, Color.WHITE))).padRight(15);
        else
            add(new Label(Integer.toString(numberOfPresident + 1),
                    new Label.LabelStyle(GameInfo.CARD_FONT, Color.WHITE))).padRight(22);
    }
}
