package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;
import huds.DecoratorChooseFromAll;
import huds.DecoratorWIthCards;
import huds.MainMenuButtons;
import huds.PortraitPanel;

import static com.badlogic.gdx.input.GestureDetector.*;

/**
 * Created by Антон on 01.06.2016.
 */
public class HorisontalTetrisField implements Screen, InputProcessor {

    SpriteBatch batch;
    Sprite background;
    Sprite player;
    private Viewport viewport;
    OrthographicCamera camera;
    DecoratorWIthCards decoratorWithCards;
    PortraitPanel portraitPanel;
    boolean switcher = true;
    boolean isRightAnswer, isPlayerFlinged;
    MainGame game;
    GestureDetector listener;
    private boolean isUpMove, isDownMove;


    public HorisontalTetrisField(MainGame game) {

        GameManager.setNewCurrentPresident(true);
        this.game = game;

        if (GameManager.quantityOfHints == 0) decoratorWithCards = new DecoratorChooseFromAll(game);
        else decoratorWithCards = new DecoratorWIthCards(game);

        portraitPanel = new PortraitPanel(game);

        background = new Sprite(new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground_game.jpg")),
                0, 0, GameInfo.WORLD_WIDTH, Math.round(60 * (GameManager.lastPresidentInRange - GameManager.firstPresidentInRange + 1)));
        player = new Sprite(new Texture(Gdx.files.internal("players/arrowUSA.png")));
        batch = game.getBatch();
        camera = decoratorWithCards.getCamera();
        camera.position.set(background.getWidth() / 2, background.getHeight() / 2, 0);
        viewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);


    }

    @Override
    public void show() {
//        listener = new GestureDetector(this);
//        Gdx.input.setInputProcessor(listener);
        if (GameManager.quantityOfHints == 0) decoratorWithCards = new DecoratorChooseFromAll(game);
        else decoratorWithCards = new DecoratorWIthCards(game);

        portraitPanel = new PortraitPanel(game);

        background = new Sprite(new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground_game.jpg")),
                0, 0, GameInfo.WORLD_WIDTH, Math.round(60 * (GameManager.lastPresidentInRange - GameManager.firstPresidentInRange + 1)));
        player = new Sprite(new Texture(Gdx.files.internal("players/arrowUSA.png")));
        batch = game.getBatch();
        camera = decoratorWithCards.getCamera();
        camera.position.set(background.getWidth() / 2, background.getHeight() / 2, 0);
        viewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);
        setInitialPlayerPosition();
    }

    @Override
    public void render(float delta) {
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
            case SetNewPlayer:
                if (player.getX() + player.getWidth() <= GameInfo.WORLD_WIDTH) {
                    if (isPlayerFlinged) player.setX(player.getX() + 30);
//                    queryInput();   //do not use in android
//                    camera.update();  //do not use in android
                    batch.setProjectionMatrix(camera.projection);
                    batch.setTransformMatrix(camera.view);
                    batch.begin();
                    batch.draw(player, player.getX(), player.getY());
                    batch.end();
                    updatePlayer();
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
                    if (isRightAnswer) decoratorWithCards.pushRightNameCardIfRightAnswer();
                    else decoratorWithCards.showRightNameCardIfWrongAnswer();
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

    void updatePlayer() {
        player.setX(player.getX() + GameInfo.SLOW_STEP_FOR_TETRIS_X);

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


    void setInitialPlayerPosition() {
        player.setPosition(GameInfo.START_X_POSITION_OF_TETRIS_PLAYER, background.getHeight() / 2 - player.getHeight() / 2);
        Gdx.input.setInputProcessor(this);
    }

    boolean checkAnswer() {
        return ((player.getY() + 29 >= (GameManager.currentRightPresident - GameManager.firstPresidentInRange) * 60 &&
                (player.getY() + 29 <= (GameManager.currentRightPresident - GameManager.firstPresidentInRange) * 60 + 60)));
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
        //TODO convert screen to world coordinates
        if (screenY > GameInfo.WORLD_HEIGHT/2 && screenX < GameInfo.WORLD_WIDTH) {
            isDownMove = true;
            return true;
        }
        if (screenY < GameInfo.WORLD_HEIGHT/2 && screenX < GameInfo.WORLD_WIDTH) {
            isUpMove  = true;
            return true;
        }
        if (screenX > GameInfo.WORLD_WIDTH/2) {
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

//    @Override
//    public boolean touchDown(float x, float y, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean tap(float x, float y, int count, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean longPress(float x, float y) {
//        return false;
//    }
//
//    @Override
//    public boolean fling(float velocityX, float velocityY, int button) {
//        if (velocityX > 2000) isPlayerFlinged = true;
//        return false;
//    }
//
//    @Override
//    public boolean pan(float x, float y, float deltaX, float deltaY) {
//
//        if ((x == deltaX & y == deltaY) || (deltaX == 0 & deltaY == 0)) return false;
//        else {
//            Gdx.app.log("MyLog", "x = " + x);
//            Gdx.app.log("MyLog", "y= " + y);
//            Gdx.app.log("MyLog", "deltaX = " + deltaX);
//            Gdx.app.log("MyLog", "deltaY = " + deltaY);
//            Gdx.app.log("MyLog", "___");
//            if (player.getY() <= background.getHeight() - player.getHeight() - deltaY* 1.3f && player.getY() >= 0 - deltaY* 1.3f) {
//                player.setY(player.getY() + deltaY * 1.3f);
//                {
//                    if (deltaY > 0 && (camera.position.y <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - deltaY* 1.3f) &&
//                            (player.getY() >= GameInfo.WORLD_HEIGHT / 2 - player.getHeight() / 2))
//                        camera.position.y = camera.position.y + deltaY * 1.3f;
//                    if (deltaY < 0 && (camera.position.y >= GameInfo.WORLD_HEIGHT / 2 - deltaY* 1.3f) &&
//                            (player.getY() <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - player.getHeight() / 2))
//                        camera.position.y = camera.position.y + deltaY * 1.3f;
//                }
//            }
//            return true;
//        }
//    }
//
//    @Override
//    public boolean panStop(float x, float y, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean zoom(float initialDistance, float distance) {
//        return false;
//    }
//
//    @Override
//    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2
//            pointer1, Vector2 pointer2) {
//        return false;
//    }
//
//    @Override
//    public void pinchStop() {
//
//    }
}
