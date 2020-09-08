import biuoop.KeyboardSensor;

/**
 * The type Show hi scores task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private HighScoresAnimation hsa;
    private KeyboardSensor ks;

    /**
     * Instantiates a new Show hi scores task.
     *
     * @param runner         the runner
     * @param highScoresAnim the high scores anim
     * @param ks             the ks
     */
    public ShowHiScoresTask(AnimationRunner runner, HighScoresAnimation highScoresAnim, KeyboardSensor ks) {
        this.runner = runner;
        this.hsa = highScoresAnim;
        this.ks = ks;
    }

    /**
     * Run the high score animation task.
     *
     * @return Void null.
     */
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.hsa, this.ks, "space"));
        return null;
    }
}