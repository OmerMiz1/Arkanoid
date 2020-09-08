import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    /**
     * The constant FONT_SIZE.
     */
    public static final int FONT_SIZE = GameLevel.SCREEN_WID / 20;
    /**
     * The constant FONT_COLOR.
     */
    public static final Color FONT_COLOR = Color.RED;

    private SpriteCollection sprites;

    /**
     * Instantiates a new Pause screen.
     *
     * @param sc the sc
     */
    public PauseScreen(SpriteCollection sc) {
        this.sprites = sc;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int x = d.getWidth() / 7, y = (d.getHeight() * 9) / 10;
        this.sprites.drawAllOn(d);
        String msg = "paused -- press space to continue";
        d.setColor(Color.BLACK);
        d.drawText(x + 1, y - 1, msg, FONT_SIZE);
        d.drawText(x - 1, y + 1, msg, FONT_SIZE);
        d.setColor(FONT_COLOR);
        d.drawText(x, y, msg, FONT_SIZE);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }

    /**
     * Gets sprites.
     *
     * @return the sprites
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }
}