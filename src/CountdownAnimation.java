import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * The type Countdown animation.
 */
// The CountdownAnimation will display the given gameScreen,
// for numOfSeconds seconds, and on top of them it will show
// a countdown from countFrom back to 1, where each number will
// appear on the screen for (numOfSeconds / countFrom) secods, before
// it is replaced with the next one.
public class CountdownAnimation implements Animation {
    /**
     * The constant FONT_SIZE.
     */
    public static final int FONT_SIZE = 64;
    /**
     * The constant DEFAULT_COLOR.
     */
    public static final Color DEFAULT_COLOR = Color.RED;
    private boolean stop = false;
    private Counter secondsLeft;
    private SpriteCollection gameSprites;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param countFrom  the count from
     * @param gameScreen the game screen
     */
    public CountdownAnimation(int countFrom, SpriteCollection gameScreen) {
        this.secondsLeft = new Counter(countFrom);
        this.gameSprites = gameScreen;
    }

    /**
     * Instantiates a new Countdown animation (DEFAULT).
     *
     * @param gameScreen the game screen
     */
    public CountdownAnimation(SpriteCollection gameScreen) {
        this(3, gameScreen);
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        Sleeper sleeper = new Sleeper();
        Integer secLeft = this.secondsLeft.getValue();
        d.setColor(DEFAULT_COLOR);
        this.gameSprites.drawAllOn(d);
        int x = (d.getWidth() / 2) - (FONT_SIZE / 2);
        int y = d.getHeight() - d.getHeight() / 3;

        if (this.secondsLeft.getValue() < 0) {
            this.stop = true;
        } else if (this.secondsLeft.getValue() == 0) {
            d.setColor(Color.BLACK);
            d.drawText(x - (FONT_SIZE / 2) + 1, y - 1, "GO", FONT_SIZE);
            d.drawText(x - (FONT_SIZE / 2) - 1, y + 1, "GO", FONT_SIZE);
            d.setColor(DEFAULT_COLOR);
            d.drawText(x - (FONT_SIZE / 2), y, "GO", FONT_SIZE);
        } else {
            d.setColor(Color.BLACK);
            d.drawText(x + 1, y - 1, secLeft.toString() + "..", FONT_SIZE);
            d.drawText(x - 1, y + 1, secLeft.toString() + "..", FONT_SIZE);
            d.setColor(DEFAULT_COLOR);
            d.drawText(x, y, secLeft.toString() + "..", FONT_SIZE);
        }

        sleeper.sleepFor((long) (500));
        this.secondsLeft.decrease(1);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}