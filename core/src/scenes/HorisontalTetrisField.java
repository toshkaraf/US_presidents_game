package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;
import huds.DecoratorFieldWIthCards;
import huds.MainMenuButtons;
import huds.PortraitPanel;

/**
 * Created by Антон on 01.06.2016.
 */
public class HorisontalTetrisField implements Screen {

    SpriteBatch batch;
    Sprite background;
    Sprite player;
    private Viewport viewport;
    private OrthographicCamera camera;
    DecoratorFieldWIthCards decoratorFieldWIthCards;
    PortraitPanel portraitPanel;
    boolean switcher = true;
    private boolean isRightAnswer;
    MainGame game;


    public HorisontalTetrisField(MainGame game) {

        GameManager.initPresidentsListForQuestionsArray();
        GameManager.setNewCurrentPresident(true);
        this.game = game;
        decoratorFieldWIthCards = new DecoratorFieldWIthCards(game);
        portraitPanel = new PortraitPanel(game);
//        background = decoratorFieldWIthCards.getBackgroundSprite();
        background = new Sprite(new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground_game.jpg")),
                0, 0, GameInfo.WORLD_WIDTH, Math.round(60 * (GameManager.lastPresidentInRange - GameManager.firstPresidentInRange + 1)));

        player = new Sprite(new Texture(Gdx.files.internal("players/arrowUSA.png")));
        batch = game.getBatch();
        camera = decoratorFieldWIthCards.getCamera();
//        camera = new OrthographicCamera();
        camera.position.set(background.getWidth() / 2, background.getHeight() / 2, 0);
        viewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);

//        viewport.apply();

        setInitialPlayerPosition();
    }

    @Override
    public void show() {

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
        decoratorFieldWIthCards.getStage().draw();
        decoratorFieldWIthCards.getStage().act(Gdx.graphics.getDeltaTime());

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
                    decoratorFieldWIthCards.generateHints();
                    decoratorFieldWIthCards.pushHintCards();
                }
                break;
            case SetNewPlayer:
                if (player.getX() + player.getWidth() <= GameInfo.WORLD_WIDTH) {
                    queryInput();
                    camera.update();
                    batch.setProjectionMatrix(camera.projection);
                    batch.setTransformMatrix(camera.view);
                    batch.begin();
                    batch.draw(player, player.getX(), player.getY());
                    batch.end();
                    updatePlayer();
                } else
                    GameManager.renderMode = GameManager.RenderMode.MoveCamToRightAnswer;
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
                    if (isRightAnswer) decoratorFieldWIthCards.pushRightNameCardIfRightAnswer();
                    else decoratorFieldWIthCards.showRightNameCardIfWrongAnswer();
//                    game.setScreen(new Menu(game, new MainMenuButtons(game)));
                }
                break;
            case MoveCamToStartPosition:
                if (moveCameraToY(background.getHeight() / 2)) {
                    decoratorFieldWIthCards.renewData(isRightAnswer);
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

    private boolean moveCameraToY(float y) {
        if (y <= GameInfo.WORLD_HEIGHT / 2 + 10 && camera.position.y <= GameInfo.WORLD_HEIGHT / 2 + 10)
            return true;

        if (camera.position.y > y && y >= GameInfo.WORLD_HEIGHT / 2 + 10) {
            camera.position.y = camera.position.y - 10;
            if (camera.position.y <= y || camera.position.y <= GameInfo.WORLD_HEIGHT / 2 + 10)
                return true;
        }

        if (y >= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - 10 && camera.position.y >= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - 10)
            return true;
        if (camera.position.y < y && y <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - 10) {
            camera.position.y = camera.position.y + 10;
            if (camera.position.y >= y || camera.position.y >= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - 10)
                return true;
        }

        if (camera.position.y == y)
            return true;
        return false;
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


    private void setInitialPlayerPosition() {
        player.setPosition(GameInfo.START_X_POSITION_OF_TETRIS_PLAYER, background.getHeight() / 2 - player.getHeight() / 2);
    }

    private boolean checkAnswer() {
        return ((player.getY() + 29 >= (GameManager.currentRightPresident - GameManager.firstPresidentInRange) * 60 &&
                (player.getY() + 29 <= (GameManager.currentRightPresident - GameManager.firstPresidentInRange) * 60 + 60)));
    }

    private void gameOver() {
//        MainGame.getInstance().setNewScreen(MainGame.KindsOfMenu.mainMenu);
//        game.setScreen(new MainMenu1(game));
//        batch.dispose();
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
        decoratorFieldWIthCards.getStage().dispose();
        portraitPanel.getStage().dispose();

    }
}
