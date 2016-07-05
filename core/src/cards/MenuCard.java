package cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import helpers.GameInfo;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

/**
 * Created by Антон on 14.06.2016.
 */
public class MenuCard extends Stack {

    public MenuCard(Sprite card, int x, int y, String text, int fontSize) {
        super();
        add(new Image(new SpriteDrawable(card)));
        setBounds(getX(), getY(), card.getWidth(), card.getHeight());
        setPosition(x, y);
        Label label = new Label(text, new Label.LabelStyle(GameInfo.DATE_FONT, Color.WHITE));
        label.setAlignment(Align.center);
        add(label);
    }

    public void move(int targetX) {
        addAction(moveTo(targetX, getY(), .5f));
    }
}
