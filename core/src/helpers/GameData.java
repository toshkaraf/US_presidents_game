package helpers;

/**
 * Created by Антон on 06.07.2016.
 */
public class GameData {
    int highScore;
    boolean musicOn = true;
    //TODO make enum
    String level;

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
