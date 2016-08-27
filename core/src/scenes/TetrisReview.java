package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;
import huds.DecoratorForReview;
import huds.ReviewPanel;

/**
 * Created by Антон on 09.06.2016.
 */
public class TetrisReview extends HorisontalTetris {

    ReviewPanel reviewPanel;

    public TetrisReview(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        GameManager.renderMode = GameManager.RenderMode.PrepareField;
        decoratorWithCards = new DecoratorForReview(game);

        reviewPanel = new ReviewPanel(game);
        background = new Sprite(new Texture(Gdx.files.internal("Backgrounds/USAPresidentsBackground.png")),
                0, 0, GameInfo.WORLD_WIDTH, Math.round(60 * (GameManager.lastPresidentInRange - GameManager.firstPresidentInRange + 1)));
        batch = game.getBatch();
        camera = decoratorWithCards.getCamera();
        camera.position.set(background.getWidth() / 2, background.getHeight() / 2, 0);
        viewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT, camera);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        prepareField();
        switch (GameManager.renderMode) {
            case PrepareField:
                break;
            case ShowReviewPanel:
                reviewPanel.getStage().draw();
                reviewPanel.getStage().act();
                break;
            case PlayGame:
                updateCamPosition();
        }
    }

    @Override
    void drawScorePanel() {
    }

    void updateCamPosition() {
        if (isUpMove && camera.position.y <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - GameInfo.SLOW_STEP_FOR_TETRIS_Y)
            camera.position.y = camera.position.y + GameInfo.SLOW_STEP_FOR_TETRIS_Y;
        if (isDownMove && camera.position.y >= GameInfo.WORLD_HEIGHT / 2 + GameInfo.SLOW_STEP_FOR_TETRIS_Y)
            camera.position.y = camera.position.y - GameInfo.SLOW_STEP_FOR_TETRIS_Y;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (GameManager.renderMode != GameManager.RenderMode.ShowReviewPanel) {
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
                GameManager.currentRightPresident = (int) Math.floor((worldCoordinates.y) / 60);
                reviewPanel.createPanel();
                GameManager.renderMode = GameManager.RenderMode.ShowReviewPanel;
            }
            return true;
        } else {
            reviewPanel.pullPanel();
        }
        return false;
    }
}