package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import java.util.Random;

import cards.DatesCard;
import cards.NumberCard;
import cards.PresidentNameCard;
import helpers.GameInfo;
import helpers.GameManager;
import helpers.RenderModeAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 10.06.2016.
 */
public class DecoratorFieldWIthCards {


    private SpriteBatch batch;
    private MainGame game;
    private Stage stage;
    private Sprite blueCard, redCard;
    private Sprite backgroundSprite;
    private Viewport gameViewport;
    private Group presidentCardsArray;
    private GameManager.TypeOfCard[] presidentsArray = new GameManager.TypeOfCard[44];
    private Group numberCardsArray;
    //    private Array<Card> presidentCardsArray;
    OrthographicCamera camera;


    public DecoratorFieldWIthCards(MainGame game) {

        this.game = game;
        numberCardsArray = new Group();
        presidentCardsArray = new Group();

        redCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president_red.png")));
        blueCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president.png")));
        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground_game.jpg")),
                0, 0, GameInfo.WORLD_WIDTH, Math.round(blueCard.getHeight() * (GameManager.lastPresidentInRange - GameManager.firstPresidentInRange + 1)));

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT);
        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);
        camera.position.set(backgroundSprite.getWidth() / 2, backgroundSprite.getHeight() / 2, 0);

        stage = new Stage(gameViewport, batch);

        final Actor background = new Actor() {
            public void draw(Batch batch, float alpha) {
                batch.draw(backgroundSprite, 0, 0);
            }
        };

        stage.addActor(background);
        stage.addActor(numberCardsArray);
        stage.addActor(presidentCardsArray);
        initPresidentArray();
        initCardsArrays();

    }

    private void initPresidentArray() {
        for (int president = GameManager.firstPresidentInRange; president <= GameManager.lastPresidentInRange; president++)
            this.presidentsArray[president] = GameManager.TypeOfCard.BlueDate;
    }

    private void initCardsArrays() {
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
        if (GameManager.quantityOfHints >= GameManager.getPresidentsListForQuestions().size - 1)
            quantityOfHints = GameManager.getPresidentsListForQuestions().size - 1;
        Array<Integer> hintArray = new Array<Integer>(GameManager.quantityOfHints - 1);
        Random random = new Random();
        int hint;
        for (int h = 0; h <= quantityOfHints - 1; h++) {
            do
                hint = GameManager.firstPresidentInRange + GameManager.getPresidentsListForQuestions().get(random.nextInt(GameManager.getPresidentsListForQuestions().size));
            while (hintArray.contains(hint, true) || hint == GameManager.currentRightPresident);
            presidentsArray[hint] = GameManager.TypeOfCard.RedDate;
        }
        // add right answer
        presidentsArray[GameManager.currentRightPresident] = GameManager.TypeOfCard.RedDate;
    }

//    public void pullHintCards() {
//        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
//            if (presidentsArray[a] == GameManager.TypeOfCard.RedDate) {
//                DatesCard actor = (DatesCard) presidentCardsArray.findActor("blue_date_of_" + a);
//                actor.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, actor.getY(), 1f), new RenderModeAction(GameManager.RenderMode.PushNewHints)));
//            }
//        }
//    }

    public void pushHintCards() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == GameManager.TypeOfCard.RedDate) {
                DatesCard actor = (DatesCard) presidentCardsArray.findActor("blue_date_of_" + a);
                actor.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, actor.getY(), 1f), delay(1f), new RenderModeAction(GameManager.RenderMode.SetNewPlayer)));
                DatesCard newActor = new DatesCard(redCard, GameManager.TypeOfCard.RedDate, a);
                presidentCardsArray.addActor(newActor);
            }
        }
    }

    public void pushRightNameCardIfRightAnswer() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == GameManager.TypeOfCard.RedDate) {
                DatesCard pullDateCard = (DatesCard) presidentCardsArray.findActor("red_date_of_" + a);
                pullDateCard.addAction(moveTo(GameInfo.WORLD_WIDTH, pullDateCard.getY(), 1f));
                if (a != GameManager.currentRightPresident) {
                    DatesCard pushDateCard = (DatesCard) presidentCardsArray.findActor("blue_date_of_" + a);
                    pushDateCard.push();
                } else {
                    PresidentNameCard rightNameCard = new PresidentNameCard(blueCard, GameManager.currentRightPresident);
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
                DatesCard pullDateCard = (DatesCard) presidentCardsArray.findActor("red_date_of_" + a);
                pullDateCard.addAction(moveTo(GameInfo.WORLD_WIDTH, pullDateCard.getY(), 1f));
                if (a != GameManager.currentRightPresident) {
                    DatesCard pushDateCard = (DatesCard) presidentCardsArray.findActor("blue_date_of_" + a);
                    pushDateCard.push();
                } else {
                    PresidentNameCard rightNameCard = new PresidentNameCard(blueCard, GameManager.currentRightPresident);
                    rightNameCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH - 370, rightNameCard.getY(), 1f), delay(3f),
                            moveTo(GameInfo.WORLD_WIDTH, rightNameCard.getY(), 1f),
                            new RenderModeAction(GameManager.RenderMode.MoveCamToStartPosition)));
                    presidentCardsArray.addActor(rightNameCard); //push right name card
                }
            }
        }
        DatesCard pushRightDateCard = (DatesCard) presidentCardsArray.findActor("blue_date_of_" + GameManager.currentRightPresident);
        pushRightDateCard.addAction(delay(4f));
        pushRightDateCard.push();
    }

    public void renewData(boolean answer){
        clearPresidentCardsArrayFromRedCards();
        if (!answer) presidentCardsArray.findActor("name_of_" + GameManager.currentRightPresident).remove();
        initPresidentArray();
    }

    private void clearPresidentCardsArrayFromRedCards() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == GameManager.TypeOfCard.RedDate)
                presidentCardsArray.findActor("red_date_of_" + a).remove();
        }
//        presidentCardsArray.findActor("red_date_of_" + GameManager.currentRightPresident).remove();
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
