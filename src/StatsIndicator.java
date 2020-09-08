import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The type Score indicator.
 */
public class StatsIndicator implements Sprite {
    /**
     * The constant FONT_SIZE.
     */
    public static final int FONT_SIZE = 15;

    private Counter scoreC;
    private Counter livesC;
    private String title;
    private int txtHei;
    private double bWid;
    private Rectangle board;

    /**
     * Instantiates a new Score indicator.
     *
     * @param score     the score
     * @param lives     the lives
     * @param levelName the level name
     * @param block     the block
     */
    public StatsIndicator(Counter score, Counter lives, String levelName, Block block) {
        this.scoreC = score;
        this.livesC = lives;
        this.title = levelName;
        this.txtHei = block.getCenterPoint().getYRev(GameLevel.SCREEN_HEI).intValue() + 10;
        this.bWid = block.getCollisionRectangle().getWidth();
        this.board = block.getCollisionRectangle();
    }

    @Override
    public void drawOn(DrawSurface d) {
        Double stat1X = this.bWid / 7, stat2X = (this.bWid * 3) / 7, stat3X = (this.bWid * 5) / 7;

        d.setColor(Color.WHITE);
        this.board.drawOn(d);

        d.setColor(Color.BLACK);
        d.drawText(stat1X.intValue(), this.txtHei, "Lives: " + this.livesC.getValue(), FONT_SIZE);
        d.drawText(stat2X.intValue(), this.txtHei, "Score: " + this.scoreC.getValue(), FONT_SIZE);
        d.drawText(stat3X.intValue(), this.txtHei, "Level Name: " + this.title, FONT_SIZE);
    }

    @Override
    public void timePassed() {
        // do nothing for now
    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public boolean isPaddle() {
        return false;
    }

    @Override
    public boolean isBall() {
        return false;
    }
}
