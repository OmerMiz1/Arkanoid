import java.util.List;

/**
 * The type Level set.
 */
public class LevelSet {

    /**
     * The Key.
     */
    private String key;
    /**
     * The Description.
     */
    private String description;
    /**
     * The Levels.
     */
    private List<LevelInformation> levels;

    /**
     * Instantiates a new Level set.
     *
     * @param key         the key
     * @param description the description
     * @param levels      the levels
     */
    public LevelSet(String key, String description, List<LevelInformation> levels) {
        this.key = key;
        this.description = description;
        this.levels = levels;
    }

    /**
     * Gets levels.
     *
     * @return the levels
     */
    public List<LevelInformation> getLevels() {
        return this.levels;
    }

    /**
     * Key string.
     *
     * @return the string
     */
    public String key() {
        return this.key;
    }

    /**
     * Description string.
     *
     * @return the string
     */
    public String description() {
        return this.description;
    }
}
