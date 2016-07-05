package huds;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.toshkaraf.MainGame;

import cards.NameCard;
import cards.NumberCard;
import helpers.GameManager;
import helpers.RenderModeAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 01.07.2016.
 */
public class DecoratorForReview extends DecoratorChooseFromAll {

    public DecoratorForReview(MainGame game) {
        super(game);

    }

    @Override
    void initPresidentArray() {
    }

    @Override
    void initCardsArrays() {
        for (int card = GameManager.firstPresidentInRange; card <= GameManager.lastPresidentInRange; card++) {
            numberCardsArray.addActor(new NumberCard(blueCard, card));

            final NameCard nameCard = new NameCard(blueCard, card);
            nameCard.addAction(moveTo(nameCard.getFinalPositionOfCard_X(), nameCard.getY(), 1f));

            nameCard.addListener(new InputListener() {
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    GameManager.currentRightPresident = nameCard.getNumberOfPresident();
                    if (GameManager.renderMode != GameManager.RenderMode.Portrait)
                        GameManager.renderMode = GameManager.RenderMode.Portrait;
                    else GameManager.renderMode = GameManager.RenderMode.PlayGame;
                    return true;
                }
            });

            presidentCardsArray.addActor(nameCard);
        }

        numberCardsArray.setBounds(numberCardsArray.getX(), numberCardsArray.getY(), numberCardsArray.getWidth(), numberCardsArray.getHeight());
        numberCardsArray.setPosition(-400, 0);
        numberCardsArray.addAction(sequence(moveTo(-355, 0, 1f), delay(1f), new RenderModeAction(GameManager.RenderMode.PlayGame)));

    }
}
