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

import java.util.Map;

import helpers.GameInfo;
import helpers.GameManager;
import players.President;

/**
 * Created by Антон on 01.06.2016.
 */
public class UsaPresidentTetrisVerticalDrop extends ScreenAdapter {

    SpriteBatch batch;
    Texture background;
    Array<President> presidentPlayers;
    Map <Float,String> namesOfDonePresidents;
    Sprite player;
    private Viewport viewport;
    private OrthographicCamera camera;
    President currentPlayerPresident;

    public UsaPresidentTetrisVerticalDrop(MainGame game) {
        batch = game.getBatch();
        background = new Texture(Gdx.files.internal("Backgrounds/usa_background.jpg"));
        player = new Sprite(new Texture(Gdx.files.internal("players/tetris_player.jpg")));
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);
        viewport.apply();
        presidentPlayers = GameManager.initializePresidentsArray();
        setNewPresidentAndPosition();
    }


    @Override
    public void render(float delta) {
        if (player.getY() >= 0) {
            Gdx.gl.glClearColor(1, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            queryInput();
            camera.update();
            batch.setProjectionMatrix(camera.projection);
            batch.setTransformMatrix(camera.view);
            batch.begin();
            batch.draw(background, 0, 0);
            updatePlayer();
            batch.draw(player, player.getX(), player.getY());
            batch.end();
        } else presidentIsDone();
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
        background.dispose();

    }

    //check for bounds
    //update player's and cameras coordinates
    private void queryInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getX() > 0) {
            player.setX(player.getX() - GameInfo.STEP_FOR_TETRIS_X);
            if ((camera.position.x >= GameInfo.WORLD_WIDTH / 2 + 6) &&
                    (player.getX() <= background.getWidth() - GameInfo.WORLD_WIDTH / 2 - player.getWidth() / 2))
                camera.position.x = camera.position.x - GameInfo.STEP_FOR_TETRIS_X;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getX() < background.getWidth() - player.getWidth()) {
            player.setX(player.getX() + GameInfo.STEP_FOR_TETRIS_X);
            if ((camera.position.x <= background.getWidth() - GameInfo.WORLD_WIDTH / 2 - 6) &&
                    (player.getX() >= GameInfo.WORLD_WIDTH / 2 - player.getWidth() / 2))
                camera.position.x = camera.position.x + GameInfo.STEP_FOR_TETRIS_X;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.setY(player.getY() - GameInfo.FAll_STEP_FOR_TETRIS_Y);

        }

    }

    private void updatePlayer() {
        player.setY(player.getY() - GameInfo.SLOW_STEP_FOR_TETRIS_Y);
    }

    private void setNewPresidentAndPosition() {
        if (presidentPlayers.size > 0) currentPlayerPresident = presidentPlayers.removeIndex(0);
        else gameOver();
        player.setPosition(background.getWidth() / 2 - player.getWidth() / 2 + 50, GameInfo.WORLD_HEIGHT - 100);
        camera.position.x = background.getWidth() / 2 + 50;
        camera.position.y = background.getHeight() / 2;
    }

    private void presidentIsDone() {
        if (checkAnswer()) {
            System.out.println("You are right");

        }

        else System.out.println("You are wrong");
        setNewPresidentAndPosition();
    }

    private boolean checkAnswer(){
     return ((player.getX() >= convertDateToCoordinateX(currentPlayerPresident.getInitialDate())) &&
             (player.getX() <= convertDateToCoordinateX(currentPlayerPresident.getFinalDate())));
    }

    private void gameOver() {
        System.out.println("Game over");
    }

    private float convertDateToCoordinateX(int date){
        return background.getWidth()/228*(date-GameInfo.INITIAL_DATE_OF_FIRST_PRESIDENT);
    }
}
