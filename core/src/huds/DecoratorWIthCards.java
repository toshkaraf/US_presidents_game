package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import java.util.Random;

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
 * Created by Антон on 10.06.2016.
 */
public class DecoratorWIthCards {


    private SpriteBatch batch;
    private MainGame game;
    private Stage stage;
    Sprite blueCard;
    Sprite redCard;
    Sprite purpleCard;
    private Sprite backgroundSprite;
    private Viewport gameViewport;
    Group presidentCardsArray;
    GameManager.TypeOfCard[] presidentsArray = new GameManager.TypeOfCard[44];
    Group numberCardsArray;
    OrthographicCamera camera;


    public DecoratorWIthCards(MainGame game) {

        this.game = game;
        numberCardsArray = new Group();
        presidentCardsArray = new Group();
        redCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president_red.png")));
        blueCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president.png")));
        purpleCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president_purple.png")));

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT);
        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);

        stage = new Stage(gameViewport, batch);
        stage.addActor(numberCardsArray);
        stage.addActor(presidentCardsArray);
        initPresidentArray();
        initCardsArrays();
    }

    void initPresidentArray() {
        for (int president = GameManager.firstPresidentInRange; president <= GameManager.lastPresidentInRange; president++)
            this.presidentsArray[president] = GameManager.TypeOfCard.BlueDate;
    }

    void initCardsArrays() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            numberCardsArray.addActor(new NumberCard(blueCard, a));
            presidentCardsArray.addActor(new DatesCard(blueCard, GameManager.TypeOfCard.BlueDate, a));
        }
        numberCardsArray.setBounds(numberCardsArray.getX(), numberCardsArray.getY(), numberCardsArray.getWidth(), numberCardsArray.getHeight());
        numberCardsArray.setPosition(-400, 0);
        numberCardsArray.addAction(sequence(moveTo(-355, 0, 1f), delay(1f), new RenderModeAction(GameManager.RenderMode.Portrait)));
    }

    public void generateHints() {
        int quantityOfHints = GameManager.quantityOfHints;
        if (GameManager.quantityOfHints >= GameManager.getPresidentsListForQuestions().size)
            quantityOfHints = GameManager.getPresidentsListForQuestions().size;
        Array<Integer> hintArray = new Array<Integer>(quantityOfHints);
        Array<Integer> arrayToChooseRandomHints = GameManager.getCloneOfPresidentsListForQuestions();
        Random random = new Random();
        int hint;
        for (int h = 0; h <= quantityOfHints - 1; h++) {
            do
                hint = arrayToChooseRandomHints.removeIndex(random.nextInt(arrayToChooseRandomHints.size));
            while (hintArray.contains(hint, true));
            hintArray.add(hint);
            presidentsArray[hint] = GameManager.TypeOfCard.RedDate;
        }
        // add right answer
        presidentsArray[GameManager.currentRightPresident] = GameManager.TypeOfCard.RedDate;
    }

    public void pushHintCards() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == GameManager.TypeOfCard.RedDate) {
                DatesCard actor = presidentCardsArray.findActor("blue_date_of_" + a);
                actor.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, actor.getY(), 1f), delay(1f), new RenderModeAction(GameManager.RenderMode.SetNewPlayer)));
                DatesCard newActor = new DatesCard(redCard, GameManager.TypeOfCard.RedDate, a);
                presidentCardsArray.addActor(newActor);
            }
        }
    }

    public void pushRightNameCardIfRightAnswer() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == GameManager.TypeOfCard.RedDate) {
                DatesCard pullDateCard = presidentCardsArray.findActor("red_date_of_" + a);
                pullDateCard.addAction(moveTo(GameInfo.WORLD_WIDTH, pullDateCard.getY(), 1f));
                if (a != GameManager.currentRightPresident) {
                    DatesCard pushDateCard = presidentCardsArray.findActor("blue_date_of_" + a);
                    pushDateCard.push();
                } else {
                    NameCard rightNameCard = new NameCard(blueCard, GameManager.currentRightPresident);
                    rightNameCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH - 370, rightNameCard.getY(), 1f), delay(3f),
                            new RenderModeAction(GameManager.RenderMode.MoveCamToStartPosition)));
                    presidentCardsArray.addActor(rightNameCard); //push right name card
                }
            }
        }
    }

    public void showRightNameCardIfWrongAnswer() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == GameManager.TypeOfCard.RedDate) {
                DatesCard pullDateCard = presidentCardsArray.findActor("red_date_of_" + a);
                pullDateCard.addAction(moveTo(GameInfo.WORLD_WIDTH, pullDateCard.getY(), 1f));
                if (a != GameManager.currentRightPresident) {
                    DatesCard pushDateCard = presidentCardsArray.findActor("blue_date_of_" + a);
                    pushDateCard.push();
                } else {
                    NameCard rightNameCard = new NameCard(purpleCard, GameManager.currentRightPresident);
                    rightNameCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH - 370, rightNameCard.getY(), 1f), delay(3f),
                            moveTo(GameInfo.WORLD_WIDTH, rightNameCard.getY(), 1f),
                            new RenderModeAction(GameManager.RenderMode.MoveCamToStartPosition)));
                    presidentCardsArray.addActor(rightNameCard); //push right name card
                }
            }
        }
        // return blue date card
        DatesCard pushRightDateCard = presidentCardsArray.findActor("blue_date_of_" + GameManager.currentRightPresident);
        pushRightDateCard.addAction(delay(3f));
        pushRightDateCard.push();
    }

    public void renewData(boolean answer) {
        clearPresidentCardsArrayFromRedCards();
        if (!answer)
            presidentCardsArray.findActor("name_of_" + GameManager.currentRightPresident).remove();
        initPresidentArray();
    }

    private void clearPresidentCardsArrayFromRedCards() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == GameManager.TypeOfCard.RedDate)
                presidentCardsArray.findActor("red_date_of_" + a).remove();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public Sprite getBackgroundSprite() {
        return backgroundSprite;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
