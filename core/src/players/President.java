package players;

/**
 * Created by Антон on 03.06.2016.
 */
public class President {


    public President() {
    }

    public President(String firstName, String secondName, String portraitFileName,
                     int number, int initialDate, int finfleDate, String[] prompts) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.portraitFileName = portraitFileName;
        this.number = number;
        this.initialDate = initialDate;
        this.finalDate = finfleDate;
        this.prompts = prompts;
    }

    String firstName;
    String secondName;
    String portraitFileName;
    int number;
    int initialDate;
    int finalDate;
    String[] prompts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPortraitFileName() {
        return portraitFileName;
    }

    public void setPortraitFileName(String portraitFileName) {
        this.portraitFileName = portraitFileName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(int initialDate) {
        this.initialDate = initialDate;
    }

    public int getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(int finalDate) {
        this.finalDate = finalDate;
    }

    public String[] getPrompts() {
        return prompts;
    }

    public void setPrompts(String[] prompts) {
        this.prompts = prompts;
    }
}
