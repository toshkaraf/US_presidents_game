//package cards;
//
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//
//import helpers.GameInfo;
//import helpers.GameManager;
//
///**
// * Created by Антон on 09.06.2016.
// */
//public class PresidentNameCard extends Card {
//
//    public PresidentNameCard(Sprite card, int numberOfPresident) {
//        super(card, numberOfPresident);
//        x = GameInfo.WORLD_WIDTH;
//        finalPositionOfCard_X = 440;
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlfa) {
//        if (super.draw(batch)) isCardPushed = true;
//        bitmapFont.draw(batch, GameManager.PRESIDENTS_ARRAY[number].getFirstName() +
//                " " + GameManager.PRESIDENTS_ARRAY[number].getLastName(), x + 35, y + 35);
//    }
//
////    public void act(float delta) {
////        if (x >= finalPositionOfCard_X) x = x - GameInfo.STEP_FOR_TETRIS_X;
////        setBounds(x, this.y, card.getWidth(), card.getHeight());
////    }
//}