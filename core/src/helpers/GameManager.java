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

    public static int firsPresidentInRange;
    public static int lastPresidentInRange;
    public static int currentWrightPresident;
    public static int quantityOfHints;

    public static void setFirsPresidentInRange(int firsPresidentInRange) {
        GameManager.firsPresidentInRange = firsPresidentInRange-1;
    }

    public static void setLastPresidentInRange(int lastPresidentInRange) {
        GameManager.lastPresidentInRange = lastPresidentInRange-1;
    }

    public static void setCurrentWrightPresident(int currentWrightPresident) {
        GameManager.currentWrightPresident = currentWrightPresident-1;
    }

    public static void setQuantityOfHints(int quantityOfHints) {
        GameManager.quantityOfHints = quantityOfHints;
    }



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
}
