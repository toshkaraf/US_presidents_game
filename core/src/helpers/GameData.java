package helpers;

/**
 * Created by Антон on 06.07.2016.
 */
public class GameData {
    int highScore;
    boolean musicOn;
    boolean sounds = true;

    public boolean isSounds() {
        return sounds;
    }

    public void setSounds(boolean sounds) {
        this.sounds = sounds;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }
}
