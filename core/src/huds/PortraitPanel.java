package huds;

//import com.awesometuts.jackthegiant.GameMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;
import helpers.MyFontGenerator;
import helpers.RenderModeAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


/**
 * Created by Fahir on 5/1/16.
 */
public class PortraitPanel {

    private MainGame game;
    private Stage stage;
    private Viewport gameViewport;
    private Table portraitCard;
    private Table nameCard;
    Texture bg = new Texture("cards/card_of_portrait.png");
    Texture bgName = new Texture("cards/card_of_president_red.png");


    public PortraitPanel(MainGame game) {

        this.game = game;
        gameViewport = new FitViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                new OrthographicCamera());
        stage = new Stage(gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createPortraitPanel();

        stage.addActor(portraitCard);
        stage.addActor(nameCard);
    }

    private void createPortraitPanel() {

        portraitCard = new Table();
        portraitCard.setBackground(new SpriteDrawable(new Sprite(bg)));
        portraitCard.setBounds(portraitCard.getX(), portraitCard.getY(), bg.getWidth(), bg.getHeight());
        portraitCard.setPosition(-portraitCard.getWidth(), GameInfo.WORLD_HEIGHT / 2 - 190);
        portraitCard.add(new Image(new Texture(GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getPortraitFileName()))).padBottom(70);
        portraitCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH / 2 - 170, portraitCard.getY(), 1f),
                delay(1f), moveTo(GameInfo.WORLD_WIDTH, portraitCard.getY(), 1f)));

        nameCard = new Table();
        nameCard.setBackground(new SpriteDrawable(new Sprite(bgName)));
        nameCard.setBounds(nameCard.getX(), nameCard.getY(), bgName.getWidth(), bgName.getHeight());
        nameCard.setPosition(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT / 2 - 170);
        nameCard.add(new Label(GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getFirstName() + " " +
                GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getLastName(),
                new Label.LabelStyle(MyFontGenerator.getFont("fonts/arial.ttf", 20), Color.WHITE))).center();
        nameCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH / 2 - 198, nameCard.getY(), 1f),
                delay(1f), moveTo(-nameCard.getWidth(), nameCard.getY(), 1f),new RenderModeAction(GameManager.RenderMode.PullOldHints)));
    }

    public Stage getStage() {
        return this.stage;
    }

} // UI Hud














































