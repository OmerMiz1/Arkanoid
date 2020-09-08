import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The type Rectangle.
 *
 * @author Omer Mizrachi.
 */
public class Rectangle implements Cloneable, Sprite {
    private double wid;
    private double hei;
    private Color col = null;
    private Point upLeft;
    private Line[] frame = new Line[4];

    /**
     * Instantiates a new Rectangle.
     *
     * @param upperLeft the upper left point
     * @param width     the width
     * @param height    the height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.wid = width;
        this.hei = height;
        this.upLeft = upperLeft;
        genFrameLines();
    }

    /**
     * Instantiates a new Rectangle.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }

    /**
     * Instantiates a new Rectangle.
     *
     * @param toClone the to clone
     */
    public Rectangle(Rectangle toClone) {
        this(toClone.getUpperLeft().clone(), toClone.getWidth(), toClone.getHeight());
    }

    /**
     * Gets intersection points as list with other line.
     *
     * @param line the other line
     * @return Intersections. java . util . list
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> intersections = new java.util.ArrayList<>();

        // if there's an intersection --> add it to the list.
        for (Line edge : this.getFrame()) {
            if (line.isIntersecting(edge)) {
                intersections.add(new Point(line.intersectionWith(edge)));
            }
        }
        return intersections;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return this.wid;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return this.hei;
    }

    /**
     * Get frame lines of the rectangle.
     *
     * @return An array of lines.
     */
    public Line[] getFrame() {
        return this.frame;
    }

    /**
     * Gets upper left corner point.
     *
     * @return the upper left
     */
    public Point getUpperLeft() {
        return this.upLeft;
    }

    /**
     * Generates a list with the walls of the rectangle (the frame lines).
     */
    private void genFrameLines() {
        Point botLef, botRig, topRig, topLef = getUpperLeft();
        // calculate each corner of the rectangle's frame.
        botLef = new Point(topLef.getX(), topLef.getY() - this.hei);
        botRig = new Point(topLef.getX() + this.wid, topLef.getY() - this.hei);
        topRig = new Point(topLef.getX() + this.wid, topLef.getY());
        // bottom line
        this.frame[0] = new Line(botLef, botRig);
        // right line
        this.frame[1] = new Line(botRig, topRig);
        // top line
        this.frame[2] = new Line(topLef, topRig);
        // left line
        this.frame[3] = new Line(botLef, topLef);
    }

    /**
     * Gets top edge.
     *
     * @return the top edge
     */
    public Line getTopEdge() {
        return this.frame[2];
    }

    /**
     * Gets bottom edge.
     *
     * @return the bottom edge
     */
    public Line getBottomEdge() {
        return this.frame[0];
    }

    /**
     * Gets right edge.
     *
     * @return the right edge
     */
    public Line getRightEdge() {
        return this.frame[1];
    }

    /**
     * Gets left edge.
     *
     * @return the left edge
     */
    public Line getLeftEdge() {
        return this.frame[3];
    }

    /**
     * Draw on the surface.
     *
     * @param d the surface
     */
    public void drawOn(DrawSurface d) {
        int x = this.getUpperLeft().getXInt();
        int y = this.getUpperLeft().getYRev(d).intValue();
        d.fillRectangle(x, y, (int) this.wid, (int) this.hei);
    }

    /**
     * Draw stroke on.
     *
     * @param d the d
     */
    public void drawStrokeOn(DrawSurface d) {
        int x = this.getUpperLeft().getXInt();
        int y = this.getUpperLeft().getYRev(d).intValue();
        d.drawRectangle(x, y, (int) this.wid, (int) this.hei);
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
        return this.col;
    }

    /**
     * Sets fill Color.
     *
     * @param color the fill Color
     */
    public void setColor(Color color) {
        this.col = color;
    }

    @Override
    public boolean isPaddle() {
        return false;
    }

    @Override
    public boolean isBall() {
        return false;
    }

    /**
     * Sets up left.
     *
     * @param xVal the x val
     * @param yVal the y val
     */
    public void setUpLeft(double xVal, double yVal) {
        this.upLeft.setPoint(xVal, yVal);
        this.genFrameLines();
    }

    /**
     * Returns a copy of the Rectangle.
     *
     * @return the copy.
     */
    public Rectangle clone() {
        return new Rectangle(this);
    }
}
