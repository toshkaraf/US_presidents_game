package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    void drawScorePanel() {}

    void updateCamPosition() {
        if (isUpMove && camera.position.y <= background.getHeight() - GameInfo.WORLD_HEIGHT / 2 - GameInfo.STEP_FOR_TETRIS_Y)
            camera.position.y = camera.position.y + GameInfo.STEP_FOR_TETRIS_Y;
        if (isDownMove && camera.position.y >= GameInfo.WORLD_HEIGHT / 2 + GameInfo.STEP_FOR_TETRIS_Y)
            camera.position.y = camera.position.y - GameInfo.STEP_FOR_TETRIS_Y;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //TODO convert screen to world coordinates
        if (GameManager.renderMode != GameManager.RenderMode.ShowReviewPanel) {
            if (screenY > GameInfo.WORLD_HEIGHT / 2 && screenX < GameInfo.WORLD_WIDTH) {
                isDownMove = true;
                return true;
            }
            if (screenY < GameInfo.WORLD_HEIGHT / 2 && screenX < GameInfo.WORLD_WIDTH) {
                isUpMove = true;
                return true;
            }
            if (screenX > GameInfo.WORLD_WIDTH / 2) {
                GameManager.currentRightPresident = (int) Math.floor((camera.position.y - GameInfo.WORLD_HEIGHT/2 + (GameInfo.WORLD_HEIGHT - screenY)) / 60 + 1);
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