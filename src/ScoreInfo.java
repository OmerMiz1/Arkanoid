import java.io.Serializable;

/**
 * The type Score info.
 */
public class ScoreInfo implements Comparable<ScoreInfo>, Serializable {
    private String name;
    private int score;

    /**
     * Instantiates a new Score info.
     *
     * @param playerName  the player name
     * @param playerScore the player score
     */
    public ScoreInfo(String playerName, int playerScore) {
        this.name = playerName;
        this.score = playerScore;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets score.
     */
    public void setScore() {
        this.score = score;
    }

    /**
     * Compares 2 ScoreInfo by score value.
     *
     * @return 1 if bigger, -1 if smaller, 0 if equals.
     */
    @Override
    public int compareTo(ScoreInfo other) {
        if (getScore() > other.getScore()) {
            return -1;
        } else if (getScore() < other.getScore()) {
            return 1;
        }
        return 0;
    }
}