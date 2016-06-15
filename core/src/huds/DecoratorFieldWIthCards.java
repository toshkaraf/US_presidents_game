package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import java.util.Random;

import cards.Card;
import cards.DatesCard;
import cards.NumberCard;
import cards.PresidentNameCard;
import helpers.CardsStage;
import helpers.GameInfo;
import helpers.GameManager;
import scenes.TetrisReview;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 10.06.2016.
 */
public class DecoratorFieldWIthCards {


    private SpriteBatch batch;
    private MainGame game;
    private CardsStage stage;
    private Sprite blueCard, redCard;
    private Sprite backgroundSprite;
    private Viewport gameViewport;
    private Group presidentCardsArray;
    private TypeOfCard[] presidentsArray = new TypeOfCard[44];
    private Array<NumberCard> numberCardsArray;
    //    private Array<Card> presidentCardsArray;
    OrthographicCamera camera;

    enum TypeOfCard {BlueDate, RedDate, BlueName, RedName}


    public DecoratorFieldWIthCards(MainGame game) {

        this.game = game;
        numberCardsArray = new Array<NumberCard>();
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

        stage = new CardsStage(gameViewport, batch);

        final Actor background = new Actor() {
            public void draw(Batch batch, float alpha) {
                batch.draw(backgroundSprite, 0, 0);
            }
        };

        stage.addActor(background);
        stage.addActor(presidentCardsArray);
        initPresidentArray();
        initCardsArrays();

    }

    private void initPresidentArray() {
        for (int president = GameManager.firstPresidentInRange; president <= GameManager.lastPresidentInRange; president++)
            this.presidentsArray[president] = TypeOfCard.BlueDate;
    }


    private void initCardsArrays() {
        int i = 0;
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            numberCardsArray.add(new NumberCard(blueCard, a));
            presidentCardsArray.addActor(new DatesCard(blueCard, a));
            stage.addActor(numberCardsArray.get(i));
            i++;
        }
//        i = 0;
//        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
//            numberCardsArray.add(new NumberCard(blueCard, a));
//
//            i++;
//        }
    }

    public void setHintCards() {
        generateHints();
        updatePresidentCardsArrayWithHints();

    }

    private void generateHints() {
        // replace some values of presidentsArray with hint red values
        Array<Integer> hintArray = new Array<Integer>(GameManager.quantityOfHints - 1);
        Random random = new Random();
        int hint;
        for (int h = 0; h <= GameManager.quantityOfHints - 1; h++) {
            do
                hint = GameManager.firstPresidentInRange + random.nextInt(GameManager.lastPresidentInRange - GameManager.firstPresidentInRange);
            while (hintArray.contains(hint, true) || hint == GameManager.currentWrightPresident);
            presidentsArray[hint] = TypeOfCard.RedDate;
        }
        // add right answer
        presidentsArray[GameManager.currentWrightPresident] = TypeOfCard.RedDate;
    }

    private void updatePresidentCardsArrayWithHints() {
        int i = 0;
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == TypeOfCard.RedDate)
                presidentCardsArray.addActorAt(i, new DatesCard(redCard, a));
//            stage.addActor(presidentCardsArray.get(i));
            i++;
        }
//        presidentCardsArray.isTransform();
//        presidentCardsArray.remove();
//        stage.addActor(presidentCardsArray);
    }

//    private void initCardsArrays() {
//        int i = 0;
//        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
//            numberCardsArray.add(new NumberCard(blueCard, a, i));
//            if (presidentsArray[a] == TypeOfCard.RedDate)
//                presidentCardsArray.add(new DatesCard(redCard, a, i));
//            else if (presidentsArray[a] == TypeOfCard.BlueDate)
//                presidentCardsArray.add(new DatesCard(blueCard, a, i));
//            stage.addActor(numberCardsArray.get(i));
//            stage.addActor(presidentCardsArray.get(i));
//            i++;
//        }
//    }

    public void pushRightPresidentNameCard() {
        final DatesCard actor = (DatesCard) presidentCardsArray.hit(795, (GameManager.currentWrightPresident - GameManager.firstPresidentInRange) * 60 + 30, true);
        actor.setIsPush(false);
        System.out.println(actor.getNumberPresident());

//        actor.addAction(sequence(moveAction,
//                run(new Runnable(){
//                    @Override
//                    public void run() {
//                        Gdx.app.log("STATUS", "Action complete");
//                    }
//
//                })));

//        RunnableAction run = new RunnableAction();
//        run.setRunnable(new Runnable() {
//            @Override
//            public void run() {
//                presidentCardsArray.removeActor(actor);
//                presidentCardsArray.addActor(new PresidentNameCard(blueCard, (GameManager.currentWrightPresident)));
//            }
//        });
//
//        actor.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, actor.getY(), 5f), run));


//        ((DatesCard) (presidentCardsArray.getChildren().get(GameManager.currentWrightPresident - GameManager.firstPresidentInRange))).setIsPush(false);
//        SnapshotArray<Actor> array =  presidentCardsArray.getChildren();
//        for (int i=0;  i < array.size; i++){
//            System.out.println(((DatesCard) array.get(i)).getNumberPresident());
//        }
//        presidentCardsArray.getChildren().set(GameManager.currentWrightPresident - GameManager.firstPresidentInRange + 1, new PresidentNameCard(blueCard, (GameManager.currentWrightPresident)));

    }

    public void setNewHints() {

    }

    public CardsStage getStage() {
        return stage;
    }

    public Sprite getBackgroundSprite() {
        return backgroundSprite;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
