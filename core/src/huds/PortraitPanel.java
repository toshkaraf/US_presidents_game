package huds;

//import com.awesometuts.jackthegiant.GameMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.toshkaraf.MainGame;

import cards.MenuCardToLeft;
import cards.MenuCardToRight;
import helpers.GameInfo;
import helpers.GameManager;


/**
 * Created by Fahir on 5/1/16.
 */
public class PortraitPanel {

    private MainGame game;
    private Stage stage;
    private Viewport gameViewport;

    private MenuCardToRight portraitCard;
    private MenuCardToRight nameCard;


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

        portraitCard = new MenuCardToRight(new Sprite(new Texture("cards/card_of_portrait.png")),
                -400, GameInfo.WORLD_WIDTH / 2 - 170, GameInfo.WORLD_HEIGHT / 2 - 190, "");

        nameCard = new MenuCardToLeft(new Sprite(new Texture("cards/card_of_president_red.png")),
                800, GameInfo.WORLD_WIDTH / 2 - 198, GameInfo.WORLD_HEIGHT / 2 - 170,
                GameManager.PRESIDENTS_ARRAY[GameManager.currentWrightPresident].getFirstName() + " " +
                        GameManager.PRESIDENTS_ARRAY[GameManager.currentWrightPresident].getLastName());
    }

    public void pushPortraitPanel(){
        portraitCard.setIsPush(true);
        nameCard.setIsPush(true);
    }

    public boolean checkPushed(){
        if (portraitCard.isCardPushed() && nameCard.isCardPushed()){
            portraitCard.setCardPushed(false);
            nameCard.setCardPushed(false);
            return true;
        }else return false ;
    }

    public void pullPortraitPanel(){
        portraitCard.setIsPush(false);
        nameCard.setIsPush(false);
    }

    public boolean checkPulled(){
        if (portraitCard.isCardPulled() && nameCard.isCardPulled()){
            portraitCard.setCardPulled(false);
            nameCard.setCardPulled(false);
            return true;
        }else return false ;
    }

    public Stage getStage() {
        return this.stage;
    }

} // UI Hud














































