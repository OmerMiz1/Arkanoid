import biuoop.KeyboardSensor;
import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow {
    private KeyboardSensor keyboard;
    private AnimationRunner runner;
    private SpriteCollection lastScreen = new SpriteCollection();
    private Counter score = new Counter();
    private Counter lives = new Counter(7);

    /**
     * Instantiates a new Game flow.
     *
     * @param ar the ar
     * @param ks the ks
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.runner = ar;
        this.keyboard = ks;
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        GameLevel level;
        for (LevelInformation levelInfo : levels) {
            level = new GameLevel(levelInfo, this.keyboard, this.runner, this.score, this.lives);
            level.initialize();
            this.lastScreen = level.getSprites();

            // keep game running while there are blocks and lives.
            while (level.getRemainingBlocks().getValue() > 0 && this.lives.getValue() > 0) {
                level.playOneTurn();
            }

            // case out of lives - player lost, otherwise player beat level +100 to score and continue.
            if (this.lives.getValue() == 0) {
                level.getPaddle().setSpeed(0);
                break;
            }
            this.score.increase(100);
        }
    }

    /**
     * Last screen sprites sprite collection.
     *
     * @return the sprite collection
     */
    public SpriteCollection lastScreenSprites() {
        return this.lastScreen;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return this.score.getValue();
    }

    /**
     * Gets lives.
     *
     * @return the lives
     */
    public int getLives() {
        return this.lives.getValue();
    }

    /**
     * Reset.
     */
    public void reset() {
        this.lives.clear();
        this.score.clear();
        this.lives.increase(7);
    }

}