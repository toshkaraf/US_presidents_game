package helpers;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Created by Антон on 24.06.2016.
 */
public class RenderModeAction extends Action {

    GameManager.RenderMode rendermode;

    public RenderModeAction(GameManager.RenderMode rendermode) {
        this.rendermode = rendermode;
    }

    @Override
    public boolean act(float delta) {
        GameManager.renderMode = rendermode;
        return true;
    }
}
