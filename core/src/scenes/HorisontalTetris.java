package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;
import huds.DecoratorChooseFromAll;
import huds.DecoratorWIthCards;
import huds.MainMenuButtons;
import huds.PortraitPanel;
import huds.ScorePanel;

/**
 * Created by Антон on 01.06.2016.
 */
public class HorisontalTetris implements Screen, InputProcessor {

    Texture bg;
    SpriteBatch batch;
    Sprite background, player;
    Viewport viewport;
    OrthographicCamera camera;
    DecoratorWIthCards decoratorWithCards;
    PortraitPanel portraitPanel;
    ScorePanel scorePanel;
    boolean switcher = true;
    boolean isRightAnswer, isPlayerFlinged;
    MainGame game;
    protected boolean isUpMove, isDownMove;
    private Sound victorySound;
    private int playerMoveDelayCounter = 0;
    Vector3 worldCoordinates;


    public HorisontalTetris(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        GameManager.initNewGame();
        GameManager.setNewCurrentPresident(true);
        if (GameManager.quantityOfHints == 0) decoratorWithCards = new DecoratorChooseFromAll(game);
        else decoratorWithCards = new DecoratorWIthCards(game);

        portraitPanel = new PortraitPanel(game);
        scorePanel = new ScorePanel(game);

        bg = new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground.png"));
        background = new Sprite(bg, 0, bg.getHeight() - Math.round(GameManager.lastPresidentInRange * GameInfo.CARD_HEIGHT + GameInfo.CARD_HEIGHT),
                GameInfo.WORLD_WIDTH, Math.round(GameInfo.CARD_HEIGHT * (GameManager.lastPresidentInRange - GameManager.firstPresidentInRange + 1)));
        player = new Sprite(new Texture(Gdx.files.internal("players/arrowUSA.png")));
        player.setSize(142,60);
        batch = game.getBatch();
        camera = decoratorWithCards.getCamera();
        setInitialPlayerPosition();
        viewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);

    }

    @Override
    public void render(float delta) {
        prepareField();
        switch (GameManager.renderMode) {
            case PrepareField:
                break;
            case Portrait:
                portraitPanel.getStage().draw();
                portraitPanel.getStage().act();
                break;
            case PushNewHints:
                if (switcher) {
                    switcher = false;
                    decoratorWithCards.generateHints();
                    decoratorWithCards.pushHintCards();
                }
                break;
            case PlayGame:
                if (player.getX() + player.getWidth() <= GameInfo.WORLD_WIDTH) {
                    if (isPlayerFlinged) player.setX(player.getX() + 30);
//                    queryInput();   //do not use in android
//                    camera.update();  //do not use in android
                    batch.setProjectionMatrix(camera.projection);
                    batch.setTransformMatrix(camera.view);
                    batch.begin();
                    batch.draw(player, player.getX(), player.getY());
                    batch.end();
                    updatePlayerX();
                    if (playerMoveDelayCounter == 5) {
                        playerMoveDelayCounter = 0;
                        updatePlayerAndCamY();
                    }else playerMoveDelayCounter++;
                } else {
                    GameManager.renderMode = GameManager.RenderMode.MoveCamToRightAnswer;
                    isPlayerFlinged = false;
                }
                break;
            case MoveCamToRightAnswer:
                if (moveCameraToY((GameManager.currentRightPresident - GameManager.firstPresidentInRange) * 60 + 30)) {
                    GameManager.renderMode = GameManager.RenderMode.ShowRightAnswer;
                    isRightAnswer = checkAnswer();
                }
                break;
            case ShowRightAnswer:
                if (!switcher) {
                    switcher = true;
                    if (isRightAnswer) {
                        if (GameManager.getInstance().gameData.isSounds()) GameManager.getInstance().getRightSound().play(1f);
                        decoratorWithCards.pushRightNameCardIfRightAnswer();
                        scorePanel.incrementScore(100);
                    } else {
                        if (GameManager.getInstance().gameData.isSounds()) GameManager.getInstance().getWrongSound().play(1f);
                        decoratorWithCards.showRightNameCardIfWrongAnswer();
                        scorePanel.decrementLife();
                        if (GameManager.getInstance().life == 0) gameOver();
                    }
                }
                break;
            case MoveCamToStartPosition:
                if (moveCameraToY(background.getHeight() / 2)) {
                    decoratorWithCards.renewData(isRightAnswer);
                    if (!GameManager.setNewCurrentPresident(isRightAnswer)) gameOver();
                    else {
                        portraitPanel.getStage().dispose();
                        portraitPanel = new PortraitPanel(game);
                        setInitialPlayerPosition();
                        GameManager.renderMode = GameManager.RenderMode.Portrait;
                    }
                }
                break;
        }
    }

    void prepareField() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        decoratorWithCards.getStage().draw();
        decoratorWithCards.getStage().act(Gdx.graphics.getDeltaTime());
        drawScorePanel();
    }

    void drawScorePanel() {
        scorePanel.getStage().draw();
    }

    boolean moveCameraToY(float y) {
        if ((y <= GameInfo.WORLD_HEIGHT / 2 + 20 && camera.position.y <= GameInfo.WORLD_HEIGHT / 2 + 20) ||
                (y >= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - 20 &&
                        camera.position.y >= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - 20) ||
                (camera.position.y == y))
            return true;
        else {
            if (camera.position.y > y) {
                camera.position.y = camera.position.y - 20;
                if (camera.position.y <= y || camera.position.y <= GameInfo.WORLD_HEIGHT / 2 + 20)
                    return true;
            }
            if (camera.position.y < y) {
                camera.position.y = camera.position.y + 20;
                if (camera.position.y >= y || camera.position.y >= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - 20)
                    return true;
            }
        }
        return false;
    }


    //check for bounds
    //update player's and cameras coordinates
    //for desktop
    void queryInput() {
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

    void updatePlayerAndCamY() {

        if (isUpMove && player.getY() < background.getHeight() - player.getHeight()) {
            player.setY(player.getY() + GameInfo.STEP_FOR_TETRIS_Y);
            if ((camera.position.y <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - GameInfo.STEP_FOR_TETRIS_Y) &&
                    (player.getY() >= GameInfo.WORLD_HEIGHT / 2 - player.getHeight() / 2))
                camera.position.y = camera.position.y + GameInfo.STEP_FOR_TETRIS_Y;
        }
        if (isDownMove && player.getY() > 0) {
            player.setY(player.getY() - GameInfo.STEP_FOR_TETRIS_Y);
            if ((camera.position.y >= GameInfo.WORLD_HEIGHT / 2 + GameInfo.STEP_FOR_TETRIS_Y) &&
                    (player.getY() <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - player.getHeight() / 2))
                camera.position.y = camera.position.y - GameInfo.STEP_FOR_TETRIS_Y;
        }
    }

    void updatePlayerX(){
        player.setX(player.getX() + GameInfo.SLOW_STEP_FOR_TETRIS_X);
    }

    void setInitialPlayerPosition() {
        player.setPosition(GameInfo.START_X_POSITION_OF_TETRIS_PLAYER,(GameManager.lastPresidentInRange - GameManager.firstPresidentInRange)/2*GameInfo.CARD_HEIGHT);
        camera.position.set(background.getWidth() / 2,player.getY(), 0);
        Gdx.input.setInputProcessor(this);
    }

    boolean checkAnswer() {
        return ((player.getY() + 29 >= (GameManager.currentRightPresident - GameManager.firstPresidentInRange) * GameInfo.CARD_HEIGHT &&
                (player.getY() + 29 <= (GameManager.currentRightPresident - GameManager.firstPresidentInRange) * GameInfo.CARD_HEIGHT + GameInfo.CARD_HEIGHT)));
    }

    void gameOver() {
        game.setScreen(new Menu(game, new MainMenuButtons(game)));
        dispose();
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
        player.getTexture().dispose();
        background.getTexture().dispose();
        decoratorWithCards.getStage().dispose();
        portraitPanel.getStage().dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        if (worldCoordinates.x < GameInfo.WORLD_WIDTH/2) {
            if (worldCoordinates.y < camera.position.y) {
                isDownMove = true;
                return true;
            }
            if (worldCoordinates.y > camera.position.y) {
                isUpMove = true;
                return true;
            }
        }else {
            isPlayerFlinged = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isUpMove = false;
        isDownMove = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
