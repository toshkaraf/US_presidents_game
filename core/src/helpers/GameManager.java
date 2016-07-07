package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import players.President;

/**
 * Created by Антон on 03.06.2016.
 */
public class GameManager {

    public int life = 3;
    public int score = 100;
    private Music music;

    public enum RenderMode {PrepareField, Portrait, PushNewHints, PlayGame, MoveCamToRightAnswer, ShowRightAnswer, MoveCamToStartPosition, ShowReviewPanel, HideReviewPanel}

    public enum TypeOfCard {BlueDate, RedDate, BlueName, RedName}

    private static GameManager ourInstance = new GameManager();
    static Array<Integer> presidentsListForQuestions = new Array<Integer>();
    public static final President[] PRESIDENTS_ARRAY = initializePresidentsArray();
    public static int firstPresidentInRange; // number in array (from 0)
    public static int lastPresidentInRange; // number in array
    public static int currentRightPresident; // number in array
    public static int quantityOfHints;
    public static RenderMode renderMode;
    public GameData gameData =  new GameData();
    private Json json = new Json();
    private FileHandle fileHandle = Gdx.files.local("bin/GameData.json");
    public Sound[] rightSounds = new Sound[6];
    public Sound[] wrongSounds = new Sound[6];
    int soundsCounter = 0;


    public GameManager() {

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

    void initSounds(){
        for (int i = 0; i <6; i++){
            rightSounds[i] = Gdx.audio.newSound(Gdx.files.internal("Sounds/right_answer/" + i + ".wav"));
            wrongSounds[i] = Gdx.audio.newSound(Gdx.files.internal("Sounds/wrong_answer/" + i + ".wav"));
        }
    }

    public Sound getRightSound() {
        if (soundsCounter < 5) soundsCounter++;
        else soundsCounter = 0;
        return rightSounds[soundsCounter];
    }

    public Sound getWrongSound() {
        if (soundsCounter < 5) soundsCounter++;
        else soundsCounter = 0;
        return wrongSounds[soundsCounter];
    }

    public static boolean initNewGame() {
        renderMode = RenderMode.PrepareField;
//        GameManager.firstPresidentInRange = firstPresidentInRange - 1;
//        GameManager.lastPresidentInRange = lastPresidentInRange - 1;
//        GameManager.quantityOfHints = quantityOfHints;
        if (presidentsListForQuestions.size != 0) presidentsListForQuestions.clear();
        initPresidentsListForQuestionsArray();
        return true;
    }

    public static void setLastPresidentInRange(int lastPresidentInRange) {
        GameManager.lastPresidentInRange = lastPresidentInRange - 1;
    }

    public static void setFirstPresidentInRange(int firstPresidentInRange) {
        GameManager.firstPresidentInRange = firstPresidentInRange - 1;

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

    public void playMusic() {
        if(music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Manhattan_Beach.mp3"));
            initSounds();
        }

        if(!music.isPlaying()) {
            music.setLooping(true);
            music.setVolume(.4f);
            music.play();
        }

    }

    public void stopMusic() {
        if(music.isPlaying()) {
            music.stop();
            music.dispose();
        }
    }

    public void saveData() {
    }

    public static GameManager getInstance() {
        return ourInstance;
    }
}
