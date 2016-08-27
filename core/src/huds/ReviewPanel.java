package huds;

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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import helpers.GameInfo;
import helpers.GameManager;
import helpers.RenderModeAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by Антон on 03.07.2016.
 */
public class ReviewPanel {

    private MainGame game;
    private Stage stage;
    private Viewport gameViewport;
    private Table reviewCard;
    private Table nameCard;
    Texture bgName = new Texture("cards/card_of_president_red.png");
    Sprite portrait;
    Sprite bg = new Sprite(new Texture("cards/card_of_portrait.png"));

    public ReviewPanel(MainGame game) {
        this.game = game;

        gameViewport = new StretchViewport(GameInfo.WORLD_WIDTH, GameInfo.WORLD_HEIGHT,
                new OrthographicCamera());
        stage = new Stage(gameViewport, game.getBatch());
    }

    public void createPanel() {
        stage.clear();

        reviewCard = new Table();
//        reviewCard.setDebug(true);
        bg.setSize(650, 450);
        reviewCard.setBackground(new SpriteDrawable(bg));
        reviewCard.setBounds(reviewCard.getX(), reviewCard.getY(), bg.getWidth(), bg.getHeight());

        Table topPart = new Table();
//        topPart.setDebug(true);
            portrait = new Sprite(new Texture(GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getPortraitFileName()));
//            portrait.setSize(180, 230);
            if (Gdx.graphics.getWidth()/Gdx.graphics.getHeight() > GameInfo.WORLD_WIDTH/GameInfo.WORLD_HEIGHT)
                portrait.setSize(180/GameInfo.RESIZE_PROPORTION_X*GameInfo.RESIZE_PROPORTION_Y, 230);
            else portrait.setSize(180, portrait.getHeight()/GameInfo.RESIZE_PROPORTION_Y*GameInfo.RESIZE_PROPORTION_X);

            Table mainInfo = new Table();
                Label labelDate = new Label(GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getInitialDate() + " - " +
                GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getFinalDate() + "\n", new Label.LabelStyle(GameInfo.NAME_FONT, Color.RED));
                labelDate.setAlignment(Align.center);
                mainInfo.add(labelDate);
                mainInfo.row();
                Label info = new Label("Age: " + GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getAge() +
                "\nParty: " + GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getParty(),
                new Label.LabelStyle(GameInfo.NAME_FONT, Color.WHITE));
                info.setAlignment(Align.center);
                mainInfo.add(info);
            topPart.add(new Image(new SpriteDrawable(portrait))).expand();
            topPart.add(mainInfo).expandX().padTop(90).padRight(45);
        reviewCard.add(topPart).expand().fill();
        reviewCard.row();

            Label factsLabel = new Label(GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getPrompts()[0] + "\n" +
                GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getPrompts()[1] + "\n" +
                GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getPrompts()[2],
                new Label.LabelStyle(GameInfo.DATE_FONT, Color.WHITE));
                factsLabel.setWrap(true);
        reviewCard.add(factsLabel).width((bg.getWidth() - 65)).top().padBottom(15);
        reviewCard.setPosition(-reviewCard.getWidth(), GameInfo.WORLD_HEIGHT / 2 - bg.getHeight() / 2);
        reviewCard.addAction(moveTo(GameInfo.WORLD_WIDTH / 2 - reviewCard.getWidth() / 2, reviewCard.getY(), .5f));

        nameCard = new Table();
        nameCard.setBackground(new SpriteDrawable(new Sprite(bgName)));
        nameCard.setBounds(nameCard.getX(), nameCard.getY(), bgName.getWidth(), bgName.getHeight());
        nameCard.setPosition(GameInfo.WORLD_WIDTH, reviewCard.getY() + reviewCard.getHeight() - nameCard.getHeight() - 30);
        nameCard.add(new Label(GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getNumber() + ". " + GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getFirstName() + " " +
                GameManager.PRESIDENTS_ARRAY[GameManager.currentRightPresident].getLastName(),
                new Label.LabelStyle(GameInfo.DATE_FONT, Color.WHITE))).center();
        nameCard.addAction(moveTo(GameInfo.WORLD_WIDTH / 2 - 50, nameCard.getY(), .5f));

        stage.addActor(reviewCard);
        stage.addActor(nameCard);
    }

    public void pullPanel() {
        reviewCard.addAction(sequence(moveTo(GameInfo.WORLD_WIDTH, reviewCard.getY(), .5f), new RenderModeAction(GameManager.RenderMode.PlayGame)));
        nameCard.addAction(moveTo(-nameCard.getWidth(), nameCard.getY(), .5f));
    }

    public Stage getStage() {
        return this.stage;
    }
}
