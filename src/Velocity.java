import java.util.Random;

/**
 * The type Velocity.
 *
 * @author Omer Mizrachi.
 */
public class Velocity implements Cloneable {
    private Double dx;
    private Double dy;

    /**
     * Instantiates a new Velocity (constructor #1).
     *
     * @param dx the dx
     * @param dy the dy
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Calculates the velocity's dx, dy fields from angle (degrees) and speed.
     *
     * @param angle the direction
     * @param speed the speed (distance with time)
     * @return the velocity object.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //convert angle from degrees to radians
        // DEBUG POINT angle = Math.toRadians(angle);
        double xDiff = (speed * Math.cos(Math.toRadians(angle)));
        double yDiff = (speed * Math.sin(Math.toRadians(angle)));
        return new Velocity(xDiff, yDiff);
    }

    /**
     * Returns a random angle in Degrees.
     *
     * @return the angle (double)
     */
    public static double randAngle() {
        Random rand = new Random();
        return (60 + (120 - 60) * rand.nextDouble());
    }

    /**
     * Gets the dx value of the object.
     *
     * @return the dx of the object
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets the dy of the object.
     *
     * @return the dy of the object.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Sets velocity.
     *
     * @param newDx the dx
     * @param newDy the dy
     */
    public void setVelocity(Double newDx, Double newDy) {
        this.dx = newDx;
        this.dy = newDy;
    }

    /**
     * Sets velocity.
     *
     * @param vel the vel
     */
    public void setVelocity(Velocity vel) {
        this.setVelocity(vel.getDx(), vel.getDy());
    }

    /**
     * Applies the change of a point (for the next frame).
     *
     * @param p the point to change
     * @return the resulting point after the change.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.getDx(), p.getY() + this.getDy());
    }

    /**
     * Returns a copy of the velocity object.
     *
     * @return the copy
     */
    public Velocity clone() {
        return new Velocity(this.dx, this.dy);
    }
}

