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

    private static GameManager ourInstance = new GameManager();

    public static int counterOfPushedCards = 0;

    public static final President[] PRESIDENTS_ARRAY = initializePresidentsArray();

    public static int firstPresidentInRange; // number in array (fmrom 0)
    public static int lastPresidentInRange; // number in array
    public static int currentWrightPresident; // number in array
    public static int quantityOfHints;

    public static GameManager getInstance() {
        return ourInstance;
    }

    private static President[] initializePresidentsArray() {
        President[] presidentsArray = new President[44];

        Json json = new Json();
        int presidentNumber = 0;
        Array<JsonValue> list = json.fromJson(Array.class, Gdx.files.internal("data/presidents.json"));
        for (JsonValue v : list) {
            presidentsArray[presidentNumber++] = json.readValue(President.class, v);
        }
        return presidentsArray;
    }

    public static void setFirstPresidentInRange(int firstPresidentInRange) {
        GameManager.firstPresidentInRange = firstPresidentInRange - 1;
    }

    public static void setLastPresidentInRange(int lastPresidentInRange) {
        GameManager.lastPresidentInRange = lastPresidentInRange - 1;
    }

    public static void setCurrentWrightPresident(int currentWrightPresident) {
        GameManager.currentWrightPresident = currentWrightPresident - 1;
    }

    public static void setQuantityOfHints(int quantityOfHints) {
        GameManager.quantityOfHints = quantityOfHints;
    }
}
