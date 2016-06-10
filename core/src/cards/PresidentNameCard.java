package cards;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 09.06.2016.
 */
public class PresidentNameCard extends Card {

    private final int finalPositionOfCard_X = 440;

    public PresidentNameCard(Sprite card, int numberOfPresident, int positionFromBottom) {
        super(card, numberOfPresident, positionFromBottom);
        x = 800;
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        super.draw(batch);
        bitmapFont.draw(batch, GameManager.PRESIDENTS_ARRAY.get(number).getFirstName() +
                " " + GameManager.PRESIDENTS_ARRAY.get(number).getLastName(), x + 35, y + 35);
    }


    public void act(float delta) {
        if (x >= finalPositionOfCard_X) x = x - GameInfo.STEP_FOR_TETRIS_X;
    }
}