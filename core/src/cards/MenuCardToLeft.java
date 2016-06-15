package cards;

import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;

/**
 * Created by Антон on 14.06.2016.
 */
public class MenuCardToLeft extends MenuCardToRight {

    public MenuCardToLeft(Sprite card, int startX, int finishX, int y, String text) {
        super(card, startX, finishX, y, text);
    }

    @Override
    public void act(float delta) {
        if (isPush) {
            if (x >= finishX) {
                x = x - GameInfo.STEP_FOR_MENU;
                setBounds(x, this.y, card.getWidth(), card.getHeight());
            } else setCardPushed(true);
        } else {
            if (x >= -400) x = x - GameInfo.STEP_FOR_MENU;
            else isCardPulled = true;
        }
    }
}
