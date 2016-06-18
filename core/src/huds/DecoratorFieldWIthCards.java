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
    private TypeOfCard[] presidentsArray = new TypeOfCard[44];
    private Group numberCardsArray;
    //    private Array<Card> presidentCardsArray;
    OrthographicCamera camera;


    enum TypeOfCard {BlueDate, RedDate, BlueName, RedName}


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
            this.presidentsArray[president] = TypeOfCard.BlueDate;
    }

    private void initCardsArrays() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            numberCardsArray.addActor(new NumberCard(blueCard, a));
            presidentCardsArray.addActor(new DatesCard(blueCard, a));
        }

        numberCardsArray.setBounds(numberCardsArray.getX(), numberCardsArray.getY(), numberCardsArray.getWidth(), numberCardsArray.getHeight());
        numberCardsArray.setPosition(-400, 0);
        numberCardsArray.addAction(sequence(moveTo(-355, 0, 1f), delay(1f), new RenderModeAction(GameManager.RenderMode.Portrait)));
    }

    public void generateHints() {
        // replace some values of presidentsArray with hint red values
        Array<Integer> hintArray = new Array<Integer>(GameManager.quantityOfHints - 1);
        Random random = new Random();
        int hint;
        for (int h = 0; h <= GameManager.quantityOfHints - 1; h++) {
            do
                hint = GameManager.firstPresidentInRange + random.nextInt(GameManager.lastPresidentInRange - GameManager.firstPresidentInRange);
            while (hintArray.contains(hint, true) || hint == GameManager.currentRightPresident);
            presidentsArray[hint] = TypeOfCard.RedDate;
        }
        // add right answer
        presidentsArray[GameManager.currentRightPresident] = TypeOfCard.RedDate;
    }

    public void pullHintCards() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == TypeOfCard.RedDate) {
                DatesCard actor = (DatesCard) presidentCardsArray.findActor("date_of_" + a);
                actor.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, actor.getY(), 1f), new RenderModeAction(GameManager.RenderMode.PushNewHints)));
            }
        }
    }

    public void pushHintCards() {
        for (int a = GameManager.firstPresidentInRange; a <= GameManager.lastPresidentInRange; a++) {
            if (presidentsArray[a] == TypeOfCard.RedDate) {
                presidentCardsArray.findActor("date_of_" + a).remove();
                DatesCard actor = new DatesCard(redCard, a);
                actor.addAction(new RenderModeAction(GameManager.RenderMode.SetNewPlayer));
                presidentCardsArray.addActor(actor);
            }
        }
    }

    public void pushRightNameCardIfRightAnswer() {
        DatesCard currentPresidentCard = presidentCardsArray.findActor("date_of_" + GameManager.currentRightPresident);
        currentPresidentCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, currentPresidentCard.getY(), 1f),delay(3f),
                new RenderModeAction(GameManager.RenderMode.MoveCamToStartPosition)));
        PresidentNameCard rightNameCard = new PresidentNameCard(blueCard, GameManager.currentRightPresident);
        rightNameCard.addAction(moveTo(GameInfo.WORLD_WIDTH-370, rightNameCard.getY(), 1f));
        presidentCardsArray.addActor(rightNameCard);
    }

    public void showRightNameCardIfWrongAnswer() {
        DatesCard currentPresidentCard = presidentCardsArray.findActor("date_of_" + GameManager.currentRightPresident);
        currentPresidentCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, currentPresidentCard.getY(),1f)));
        PresidentNameCard rightNameCard = new PresidentNameCard(blueCard, GameManager.currentRightPresident);
        presidentCardsArray.addActor(rightNameCard);
        rightNameCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH-370, rightNameCard.getY(), 1f),delay(3f),moveTo(GameInfo.WORLD_WIDTH, currentPresidentCard.getY(), 1f),
                new RenderModeAction(GameManager.RenderMode.MoveCamToStartPosition)));

    }


//        final DatesCard actor = (DatesCard) presidentCardsArray.hit(795, (GameManager.currentRightPresident - GameManager.firstPresidentInRange) * 60 + 30, true);
//        actor.setIsPush(false);
//        System.out.println(actor.getNumberPresident());

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
//                presidentCardsArray.addActor(new PresidentNameCard(blueCard, (GameManager.currentRightPresident)));
//            }
//        });
//
//        actor.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, actor.getY(), 5f), run));


//        ((DatesCard) (presidentCardsArray.getChildren().get(GameManager.currentRightPresident - GameManager.firstPresidentInRange))).setIsPush(false);
//        SnapshotArray<Actor> array =  presidentCardsArray.getChildren();
//        for (int i=0;  i < array.size; i++){
//            System.out.println(((DatesCard) array.get(i)).getNumberPresident());
//        }
//        presidentCardsArray.getChildren().set(GameManager.currentRightPresident - GameManager.firstPresidentInRange + 1, new PresidentNameCard(blueCard, (GameManager.currentRightPresident)));



    public void setNewHints() {

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
