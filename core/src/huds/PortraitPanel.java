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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;
import helpers.RenderModeAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


/**
 * Created by Fahir on 5/1/16.
 */
public class PortraitPanel {

    private MainGame game;
    private Stage stage;
    private Viewport gameViewport;
    private Table portraitCard;
    private Table nameCard;
    Sprite bg = new Sprite(new Texture("cards/card_of_portrait.png"));

    Sprite bgName = new Sprite(new Texture("cards/card_of_president_red.png"));
    Sprite portrait;


    public PortraitPanel(MainGame game) {
        this.game = game;

        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                new OrthographicCamera());
        stage = new Stage(gameViewport, game.getBatch());

        createPortraitPanel();

        stage.addActor(portraitCard);
        stage.addActor(nameCard);
    }

    private void createPortraitPanel() {

        portraitCard = new Table();

        portraitCard.setBackground(new SpriteDrawable(bg));
        if (Gdx.graphics.getWidth()/Gdx.graphics.getHeight() > GameInfo.WORLD_WIDTH/GameInfo.WORLD_HEIGHT)
            bg.setSize(bg.getWidth()/GameInfo.RESIZE_PROPORTION_X*GameInfo.RESIZE_PROPORTION_Y, bg.getHeight());
        else bg.setSize(bg.getWidth(), bg.getHeight()/GameInfo.RESIZE_PROPORTION_Y*GameInfo.RESIZE_PROPORTION_X);
        portraitCard.setBounds(portraitCard.getX(), portraitCard.getY(), bg.getWidth(), bg.getHeight());
        portraitCard.setPosition(-portraitCard.getWidth(), GameInfo.WORLD_HEIGHT / 2 - portraitCard.getWidth()/2);
        portrait = new Sprite(new Texture(GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getPortraitFileName()));
        if (Gdx.graphics.getWidth()/Gdx.graphics.getHeight() > GameInfo.WORLD_WIDTH/GameInfo.WORLD_HEIGHT)
            portrait.setSize(portrait.getWidth()/GameInfo.RESIZE_PROPORTION_X*GameInfo.RESIZE_PROPORTION_Y, portrait.getHeight());
        else portrait.setSize(portrait.getWidth(), portrait.getHeight()/GameInfo.RESIZE_PROPORTION_Y*GameInfo.RESIZE_PROPORTION_X);

        portraitCard.add(new Image(new SpriteDrawable(portrait))).padBottom(portraitCard.getHeight()/5.4f);
        portraitCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH / 2 - portraitCard.getWidth()/2, portraitCard.getY(), .5f),
                delay(1.5f), moveTo(GameInfo.WORLD_WIDTH, portraitCard.getY(), .5f)));

        nameCard = new Table();
        nameCard.setBackground(new SpriteDrawable(bgName));
        nameCard.setBounds(nameCard.getX(), nameCard.getY(), bgName.getWidth(), bgName.getHeight());
        nameCard.setPosition(GameInfo.WORLD_WIDTH, portraitCard.getY() + portraitCard.getHeight()/19f);
        nameCard.add(new Label(GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getFirstName() + " " +
                GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getLastName(),
                new Label.LabelStyle(GameInfo.DATE_FONT, Color.WHITE))).center();
        nameCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH / 2 - nameCard.getWidth()/2, nameCard.getY(), .5f),
                delay(1.5f), moveTo(-nameCard.getWidth(), nameCard.getY(), .5f), new RenderModeAction(GameManager.RenderMode.PushNewHints)));
    }

    public Stage getStage() {
        return this.stage;
    }

}














































