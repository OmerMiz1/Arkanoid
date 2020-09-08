import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The type End screen.
 */
public class EndScreen extends PauseScreen {
    private int score;
    private boolean hasWon = false;

    /**
     * Instantiates a new End screen.
     *
     * @param lastLevelSprites the last level sprites
     * @param score            the score
     * @param lives            the lives
     */
    public EndScreen(SpriteCollection lastLevelSprites, int score, int lives) {
        super(lastLevelSprites);
        this.score = score;
        if (lives > 0) {
            this.hasWon = true;
        }
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int x = (d.getWidth() / 5);
        int y = d.getHeight() - d.getHeight() / 3;
        String msg = "Game Over. Your score is ";
        Color col = FONT_COLOR;

        if (this.hasWon) {
            msg = "You Win! Your score is ";
            col = Ball.randColor();
        }

        getSprites().drawAllOn(d);
        getSprites().notifyAllTimePassed();

        d.setColor(Color.BLACK);
        d.drawText(x + 2, y + 2, msg + this.score, FONT_SIZE);
        d.drawText(x - 2, y - 2, msg + this.score, FONT_SIZE);
        d.setColor(col);
        d.drawText(x, y, msg + this.score, FONT_SIZE);
    }
}
