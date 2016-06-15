package helpers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import cards.Card;

/**
 * Created by Антон on 10.06.2016.
 */
public class CardsStage extends Stage {

    public CardsStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
    }

    Array<Actor> actors = getActors();
    Card checkActor;

    public void actAndDraw(Camera camera) {
        for (Actor actor : actors) {
            if (!(actor instanceof Card)) {
                getBatch().setProjectionMatrix(camera.projection);
                getBatch().setTransformMatrix(camera.view);
                getBatch().begin();
                actor.draw(getBatch(),1);
                getBatch().end();
                actor.act(0.033333335f);
            } else {
                checkActor = (Card) actor;
                do {
//                    if (!checkActor.isCardPushed()) {
                    getBatch().setProjectionMatrix(camera.projection);
                    getBatch().setTransformMatrix(camera.view);
                    getBatch().begin();
                    actor.draw(getBatch(), 1);
                    getBatch().end();
                    actor.act(0.033333335f);
//                    long i=0;
//                    do {i++;} while (i!=10000000);
//                        return;}
                }while (!checkActor.isCardPushed());
            }
            long i=0;
            do {i++;} while (i!=10000000);
        }
    }

//    @Override
//    public void act(float delta) {
//        for (Actor actor : actors) {
//            if (!(actor instanceof Card)) {
//                actor.act(delta);
//            } else {
//                checkActor = (Card) actor;
//                do {
//                    getBatch().begin();
//                    actor.draw(getBatch(),1);
//                    getBatch().end();
//                    actor.act(delta);
//                }while (!checkActor.isCardPushed());
//            }
//        }
//    }

//    @Override
//    public void draw() {
//        for (Actor actor : actors) {
//            Class actorClass = actor.getClass();
//            if (!(actor instanceof Card)) {
//                getBatch().begin();
//                actor.draw(getBatch(), 1);
//                getBatch().end();
//            } else {
//                checkActor = (Card) actor;
//                do {
//                    if (!checkActor.isCardPushed()) {
//                        getBatch().begin();
//                        actor.draw(getBatch(), 1);
//                        actor.act();
//                        getBatch().end();
//                        break;
//                    }
//                } while (true);

//            }
//        }
//    }
}
