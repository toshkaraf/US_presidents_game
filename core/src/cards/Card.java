//package cards;
//
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.scenes.scene2d.ui.Button;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//
//import helpers.GameManager;
//
///**
// * Created by Антон on 07.06.2016.
// */
//public class Card extends Table{
//    int number;
//    float x,y;
//    Sprite card;
//    BitmapFont bitmapFont;
//    int finalPositionOfCard_X;
//    boolean isCardPushed = false;
//    boolean isCardPulled = false;
//    boolean isPush = true;
//
//    public Card (Sprite card){
//        this.card = card;
//        bitmapFont = new BitmapFont();
//    }
//
//    public Card(Sprite card, int numberOfPresident) {
//        this.number = numberOfPresident;
//        this.card = card;
//        bitmapFont = new BitmapFont();
//        y = (numberOfPresident-GameManager.firstPresidentInRange) * card.getHeight();
//        setBounds(x, this.y, card.getWidth(), card.getHeight());
//    }
//
//    public boolean draw(Batch batch) {
//        batch.draw(card, x, y, getWidth(),getHeight());
//        return (x <= finalPositionOfCard_X) ;
//    }
//
//    public boolean isCardPushed() {
//        return isCardPushed;
//    }
//
//    public void setIsPush(boolean push) {
//        this.isPush = push;
//    }
//
//    public boolean isCardPulled() {
//        return isCardPulled;
//    }
//
//    public void setCardPulled(boolean cardPulled) {
//        isCardPulled = cardPulled;
//    }
//
//    public void setCardPushed(boolean cardPushed) {
//        isCardPushed = cardPushed;
//    }
//
//}
