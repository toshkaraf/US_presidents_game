package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Антон on 17.06.2016.
 */
public class MyFontGenerator {

    public static BitmapFont getFont(String fontFileName, int size) {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal(fontFileName));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = size;

        return generator.generateFont(parameter);
    }
}
