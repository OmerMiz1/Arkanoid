import biuoop.DrawSurface;

/**
 * The interface Sprite.
 *
 * @author Omer Mizrachi.
 */
public interface Sprite {
    /**
     * Draw the sprite on the surface.
     *
     * @param d the surface
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the object that time passed.
     */
    void timePassed();

    /**
     * Adds the sprite to a gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    void addToGame(GameLevel gameLevel);

    /**
     * Gets color.
     *
     * @return the color
     */
    java.awt.Color getColor();

    /**
     * Is paddle boolean.
     *
     * @return the boolean
     */
    boolean isPaddle();

    /**
     * Is ball boolean.
     *
     * @return the boolean
     */
    boolean isBall();
}
