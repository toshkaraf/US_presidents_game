package huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;

/**
 * Created by Антон on 06.07.2016.
 */
public class ScorePanel {

    Viewport gameViewport;
    Stage stage;
    Table scoreTable;
    Label scoreLabel, lifeLabel;

    public ScorePanel(MainGame game) {

        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                new OrthographicCamera());
        stage = new Stage(gameViewport, game.getBatch());

        scoreTable = new Table().top().padTop(20);
        scoreTable.setFillParent(true);
        lifeLabel = new Label(String.valueOf(GameManager.getInstance().life),new Label.LabelStyle(GameInfo.DATE_FONT, Color.WHITE));
        scoreLabel = new Label(String.valueOf(GameManager.getInstance().score),new Label.LabelStyle(GameInfo.DATE_FONT, Color.WHITE));
        scoreTable.add(lifeLabel).spaceRight(100);
        scoreTable.add(scoreLabel);
        scoreTable.setBounds(scoreTable.getX(),scoreTable.getY(),scoreTable.getWidth(),scoreTable.getHeight());

        stage.addActor(scoreTable);
    }


    public void incrementScore(int score) {
        GameManager.getInstance().score += score;
        scoreLabel.setText(String.valueOf(GameManager.getInstance().score));
    }

    public void decrementLife() {
        GameManager.getInstance().life--;
        lifeLabel.setText(String.valueOf(GameManager.getInstance().life));
    }

    public Stage getStage() {
        return stage;
    }
}
