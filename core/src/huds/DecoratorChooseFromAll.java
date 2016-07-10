package huds;

import com.toshkaraf.MainGame;

import cards.DatesCard;
import cards.NameCard;
import cards.NumberCard;
import helpers.GameInfo;
import helpers.GameManager;
import helpers.RenderModeAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 24.06.2016.
 */
public class DecoratorChooseFromAll extends DecoratorWIthCards {
    public DecoratorChooseFromAll(MainGame game) {
        super(game);
    }

    @Override
    void initPresidentArray() {
        for (int president = GameManager.firstPresidentInRange; president <= GameManager.lastPresidentInRange; president++)
            this.presidentsArray[president] = GameManager.TypeOfCard.RedDate;
    }

    @Override
    void initCardsArrays() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            numberCardsArray.addActor(new NumberCard(blueCard, a));
            DatesCard dateCard = new DatesCard(redCard, GameManager.TypeOfCard.RedDate, a);
            dateCard.push();
            presidentCardsArray.addActor(dateCard);
        }
        numberCardsArray.setBounds(numberCardsArray.getX(), numberCardsArray.getY(), numberCardsArray.getWidth(), numberCardsArray.getHeight());
        numberCardsArray.setPosition(-400, 0);
        numberCardsArray.addAction(sequence(moveTo(-355, 0, 1f), delay(1f), new RenderModeAction(GameManager.RenderMode.Portrait)));
    }

    @Override
    public void generateHints() {
    }

    @Override
    public void pushHintCards() {
        GameManager.renderMode = GameManager.RenderMode.PlayGame;
    }

    @Override
    public void pushRightNameCardIfRightAnswer() {
        DatesCard pullDateCard = presidentCardsArray.findActor("red_date_of_" + GameManager.currentRightPresident);
        pullDateCard.addAction(moveTo(GameInfo.WORLD_WIDTH, pullDateCard.getY(), 1f));
        NameCard rightNameCard = new NameCard(blueCard, GameManager.currentRightPresident);
        rightNameCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH - 370, rightNameCard.getY(), 1f), delay(3f),
                new RenderModeAction(GameManager.RenderMode.MoveCamToStartPosition)));
        presidentCardsArray.addActor(rightNameCard); //push right name card
    }

    @Override
    public void showRightNameCardIfWrongAnswer() {
        DatesCard pullDateCard = presidentCardsArray.findActor("red_date_of_" + GameManager.currentRightPresident);
        pullDateCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, pullDateCard.getY(), 1f), delay(3f),
                moveTo(pullDateCard.getFinalPositionOfCard_X(), pullDateCard.getY(), 1f) ));
        NameCard rightNameCard = new NameCard(purpleCard, GameManager.currentRightPresident);
        rightNameCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH - 370, rightNameCard.getY(), 1f), delay(3f),
                moveTo(GameInfo.WORLD_WIDTH, rightNameCard.getY(), 1f),
                new RenderModeAction(GameManager.RenderMode.MoveCamToStartPosition)));
        presidentCardsArray.addActor(rightNameCard); //push right name card
    }

    @Override
    public void renewData(boolean answer) {
        if (!answer)
            presidentCardsArray.findActor("name_of_" + GameManager.currentRightPresident).remove();
        else
            presidentCardsArray.findActor("red_date_of_" + GameManager.currentRightPresident).remove();
        initPresidentArray();
    }

}




