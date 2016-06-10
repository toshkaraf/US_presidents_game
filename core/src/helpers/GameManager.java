package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import players.President;

/**
 * Created by Антон on 03.06.2016.
 */
public class GameManager {

    public static int counterOfPushedCards = 0; // count all pushed cards of both kinds on the right and left part of screen (numbers & dates)

    public static final Array<President> PRESIDENTS_ARRAY = initializePresidentsArray();

    private static GameManager ourInstance = new GameManager();

    public static GameManager getInstance() {
        return ourInstance;
    }

    private static Array<President> initializePresidentsArray() {
        Array<President> presidentsArray = new Array<President>();

        Json json = new Json();
        Array<JsonValue> list = json.fromJson(Array.class, Gdx.files.internal("data/presidents.json"));
        for (JsonValue v : list) {
            presidentsArray.add(json.readValue(President.class, v));
        }
        return presidentsArray;
    }
}
