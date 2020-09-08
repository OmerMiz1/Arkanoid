import biuoop.DrawSurface;

/**
 * The type Point.
 *
 * @author Omer Mizrachi.
 */
public class Point implements Cloneable {
    /**
     * The constant DEFAULT_RADIUS.
     */
    public static final int DEFAULT_RADIUS = 3;

    private Double x;
    private Double y;

    /**
     * Instantiates a new Point.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Instantiates a new Point.
     *
     * @param p the point
     */
    public Point(Point p) {
        this(p.getX(), p.getY());
    }

    /**
     * Instantiates a new Point (Default values).
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Returns the distance from this point to the other point.
     *
     * @param other the other point
     * @return the distance as a double
     */
    public double distance(Point other) {
        return Math.hypot((this.x - other.x), (this.y - other.y));
    }

    /**
     * Returns true if another point's coordinates are the same.
     *
     * @param other the other point
     * @return true if equal, false otherwise
     */
    public boolean equals(Point other) {
        if (this.x == other.x && this.y == other.y) {
            return true;
        }
        return false;
    }

    /**
     * Gets x.
     *
     * @return the x value of this point
     */
    public Double getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y value of this point
     */
    public Double getY() {
        return this.y;
    }

    /**
     * Gets x int.
     *
     * @return the x int
     */
    public int getXInt() {
        return this.x.intValue();
    }

    /**
     * Gets y int.
     *
     * @return the y int
     */
    public int getYInt() {
        return this.y.intValue();
    }

    /**
     * Gets the reversed y coordinate (mainly for drawing).
     *
     * @param surface Where the point is going to be drawn
     * @return the inverted y value in relation to the size of the screen
     */
    public Double getYRev(DrawSurface surface) {
        return surface.getHeight() - this.y;
    }

    /**
     * Gets y rev (with surface's height given).
     *
     * @param yMax the height of the surface
     * @return surface 's height - y
     */
    public Double getYRev(double yMax) {
        return yMax - this.y;
    }

    /**
     * Draws the point on given surface.
     *
     * @param d the surface
     */
    public void drawOn(DrawSurface d) {
        d.fillCircle(this.getXInt(), this.getYInt(), DEFAULT_RADIUS);
    }

    /**
     * Sets the point's values.
     *
     * @param xVal the new x value
     * @param yVal the new y value
     */
    public void setPoint(double xVal, double yVal) {
        this.x = xVal;
        this.y = yVal;
    }

    /**
     * Sets the point's value.
     *
     * @param newP the new p
     */
    public void setPoint(Point newP) {
        this.setPoint(newP.x, newP.y);
    }

    /**
     * Returns a copy of the point.
     *
     * @return a copy of the point.
     */
    public Point clone() {
        return new Point(this);
    }
}
