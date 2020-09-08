import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The type Animation runner.
 */
@SuppressWarnings("Duplicates")
public class AnimationRunner {
    /**
     * The constant SCREEN_TITLE.
     */
    public static final String SCREEN_TITLE = "Arkanoid";
    private GUI gui;
    private int framesPerSecond;

    /**
     * Instantiates a new Animation runner.
     */
    public AnimationRunner() {
        this.gui = new GUI(SCREEN_TITLE, GameLevel.SCREEN_WID, GameLevel.SCREEN_HEI);
        this.framesPerSecond = 60;
    }

    /**
     * Run.
     *
     * @param animation the animation
     */
    public void run(Animation animation) {
        Sleeper sleeper = new Sleeper();
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);

            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Gets gui.
     *
     * @return the gui
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Close game.
     */
    public void closeGame() {
        this.gui.close();
    }
}