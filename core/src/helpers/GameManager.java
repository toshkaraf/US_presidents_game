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
    static Array<Integer> presidentsListForQuestions = new Array<Integer>();
    public static int counterOfPushedCards = 0;

    public static final President[] PRESIDENTS_ARRAY = initializePresidentsArray();

    public static int firstPresidentInRange; // number in array (from 0)
    public static int lastPresidentInRange; // number in array
    public static int currentRightPresident; // number in array
    public static int quantityOfHints;

    public enum RenderMode {PrepareField, Portrait, PullOldHints, PushNewHints, SetNewPlayer, MoveCamToRightAnswer, ShowRightAnswer, MoveCamToStartPosition}
    public enum TypeOfCard {BlueDate, RedDate, BlueName, RedName}

    public static RenderMode renderMode = RenderMode.PrepareField;


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

    public static void initPresidentsListForQuestionsArray() {
        for (int i = GameManager.firstPresidentInRange; i <= GameManager.lastPresidentInRange; i++)
            presidentsListForQuestions.add(i);
        presidentsListForQuestions.shuffle();
    }

    public static boolean setNewCurrentPresident() {
        if (presidentsListForQuestions.size > 0) {
            GameManager.setCurrentRightPresident(presidentsListForQuestions.removeIndex(0) + 1);
            System.out.println(GameManager.currentRightPresident);
            return true;
        } else return false;
    }

    public static void setFirstPresidentInRange(int firstPresidentInRange) {
        GameManager.firstPresidentInRange = firstPresidentInRange - 1;
    }

    public static void setLastPresidentInRange(int lastPresidentInRange) {
        GameManager.lastPresidentInRange = lastPresidentInRange - 1;
    }

    public static void setCurrentRightPresident(int currentRightPresident) {
        GameManager.currentRightPresident = currentRightPresident - 1;
    }

    public static void setQuantityOfHints(int quantityOfHints) {
        GameManager.quantityOfHints = quantityOfHints;
    }

    public static Array<Integer> getPresidentsListForQuestions() {
        return presidentsListForQuestions;
    }

    public static GameManager getInstance() {
        return ourInstance;
    }
}
