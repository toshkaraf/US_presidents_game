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

import helpers.GameInfo;
import helpers.GameManager;
import huds.DecoratorFieldWIthCards;
import huds.PortraitPanel;

/**
 * Created by Антон on 01.06.2016.
 */
public class HorisontalTetrisField extends ScreenAdapter {

    SpriteBatch batch;
    Sprite background;
    Array<Integer> presidentsListForQuestions = new Array<Integer>();
    Sprite player;
    private Viewport viewport;
    private OrthographicCamera camera;
    DecoratorFieldWIthCards decoratorFieldWIthCards;
    PortraitPanel portraitPanel;
    boolean isPortraitMode = true;
    boolean isShowRightMode = false;
    boolean isPushNewHintsMode = false;

    public HorisontalTetrisField(MainGame game) {

        initPresidentsListForQuestionsArray();
        setNewCurrentPresident();
        decoratorFieldWIthCards = new DecoratorFieldWIthCards(game);
        portraitPanel = new PortraitPanel(game);
        background = decoratorFieldWIthCards.getBackgroundSprite();
        player = new Sprite(new Texture(Gdx.files.internal("players/arrowUSA.png")));
        batch = game.getBatch();
        camera = decoratorFieldWIthCards.getCamera();
        viewport = new FitViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);
        viewport.apply();
        GameManager.counterOfPushedCards = 0;

        setInitialPlayerPosition();
    }

    private void initPresidentsListForQuestionsArray() {
        for (int i = GameManager.firstPresidentInRange; i <= GameManager.lastPresidentInRange; i++)
            presidentsListForQuestions.add(i);
        presidentsListForQuestions.shuffle();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        decoratorFieldWIthCards.getStage().draw();
        decoratorFieldWIthCards.getStage().act(Gdx.graphics.getDeltaTime());

        switch (GameManager.renderMode) {
            case PrepareField:
                break;
            case Portrait:
                portraitPanel.getStage().draw();
                portraitPanel.getStage().act();
                break;
            case SetNewHints:
                decoratorFieldWIthCards.setHintCards();
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
                } else presidentIsDone();
        }
//        batch.setProjectionMatrix(camera.projection);
//        batch.setTransformMatrix(camera.view);
//        decoratorFieldWIthCards.getStage().draw();
//        decoratorFieldWIthCards.getStage().act();
//        if (GameManager.counterOfPushedCards <= 1000) {
//
//        } else {
//            if (isPortraitMode) {
//                portraitPanel.getStage().draw();
//                portraitPanel.getStage().act();
//                if (portraitPanel.checkPushed()) {
//                    sleep();
//                    portraitPanel.pullPortraitPanel();
//                }
//                if (portraitPanel.checkPulled()) isPortraitMode = false;
//            } else
//            if (player.getX() + player.getWidth() <= GameInfo.WORLD_WIDTH) {
//                queryInput();
//                camera.update();
//                decoratorFieldWIthCards.getStage().draw();
//                decoratorFieldWIthCards.getStage().act();
//                batch.setProjectionMatrix(camera.projection);
//                batch.setTransformMatrix(camera.view);
//                batch.begin();
//                batch.draw(player, player.getX(), player.getY());
//                batch.end();
//                updatePlayer();
//            } else presidentIsDone();
//        }
    }

    private void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
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
        decoratorFieldWIthCards.getStage().dispose();

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


    private void setNewCurrentPresident() {
        if (presidentsListForQuestions.size > 0) {
            GameManager.setCurrentWrightPresident(presidentsListForQuestions.removeIndex(0) + 1);
            System.out.println(GameManager.currentWrightPresident);
        } else gameOver();
    }

    private void setInitialPlayerPosition() {
        player.setPosition(GameInfo.START_X_POSITION_OF_TETRIS_PLAYER, background.getHeight() / 2 - player.getHeight() / 2);
        camera.position.set(background.getWidth() / 2, background.getHeight() / 2, 0);
    }

    private void presidentIsDone() {
        if (checkAnswer()) {
            System.out.println("You are right");
            isPortraitMode = true;
            decoratorFieldWIthCards.pushRightPresidentNameCard();
        } else System.out.println("You are wrong");
        setNewCurrentPresident();
        setInitialPlayerPosition();
    }

    private boolean checkAnswer() {
        return ((player.getY() + 29 >= (GameManager.currentWrightPresident - GameManager.firstPresidentInRange) * 60 &&
                (player.getY() + 29 <= (GameManager.currentWrightPresident - GameManager.firstPresidentInRange) * 60 + 60)));
    }

    private void gameOver() {
        System.out.println("Game over");
    }

}
