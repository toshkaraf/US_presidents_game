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

    public static Array<President> initializePresidentsArray() {
        Array<President> presidentsArray = new Array<President>();
//        String prompts1[] = new String[]{"Первая половина века", "Лучший президент", "Классаня работа"};
//        String prompts2[] = new String[]{"Первая половина века", "Лучший президент", "Классаня работа"};
//        String prompts3[] = new String[]{"Первая половина века", "Лучший президент", "Классаня работа"};
//        presidentsArray.add(new President("George", "Washington", "players/tetris_player.jpg", 1, 1789, 1870, prompts1));
//        presidentsArray.add(new President("George2", "Washington2", "players/tetris_player.jpg", 1, 1870, 1940, prompts2));
//        presidentsArray.add(new President("George3", "Washington3", "players/tetris_player.jpg", 1, 1940, 2017, prompts3));
//        presidentsArray.shuffle();

        Json json = new Json();
        Array<JsonValue> list = json.fromJson(Array.class, Gdx.files.internal("data/presidents.json"));
        for (JsonValue v : list) {
            presidentsArray.add(json.readValue(President.class, v));
        }


//        Json json = new Json();
//        ListOfPresidents list = json.fromJson(ListOfPresidents.class, Gdx.files.internal("data/presidents.json"));
//        presidentsArray = list.getListOfPresidents();

        return presidentsArray;
    }
}
