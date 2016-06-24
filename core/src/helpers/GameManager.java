package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.sun.org.apache.bcel.internal.generic.INEG;

import players.President;

/**
 * Created by Антон on 03.06.2016.
 */
public class GameManager {

    public enum RenderMode {PrepareField, Portrait, PushNewHints, SetNewPlayer, MoveCamToRightAnswer, ShowRightAnswer, MoveCamToStartPosition}

    public enum TypeOfCard {BlueDate, RedDate, BlueName, RedName}

    private static GameManager ourInstance = new GameManager();
    static Array<Integer> presidentsListForQuestions = new Array<Integer>();
    public static final President[] PRESIDENTS_ARRAY = initializePresidentsArray();
    public static int firstPresidentInRange; // number in array (from 0)
    public static int lastPresidentInRange; // number in array
    public static int currentRightPresident; // number in array
    public static int quantityOfHints;
    public static RenderMode renderMode;


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

    public static void initNewGame(int firstPresidentInRange, int lastPresidentInRange, int quantityOfHints) {
        renderMode = RenderMode.PrepareField;
        GameManager.firstPresidentInRange = firstPresidentInRange - 1;
        GameManager.lastPresidentInRange = lastPresidentInRange - 1;
        GameManager.quantityOfHints = quantityOfHints;
    }

    public static void initPresidentsListForQuestionsArray() {
        for (int i = firstPresidentInRange; i <= lastPresidentInRange; i++)
            presidentsListForQuestions.add(i);
        presidentsListForQuestions.shuffle();
    }

    public static boolean setNewCurrentPresident(boolean isRightAnswer) {
        if (presidentsListForQuestions.size > 0) {
            if (isRightAnswer) {
                GameManager.currentRightPresident = presidentsListForQuestions.removeIndex(0);
                System.out.println(GameManager.currentRightPresident + 1);
                return true;
            } else {
                presidentsListForQuestions.add(GameManager.currentRightPresident);
                presidentsListForQuestions.shuffle();
                GameManager.currentRightPresident = presidentsListForQuestions.removeIndex(0);
                System.out.println(GameManager.currentRightPresident + 1);
                return true;
            }
        } else return false;
    }

    public static Array<Integer> getPresidentsListForQuestions() {
        return presidentsListForQuestions;
    }

    public static Array<Integer> getCloneOfPresidentsListForQuestions() {
        Array<Integer> tempArray = new Array<Integer>();
        for (Integer number : presidentsListForQuestions) {
            tempArray.add(number);
        }
        return tempArray;
    }

    public static GameManager getInstance() {
        return ourInstance;
    }
}
