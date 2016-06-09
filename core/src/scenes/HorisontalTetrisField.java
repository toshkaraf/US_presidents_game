package scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import cards.NumberCard;
import cards.PresidentNameCard;
import helpers.GameInfo;

/**
 * Created by Антон on 01.06.2016.
 */
public class HorisontalTetrisField extends ScreenAdapter {

    SpriteBatch batch;
    Sprite background;
    Array<Integer> presidentsNumbersArray = new Array<Integer>();
    Sprite player;
    Texture blueCard, redCard;
    NumberCard numberCard;
    PresidentNameCard nameCard;
    private Viewport viewport;
    private OrthographicCamera camera;

    Boolean isNumberCardPrepared = false;
    int currentPresidentNumber;
    Boolean isGameFieldPrepared = false;
    int numberOfFirstVisiblePresident, numberOfLastVisiblePresident;
    int currentNumberOfPresidentForDrawingGameField, currentPositionFromBottom;
    Array<NumberCard> fixedNumberCardsArray = new Array<NumberCard>();
    Array<PresidentNameCard> fixedNameCardsArray = new Array<PresidentNameCard>();

    public HorisontalTetrisField(MainGame game, int numberOfFirstVisiblePresident, int numberOfLastVisiblePresident) {
        this.numberOfFirstVisiblePresident = numberOfFirstVisiblePresident;
        this.numberOfLastVisiblePresident = numberOfLastVisiblePresident;
        currentNumberOfPresidentForDrawingGameField = numberOfFirstVisiblePresident;
        currentPositionFromBottom = 0;
        batch = game.getBatch();
        player = new Sprite(new Texture(Gdx.files.internal("players/arrowUSA.png")));
        redCard = new Texture(Gdx.files.internal("cards/card_of_president_red.png"));
        blueCard = new Texture(Gdx.files.internal("cards/card_of_president.png"));
        background = new Sprite(new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground_clear.jpg")),
                0, 0, GameInfo.WORLD_WIDTH, Math.round(blueCard.getHeight() * (numberOfLastVisiblePresident - numberOfFirstVisiblePresident + 1)));

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);
        viewport.apply();

        numberCard = new NumberCard(new Sprite(blueCard), currentNumberOfPresidentForDrawingGameField, currentPositionFromBottom);
        nameCard = new PresidentNameCard(new Sprite(blueCard), currentNumberOfPresidentForDrawingGameField, currentPositionFromBottom);

        initPresidentsNumbersArray();
        setNewPresidentAndPosition();

    }

    private void initPresidentsNumbersArray() {
        for (int i = numberOfFirstVisiblePresident; i <= numberOfLastVisiblePresident; i++)
            presidentsNumbersArray.add(i);
    }


    private void prepareGameField() {
        if (numberCard.draw(batch)) {
            fixedNumberCardsArray.add(numberCard);
            fixedNameCardsArray.add(nameCard);
            numberCard = new NumberCard(new Sprite(blueCard), ++currentNumberOfPresidentForDrawingGameField, ++currentPositionFromBottom);
            nameCard = new PresidentNameCard(new Sprite(blueCard), currentNumberOfPresidentForDrawingGameField, currentPositionFromBottom);
            if (currentNumberOfPresidentForDrawingGameField > numberOfLastVisiblePresident)
                isGameFieldPrepared = true;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        if (!isGameFieldPrepared) {
            batch.begin();
            batch.draw(background, 0, 0);
            prepareGameField();
            drawFixedCards();
            batch.end();
        } else {
            if (player.getX() + player.getWidth() <= GameInfo.WORLD_WIDTH) {
                queryInput();
                camera.update();
                batch.setProjectionMatrix(camera.projection);
                batch.setTransformMatrix(camera.view);
                batch.begin();
                batch.draw(background, 0, 0);
                drawFixedCards();
                updatePlayer();
                batch.draw(player, player.getX(), player.getY());
                batch.end();
            } else presidentIsDone();
        }
    }

    private void drawFixedCards() {
        for (NumberCard numbercard : fixedNumberCardsArray) {
            numbercard.draw(batch);
        }
        for (PresidentNameCard nameCard : fixedNameCardsArray) {
            nameCard.draw(batch);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        background.dispose();

    }

    //check for bounds
    //update player's and cameras coordinates
    private void queryInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && player.getY() < background.getHeight() - player.getHeight()) {
            player.setY(player.getY() + GameInfo.STEP_FOR_TETRIS_Y);
            if ((camera.position.y <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - 6) &&
                    (player.getY() >= GameInfo.WORLD_HEIGHT / 2 - player.getHeight() / 2))
                camera.position.y = camera.position.y + GameInfo.STEP_FOR_TETRIS_Y;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.getY() > 0) {
            player.setY(player.getY() - GameInfo.STEP_FOR_TETRIS_Y);
            if ((camera.position.y >= GameInfo.WORLD_HEIGHT / 2 + 6) &&
                    (player.getY() <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - player.getHeight() / 2))
                camera.position.y = camera.position.y - GameInfo.STEP_FOR_TETRIS_Y;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.setX(player.getX() + GameInfo.FAll_STEP_FOR_TETRIS_X);
        }

    }

    private void updatePlayer() {
        player.setX(player.getX() + GameInfo.SLOW_STEP_FOR_TETRIS_X);
    }

    private void setNewPresidentAndPosition() {
        if (presidentsNumbersArray.size > 0)
            currentPresidentNumber = presidentsNumbersArray.removeIndex(0);
        else gameOver();
        player.setPosition(GameInfo.START_X_POSITION_OF_TETRIS_PLAYER, background.getHeight() / 2 - player.getHeight() / 2);
        camera.position.set(background.getWidth() / 2, background.getHeight() / 2, 0);
    }

    private void presidentIsDone() {
        if (checkAnswer()) {
            System.out.println("You are right");

        } else System.out.println("You are wrong");
        setNewPresidentAndPosition();
    }

    private boolean checkAnswer() {
//        return ((player.getX() >= convertDateToCoordinateX(currentPlayerPresident.getInitialDate())) &&
//                (player.getX() <= convertDateToCoordinateX(currentPlayerPresident.getFinalDate())));
        return true;
    }

    private void gameOver() {
        System.out.println("Game over");
    }

    private float convertDateToCoordinateX(int date) {
        return background.getWidth() / 228 * (date - GameInfo.INITIAL_DATE_OF_FIRST_PRESIDENT);
    }
}
