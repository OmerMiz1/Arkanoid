import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The type Frame block.
 */
public class FrameBlock extends Block implements Sprite, Collidable {
    /**
     * The Default frame hp.
     */
    public static final int DEFAULT_FRAME_HP = -1;
    /**
     * The Default frame color.
     */
    public static final Color DEFAULT_FRAME_COLOR = Color.GRAY;

    /**
     * Instantiates a new Frame block.
     *
     * @param rect the rect
     */
    public FrameBlock(Rectangle rect) {
        super(rect);
        setHp();
    }

    /**
     * Instantiates a new Frame block.
     *
     * @param upperLeft the upper left
     * @param width     the width
     * @param height    the height
     */
    public FrameBlock(Point upperLeft, int width, int height) {
        super(upperLeft, width, height);
        setHp(DEFAULT_FRAME_HP);
        setCurrCol(DEFAULT_FRAME_COLOR);
    }

    /**
     * Instantiates a new Frame block.
     *
     * @param xUpLeft the x up left
     * @param yUpLeft the y up left
     * @param width   the width
     * @param height  the height
     */
    public FrameBlock(int xUpLeft, int yUpLeft, int width, int height) {
        super(xUpLeft, yUpLeft, width, height);
        setHp();
    }

    /**
     * Sets hp.
     */
    private void setHp() {
        this.setHp(-1);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.getColor());
        this.getCollisionRectangle().drawOn(d);
    }
}
