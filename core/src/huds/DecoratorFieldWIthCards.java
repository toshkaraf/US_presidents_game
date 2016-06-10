package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
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
    private int currentWrightPresident, firstPresident, lastPresident;
    private TypeOfCard[] presidentsArray = new TypeOfCard[44];
    private Array<NumberCard> numberCardArray;
    private Array<Card> presidentCardArray;
    OrthographicCamera camera;

    enum TypeOfCard {BlueDate, RedDate, BlueName, RedName}


    public DecoratorFieldWIthCards(MainGame game, int currentWrightPresident, int quantityOfHints, int firstPresident, int lastPresident) {

        this.game = game;
        this.firstPresident = firstPresident-1;
        this.lastPresident = lastPresident-1;
        this.currentWrightPresident = currentWrightPresident-1;
        numberCardArray = new Array<NumberCard>();
        presidentCardArray = new Array<Card>();

        redCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president_red.png")));
        blueCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president.png")));
        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground_game.jpg")),
                0, 0, GameInfo.WORLD_WIDTH, Math.round(blueCard.getHeight() * (lastPresident - firstPresident + 1)));

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

        initPresidentArray(quantityOfHints-1);
        initCardsArrays();
    }

    private void initPresidentArray(int quantityOfHints) {
        for (int president = firstPresident; president <= lastPresident; president++)
            this.presidentsArray[president] = TypeOfCard.BlueDate;
        // replace some values with hint red values
        Array<Integer> hintArray = new Array<Integer>(quantityOfHints);
        Random random = new Random();
        Integer hint;
        for (int h=0; h<=quantityOfHints; h++) {
            do hint = firstPresident + random.nextInt(lastPresident - firstPresident);
            while (hintArray.contains(hint, true) | hint ==currentWrightPresident);
            this.presidentsArray[hint] = TypeOfCard.RedDate;
        }
        presidentsArray[currentWrightPresident] = TypeOfCard.RedDate;
    }

    private void initCardsArrays() {
        int i = 0;
        for (int a = firstPresident; a <= lastPresident; a++) {
            numberCardArray.add(new NumberCard(blueCard, a, i));
            if (presidentsArray[a] == TypeOfCard.RedDate)
                presidentCardArray.add(new DatesCard(redCard, a, i));
            else if (presidentsArray[a] == TypeOfCard.BlueDate)
                presidentCardArray.add(new DatesCard(blueCard, a, i));
            stage.addActor(numberCardArray.get(i));
            stage.addActor(presidentCardArray.get(i));
            i++;
        }
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
