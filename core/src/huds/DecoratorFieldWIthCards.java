package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import helpers.GameInfo;

/**
 * Created by Антон on 10.06.2016.
 */
public class DecoratorFieldWIthCards {


    private SpriteBatch batch;
    private MainGame game;
    private Stage stage;
    private Sprite blueCard, redCard;
    private Sprite background;
    private Viewport gameViewport;
    private int currentPresident, firstPresident, lastPresident;
    private TypeOfCard[] presidentsArray = new TypeOfCard[44];
    private Array<NumberCard> numberCardArray;
    private Array<PresidentNameCard> presidentNameCardArray;
    private Array<Card> presidentCardArray;
    OrthographicCamera camera;

    enum TypeOfCard {BlueDate, RedDate, BlueName, RedName}

    public DecoratorFieldWIthCards(MainGame game, int quantityOfHints, int firstPresident, int lastPresident) {

        this.game = game;
        this.firstPresident = firstPresident;
        this.lastPresident = lastPresident;
        numberCardArray = new Array<NumberCard>();
        presidentCardArray = new Array<Card>();

        redCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president_red.png")));
        blueCard = new Sprite(new Texture(Gdx.files.internal("cards/card_of_president.png")));
        background = new Sprite(new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground_game.jpg")),
                0, 0, GameInfo.WORLD_WIDTH, Math.round(blueCard.getHeight() * (lastPresident - firstPresident + 1)));

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT);
        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);
        camera.position.set(background.getWidth() / 2, background.getHeight() / 2, 0);

        stage = new Stage(gameViewport, batch);
        currentPresident = firstPresident - 1;

        initPresidentArray(quantityOfHints);
        initCardsArrays();
    }

    private void initPresidentArray(int quantityOfHints) {
        int hint = 1;
        Random random = new Random(100);
        for (int president = firstPresident; president <= lastPresident; president++) {
            boolean isHint = random.nextBoolean();
            if (isHint && hint++ <= quantityOfHints)
                this.presidentsArray[president] = TypeOfCard.RedDate;
            else this.presidentsArray[president] = TypeOfCard.BlueDate;
        }
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

    public Stage getStage() {
        return stage;
    }

    public Sprite getBackground() {
        return background;
    }
}
