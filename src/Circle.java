import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The type Circle.
 */
public class Circle implements Sprite {
    private Point c;
    private Color fill;
    private int size;

    /**
     * Instantiates a new Circle.
     *
     * @param center    the center
     * @param radius    the radius
     * @param fillColor the fill color
     */
    public Circle(Point center, int radius, Color fillColor) {
        this.c = center;
        this.size = radius;
        this.fill = fillColor;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.fill);
        d.fillCircle(this.c.getXInt(), this.c.getYRev(d).intValue(), this.size);
    }

    @Override
    public void timePassed() {
        // doing nothing
    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }

    @Override
    public Color getColor() {
        return this.fill;
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
